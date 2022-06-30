package client;

import domain.Spectacol;
import domain.Vanzare;
import services.Observer;
import services.Service;

import java.io.Serializable;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.*;

public class Client implements Observer, Serializable {
    private transient final Service service;
    private transient final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

    public Client(Service service) {
        this.service = service;
    }

    public void run() {
        service.addObserver(this);
        executorService.scheduleAtFixedRate(() -> {
            Future<Boolean> result = Executors.newSingleThreadExecutor()
                    .submit(() -> {
                        Vanzare vanzare = getRandomVanzare();
                        System.out.println(vanzare);
                        return service.buyTicket(vanzare);
                    });
            try {
                System.out.println(result.get() ? "SAVED SUCCESSFULLY " : "FAILED TO SAVE");
            } catch (ExecutionException | InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }, 0, 2, TimeUnit.SECONDS);
    }

    @Override
    public void serverCloses() {
        executorService.shutdown();
        System.out.println("GOT NOTIFICATION TO STOP");
        System.exit(0);
    }

    private Vanzare getRandomVanzare() {
        Random random = new Random();
        if (random.nextBoolean()) {
            int nrBileteVandute = Math.abs(random.nextInt(100));
            List<Integer> locuri = new ArrayList<>();
            for (int i = 0; i < nrBileteVandute; i++) {
                locuri.add(random.nextInt());
            }

            float suma = Math.abs(random.nextFloat());
            int spectacolId = Math.abs(random.nextInt());

            return new Vanzare(new Spectacol(spectacolId), Date.from(Instant.now()), nrBileteVandute, locuri, suma);
        } else {
            System.out.println("HARDCODED !!!");
            Map<Integer, Float> spectacole = new HashMap<>();
            spectacole.put(1, 20f);
            spectacole.put(2, 40f);
            spectacole.put(3, 15f);
            int key = List.of(1, 2, 3).get(random.nextInt(spectacole.size()));
            return new Vanzare(new Spectacol(key), Date.from(Instant.now()), 2, List.of(random.nextInt(100), random.nextInt(100)), 2 * spectacole.get(key));
        }
    }
}
