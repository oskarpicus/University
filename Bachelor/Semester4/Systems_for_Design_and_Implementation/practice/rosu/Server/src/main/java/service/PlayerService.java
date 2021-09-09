package service;

import domain.Player;
import repository.PlayerRepository;

import java.util.Optional;

public class PlayerService {
    private final PlayerRepository repository;

    public PlayerService(PlayerRepository repository) {
        this.repository = repository;
    }

    public Optional<Player> login(String username, String password) {
        return repository.findPlayerByUsernamePassword(username, password);
    }

    public Optional<Player> find(Long id) {
        return repository.find(id);
    }
}
