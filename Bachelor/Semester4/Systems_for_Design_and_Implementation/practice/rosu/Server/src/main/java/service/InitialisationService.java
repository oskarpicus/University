package service;

import domain.Initialisation;
import repository.InitialisationRepository;

import java.util.Optional;

public class InitialisationService {
    private final InitialisationRepository repository;

    public InitialisationService(InitialisationRepository repository) {
        this.repository = repository;
    }

    public Optional<Initialisation> save(Initialisation initialisation) {
        return repository.save(initialisation);
    }
}
