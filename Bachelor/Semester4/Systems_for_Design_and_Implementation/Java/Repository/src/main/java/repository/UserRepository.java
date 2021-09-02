package repository;

import domain.User;

import java.util.Optional;

public interface UserRepository extends Repository<Long, User> {
    /**
     * Method for finding a user based on its username or password (e.g. login)
     * @param username: String, the desired username
     * @param password: String, the desired password
     * @return an {@code Optional}
     *         - null, if there is no User with username and password
     *         - the user, otherwise
     */
    Optional<User> findByUsernamePassword(String username, String password);
}
