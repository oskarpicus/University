package validator;

import domain.User;

public class UserValidator implements Validator<Long, User> {

    @Override
    public void validate(User entity) {
        String errors = "";
        if(!validateUsername(entity))
            errors += "Invalid username\n";
        if(!validatePassword(entity))
            errors += "Invalid password\n";
        if(!errors.equals(""))
            throw new ValidationException(errors);
    }

    /**
     * Method for verifying the username of a user
     * @param entity: User, user to check
     * @return - true, if {@code entity}'s username is not empty
     *         - false, otherwise
     */
    private boolean validateUsername(User entity){
        return !entity.getUsername().equals("");
    }

    /**
     * Method for verifying the password of a user
     * @param entity: User, user to check
     * @return - true, if {@code entity}'s password is not empty
     *         - false, otherwise
     */
    private boolean validatePassword(User entity){
        return !entity.getPassword().equals("");
    }
}
