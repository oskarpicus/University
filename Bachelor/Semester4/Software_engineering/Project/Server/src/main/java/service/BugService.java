package service;

import domain.Bug;
import domain.Severity;
import domain.Tester;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.BugRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class BugService {
    private final BugRepository repository;
    private final static Logger logger = LogManager.getLogger();

    public BugService(BugRepository repository) {
        this.repository = repository;
    }

    /**
     * Method for saving a bug
     * @param bug: Bug
     * @return an {@code Optional}
     *          - null, if the bug was successfully saved
     *          - the bug, otherwise
     */
    public Optional<Bug> add(Bug bug){
        logger.traceEntry("Entry Add {}", bug);
        Optional<Bug> result = repository.save(bug);
        logger.traceExit("Exit Add result {}", result);
        return result;
    }

    /**
     * Method for finding the bugs added by a tester
     * @param tester: Tester, the tester that added the bugs
     * @return l: List<Bug>, the bugs added by tester
     */
    public List<Bug> findBugsByTester(Tester tester) {
        logger.traceEntry("Entry Find Bugs By Tester {}", tester);
        Iterable<Bug> result = repository.findBugsByTester(tester);
        if (result != null) {
            return StreamSupport.stream(result.spliterator(), false).collect(Collectors.toList());
        }
        return null;
    }

    /**
     * Method for obtaining all the registered bugs
     * @return l: List<Bug>, containing all the registered bugs
     */
    public List<Bug> findAll() {
        logger.traceEntry("Entry Find All Bugs");
        Iterable<Bug> result = repository.findAll();
        List<Bug> resultList = StreamSupport.stream(result.spliterator(), false).collect(Collectors.toList());
        logger.traceExit("Exit Find All Bugs {}", resultList);
        return resultList;
    }

    /**
     * Method for filtering bugs, based on their severity
     * @param severity: Severity, the desired severity
     * @return l: List<Bug>, containing all the bugs with severity
     */
    public List<Bug> findBugsBySeverity(Severity severity) {
        logger.traceEntry("Entry Find Bugs By Severity {}", severity);
        Iterable<Bug> result = repository.findBugsBySeverity(severity);
        List<Bug> resultList = StreamSupport.stream(result.spliterator(), false).collect(Collectors.toList());
        logger.traceExit("Exit Find Bugs By Severity {}", resultList);
        return resultList;
    }

    /**
     * Method for updating a bug
     * @param bug: Bug, the bug to be updated
     * @return an {@code Optional}
     *          - null, if the {@code bug} was successfully updated
     *          - the bug, otherwise
     */
    public Optional<Bug> update(Bug bug) {
        logger.traceEntry("Entry Update Bug {}", bug);
        Optional<Bug> result = this.repository.update(bug);
        logger.traceExit("Exit Update Bug result {}", result);
        return result;
    }
}
