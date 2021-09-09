package repository;

import domain.Player;

import java.util.Optional;

public interface PlayerRepository extends Repository<Long, Player> {
    /**
     * Method for finding a user based on its username or password (e.g. login)
     * @param username: String, the desired username
     * @param password: String, the desired password
     * @return an {@code Optional}
     *         - null, if there is no User with username and password
     *         - the user, otherwise
     */
    Optional<Player> findByUsernamePassword(String username, String password);
}

