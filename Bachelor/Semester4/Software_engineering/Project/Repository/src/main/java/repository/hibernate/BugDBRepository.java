package repository.hibernate;

import domain.Bug;
import domain.Severity;
import domain.Status;
import domain.Tester;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import repository.BugRepository;
import validator.Validator;

import java.util.List;

public class BugDBRepository extends AbstractDBRepository<Long, Bug> implements BugRepository {

    public BugDBRepository(Validator<Long, Bug> validator) {
        super(validator);
    }

    @Override
    protected Query<Bug> getFindQuery(Session session, Long aLong) {
        return session.createQuery("from Bug where id=:id", Bug.class)
                .setParameter("id", aLong);
    }

    @Override
    protected Query<Bug> getFindAllQuery(Session session) {
        return session.createQuery("from Bug where status!=:status", Bug.class)
                .setParameter("status", Status.RESOLVED);
    }

    @Override
    public Iterable<Bug> findBugsByTester(Tester tester) {
        logger.traceEntry("Entry Find Bugs By Tester {}", tester);
        if (tester == null) {
            throw logger.throwing(new IllegalArgumentException());
        }
        List<Bug> result = null;
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            result = session.createQuery("from Bug where tester=:tester", Bug.class)
                    .setParameter("tester", tester)
                    .list();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            if (transaction != null)
                transaction.rollback();
        }
        logger.traceExit("Exit Find Bugs By Tester result {}", result);
        return result;
    }

    @Override
    public Iterable<Bug> findBugsBySeverity(Severity severity) {
        logger.traceEntry("Entry Find Bugs By Severity {}", severity);
        if (severity == null) {
            throw logger.throwing(new IllegalArgumentException());
        }
        List<Bug> result = null;
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            result = session.createQuery("FROM Bug where severity=:severity and status!=:status", Bug.class)
                    .setParameter("severity", severity)
                    .setParameter("status", Status.RESOLVED)
                    .list();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            if (transaction != null)
                transaction.rollback();
        }
        logger.traceExit("Exit Find Bugs By Severity result {}", result);
        return result;
    }
}
