package mocks;

import domain.Spectacol;
import repository.SpectacolRepository;

import java.util.List;
import java.util.Optional;

public class MockSpectacolRepository implements SpectacolRepository {
    @Override
    public int save(Spectacol entity) {
        return 0;
    }

    @Override
    public Optional<Spectacol> find(Integer integer) {
        return Optional.empty();
    }

    @Override
    public Optional<Spectacol> update(Spectacol entity) {
        return Optional.empty();
    }

    @Override
    public Iterable<Spectacol> findAll() {
        return null;
    }

    @Override
    public List<Integer> getLocuriVandute(Spectacol spectacol) {
        return null;
    }
}
