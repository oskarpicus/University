package service;

import domain.Sala;
import domain.Spectacol;
import domain.Vanzare;
import repository.SpectacolRepository;
import repository.VanzareRepository;
import services.Observer;
import services.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MasterService implements Service {
    private final SpectacolRepository spectacolRepository;
    private final VanzareRepository vanzareRepository;
    private final List<Observer> observers = new ArrayList<>();
    private static final String CHECK_RESULTS_FILEPATH = "checks.txt";
    private static final Sala sala = new Sala(100);

    public MasterService(SpectacolRepository spectacolRepository, VanzareRepository vanzareRepository) {
        this.spectacolRepository = spectacolRepository;
        this.vanzareRepository = vanzareRepository;
    }

    @Override
    public void checkShows() {
        System.out.println("CALLED CHECK " + LocalDateTime.now());

        List<Spectacol> spectacoleViitoare = StreamSupport.stream(spectacolRepository.findAll().spliterator(), false)
                .filter(spectacol -> spectacol.getData().after(Date.from(Instant.now())))
                .collect(Collectors.toList());

        Map<Spectacol, Boolean> oks = new HashMap<>();
        for (Spectacol spectacol : spectacoleViitoare) {
            Iterable<Vanzare> vanzari = vanzareRepository.getBySpectacol(spectacol);
            float sum = 0;
            for (Vanzare vanzare : vanzari) {
                sum += vanzare.getSuma();
            }

            List<Integer> locuriVandute = StreamSupport.stream(vanzari.spliterator(), false)
                    .map(vanzare -> {
                        List<Integer> locuriVanduteCurrent = vanzareRepository.getLocuriVandute(vanzare);
                        vanzare.setListaLocuriVandute(locuriVanduteCurrent);
                        return locuriVanduteCurrent;
                    })
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());

            boolean properSumSeats = StreamSupport.stream(vanzari.spliterator(), false)
                    .allMatch(vanzare -> vanzare.getListaLocuriVandute().size() * spectacol.getPret() == vanzare.getSuma());

            spectacol.setLocuriVandute(locuriVandute);
            spectacol.setVanzari(StreamSupport.stream(vanzari.spliterator(), false).collect(Collectors.toList()));

            boolean isSet = locuriVandute.size() == new HashSet<>(locuriVandute).size();

            oks.put(spectacol, spectacol.getSold() == sum && isSet && properSumSeats);
        }

        writeCheckResults(oks);
    }

    @Override
    public boolean buyTicket(Vanzare vanzare) {
        List<Integer> locuriVandute = StreamSupport.stream(vanzareRepository.getBySpectacol(vanzare.getSpectacol()).spliterator(), false)
                .map(v -> {
                    List<Integer> locuriVanduteCurrent = vanzareRepository.getLocuriVandute(v);
                    v.setListaLocuriVandute(locuriVanduteCurrent);
                    return locuriVanduteCurrent;
                })
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        if (locuriVandute.size() != new HashSet<>(locuriVandute).size()) {
            return false;
        }

        boolean isNotTakenSeats = vanzare.getListaLocuriVandute().stream()
                .noneMatch(locuriVandute::contains);
        if (!isNotTakenSeats) {
            return false;
        }

        if (vanzare.getListaLocuriVandute().size() > sala.getNrLocuri()) {
            return false;
        }

        boolean isLocuriRespected = vanzare.getListaLocuriVandute().stream()
                .allMatch(loc -> loc <= sala.getNrLocuri());
        if (!isLocuriRespected) {
            return false;
        }

        Optional<Spectacol> spectacol = spectacolRepository.find(vanzare.getSpectacol().getId());
        if (spectacol.isEmpty()) {
            return false;
        }
        vanzare.setSpectacol(spectacol.get());

        if (vanzare.getSuma() % spectacol.get().getPret() != 0) {
            return false;
        }

        int generatedId = vanzareRepository.save(vanzare);
        if (generatedId == 0) {
            return false;
        }

        vanzare.getSpectacol().setSold(vanzare.getSpectacol().getSold() + vanzare.getSuma());
        spectacolRepository.update(vanzare.getSpectacol());

        return true;
    }

    @Override
    public synchronized void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObserversServerCloses() {
        observers.forEach(Observer::serverCloses);
        observers.clear();
    }

    private void writeCheckResults(Map<Spectacol, Boolean> oks) {
        String result = oks.entrySet().stream()
                .map(entry -> LocalDateTime.now() + "," + entry.getKey().toString() + "," + entry.getKey().getVanzari() + "," + (entry.getValue() ? ",corect\n" : ",incorect\n"))
                .reduce("", (x, y) -> x + y);
        Path path = Paths.get(CHECK_RESULTS_FILEPATH);
        try {
            Files.writeString(path, result, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
