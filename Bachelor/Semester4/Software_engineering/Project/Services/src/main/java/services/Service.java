package services;

import domain.Bug;
import domain.Severity;
import domain.Tester;
import domain.User;

import java.util.List;

public interface Service {

    /**
     * Method for finding a user by their username and password
     * @param username: String, the desired username
     * @param password: String, the desired password
     * @return
     *          - null, if there is no user with that {@code username} and {@code password}
     *          - the user, otherwise
     */
    User findUser(String username, String password);

    /**
     * Method for adding a bug
     * @param bug: Bug, the bug that wants to be added
     * @return
     *          - null, if {@code bug} is successfully saved
     *          - the bug, otherwise
     */
    Bug addBug(Bug bug);

    /**
     * Method for finding the bugs added by a tester
     * @param tester: Tester, the tester that added the bugs
     * @return l: List<Bug>, the bugs added by tester
     */
    List<Bug> findBugsByTester(Tester tester);

    /**
     * Method for obtaining all the registered bugs
     * @return l: List<Bug>, containing all the registered bugs
     */
    List<Bug> getAllBugs();

    /**
     * Method for adding an observer
     * @param observer: Observer, the client that made the request
     */
    void addObserver(Observer observer);

    /**
     * Method for removing an observer
     * @param observer: Observer, the client that made the request
     */
    void removeObserver(Observer observer);

    /**
     * Method for filtering bugs, based on their severity
     * @param severity: Severity, the desired severity
     * @return list of bugs, all with severity
     */
    List<Bug> findBugsBySeverity(Severity severity);

    /**
     * Method for updating a bug
     * @param bug: Bug, the bug to be updated
     * @return
     *          - null, if {@code bug} was successfully updated
     *          - the bug, otherwise
     */
    Bug updateBug(Bug bug);
}
