package validator;

import domain.Bug;

public class BugValidator implements Validator<Long, Bug> {

    @Override
    public void validate(Bug entity) {
        String errors = "";
        if(!validateName(entity))
            errors += "Invalid name\n";
        if(!validateDescription(entity))
            errors += "Invalid description\n";
        if(!validateSeverity(entity))
            errors += "Invalid severity\n";
        if(!validateStatus(entity))
            errors += "Invalid status\n";
        if(!validateTester(entity))
            errors += "Invalid tester\n";
        if(!errors.equals(""))
            throw new ValidationException(errors);
    }

    /**
     * Method for verifying the name of a bug
     * @param entity: Bug, bug to check
     * @return - true, if {@code entity}'s name is not empty
     *         - false, otherwise
     */
    private boolean validateName(Bug entity){
        return !entity.getName().equals("");
    }

    /**
     * Method for verifying the description of a bug
     * @param entity: Bug, bug to check
     * @return - true, if {@code entity}'s description is not empty
     *         - false, otherwise
     */
    private boolean validateDescription(Bug entity){
        return !entity.getDescription().equals("");
    }

    /**
     * Method for verifying the severity of a bug
     * @param entity: Bug, bug to check
     * @return - true, if {@code entity}'s severity is not null
     *         - false, otherwise
     */
    private boolean validateSeverity(Bug entity){
        return entity.getSeverity() != null;
    }

    /**
     * Method for verifying the status of a bug
     * @param entity: Bug, bug to check
     * @return - true, if {@code entity}'s status is not null
     *         - false, otherwise
     */
    private boolean validateStatus(Bug entity){
        return entity.getStatus() != null;
    }

    /**
     * Method for verifying the tester of a bug
     * @param entity: Bug, bug to check
     * @return - true, if {@code entity}'s tester is not null
     *         - false, otherwise
     */
    private boolean validateTester(Bug entity){
        return entity.getTester() != null;
    }
}
