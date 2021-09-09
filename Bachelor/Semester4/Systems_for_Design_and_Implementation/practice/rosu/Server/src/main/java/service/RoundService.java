package service;

import domain.Round;
import repository.RoundRepository;

import java.util.Optional;

public class RoundService {
    private final RoundRepository repository;

    public RoundService(RoundRepository repository) {
        this.repository = repository;
    }

    public Optional<Round> save(Round round) {
        return repository.save(round);
    }
}
