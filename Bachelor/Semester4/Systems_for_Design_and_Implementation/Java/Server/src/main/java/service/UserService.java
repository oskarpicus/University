package service;

import domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.UserRepository;

import java.util.Optional;

public class UserService {
    private final UserRepository repository;
    private static final Logger logger = LogManager.getLogger();

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    /**
     * Method for finding a user by his/her username and password
     * @param username : String, the desired username
     * @param password : String, the desired password
     * @return an {@code Optional}
     *          - null, if there is no user with that username and password
     *          - the user, otherwise
     */
    public Optional<User> find(String username, String password){
        logger.traceEntry("Entry find user {} {}", username, password);
        Optional<User> result = repository.findByUsernamePassword(username, password);
        logger.traceExit(result);
        return result;
    }
}
