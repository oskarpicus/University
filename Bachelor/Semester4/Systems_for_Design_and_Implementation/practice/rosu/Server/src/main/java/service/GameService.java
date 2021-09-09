package service;

import domain.Game;
import repository.GameRepository;

import java.util.Optional;

public class GameService {
    private final GameRepository repository;

    public GameService(GameRepository repository) {
        this.repository = repository;
    }

    public Optional<Game> save(Game game) {
        return repository.save(game);
    }

    public Optional<Game> update(Game game) {
        return repository.update(game);
    }

    public Optional<Game> find(Long currentGameId) {
        return repository.find(currentGameId);
    }
}
