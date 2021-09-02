package domain.validator;

import domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserValidator implements Validator<Long, User> {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public void validate(User entity) {
        String errors = "";
        if(!validateUsername(entity))
            errors+="Invalid username\n";
        if(!validatePassword(entity))
            errors+="Invalid password\n";
        if(!errors.equals(""))
            throw logger.throwing(new ValidationException(errors));
    }

    /**
     * Method for verifying the username of a user
     * @param user: User, user to check
     * @return - true, if {@param user}'s username is not empty
     *         - false, otherwise
     */
    private boolean validateUsername(User user){
        return !user.getUsername().equals("");
    }

    /**
     * Method for verifying the password of a user
     * @param user: User, user to check
     * @return - true, if {@param user}'s password is not empty and has at least 5 characters
     *         - false, otherwise
     */
    private boolean validatePassword(User user){
        int length = user.getPassword().length();
        return !user.getPassword().equals("") && length>=5;
    }
}
