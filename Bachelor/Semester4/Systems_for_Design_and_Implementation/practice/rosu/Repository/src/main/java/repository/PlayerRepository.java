package repository;

import domain.Player;

import java.util.Optional;

public interface PlayerRepository extends Repository<Long, Player> {
    Optional<Player> findPlayerByUsernamePassword(String username, String password);
}
