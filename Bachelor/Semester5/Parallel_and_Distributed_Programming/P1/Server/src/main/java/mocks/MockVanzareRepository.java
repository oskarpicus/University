package mocks;

import domain.Spectacol;
import domain.Vanzare;
import repository.VanzareRepository;

import java.util.List;
import java.util.Optional;

public class MockVanzareRepository implements VanzareRepository {
    @Override
    public int save(Vanzare entity) {
        return 0;
    }

    @Override
    public Optional<Vanzare> find(Integer integer) {
        return Optional.empty();
    }

    @Override
    public Optional<Vanzare> update(Vanzare entity) {
        return Optional.empty();
    }

    @Override
    public Iterable<Vanzare> findAll() {
        return null;
    }

    @Override
    public Iterable<Vanzare> getBySpectacol(Spectacol spectacol) {
        return null;
    }

    @Override
    public List<Integer> getLocuriVandute(Vanzare vanzare) {
        return null;
    }
}
