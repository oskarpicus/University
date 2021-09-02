package repository;

import domain.Bug;
import domain.Severity;
import domain.Tester;

public interface BugRepository extends Repository<Long, Bug> {

    /**
     * Method for finding bugs that were added by a Tester
     * @param tester: Tester, the tester that added the bugs
     * @return i: Iterable<Bug>, containing all of tester's bugs
     * @throws IllegalArgumentException if tester is null
     */
    Iterable<Bug> findBugsByTester(Tester tester);

    /**
     * Method for filtering bugs, based on their severity
     * @param severity: Severity, the desired severity
     * @return i: Iterable<Bug>, containing all the bugs with severity
     * @throws IllegalArgumentException if severity is null
     */
    Iterable<Bug> findBugsBySeverity(Severity severity);
}
