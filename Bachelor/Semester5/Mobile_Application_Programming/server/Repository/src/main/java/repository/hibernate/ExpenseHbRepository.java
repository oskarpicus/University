package repository.hibernate;

import model.Expense;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import repository.ExpenseRepository;
import validator.ExpenseValidator;

import java.util.List;
import java.util.Optional;

public class ExpenseHbRepository implements ExpenseRepository {
    protected static SessionFactory sessionFactory;
    protected static final Logger logger = LogManager.getLogger();
    protected final ExpenseValidator validator;

    static {
        // connecting to the database and migrations
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);
            throw new RuntimeException("Failed to create session factory");
        }
    }

    public ExpenseHbRepository(ExpenseValidator validator) {
        this.validator = validator;
    }

    @Override
    public Optional<Expense> save(Expense entity) {
        logger.traceEntry("Entry Save {}", entity);
        if (entity == null) {
            throw logger.throwing(new IllegalArgumentException());
        }
        validator.validate(entity);
        Transaction transaction = null;
        Optional<Expense> result = Optional.empty();
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            logger.error(e);
            if (transaction != null)
                transaction.rollback();
            result = Optional.of(entity);
        }
        logger.traceExit("Exit Save result {}", result);
        return result;
    }

    @Override
    public Optional<Expense> delete(String id) {
        logger.traceEntry("Entry Delete id {}", id);
        if (id == null) {
            throw logger.throwing(new IllegalArgumentException());
        }
        Transaction transaction = null;
        Optional<Expense> result = Optional.empty();
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            result = find(id);
            result.ifPresent(session::delete);
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            logger.error(e);
            if (transaction != null)
                transaction.rollback();
        }
        logger.traceExit("Exit Delete result {}", result);
        return result;
    }

    @Override
    public Optional<Expense> update(Expense entity) {
        logger.traceEntry("Entry Update {}", entity);
        if (entity == null) {
            throw logger.throwing(new IllegalArgumentException());
        }
        validator.validate(entity);
        Transaction transaction = null;
        Optional<Expense> result = Optional.empty();
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(entity);
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            logger.error(e);
            if (transaction != null)
                transaction.rollback();
            result = Optional.of(entity);
        }
        logger.traceExit("Exit Update result {}", result);
        return result;
    }

    @Override
    public Optional<Expense> find(String id) {
        logger.traceEntry("Entry Find id {}", id);
        if (id == null) {
            throw logger.throwing(new IllegalArgumentException());
        }
        Optional<Expense> result = Optional.empty();
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Expense entity = getFindQuery(session, id)
                    .setMaxResults(1)
                    .uniqueResult();
            transaction.commit();
            result = entity != null ? Optional.of(entity) : result;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            logger.error(e);
            if (transaction != null)
                transaction.rollback();
        }
        logger.traceExit("Exit Find result {}", result);
        return result;
    }

    @Override
    public Iterable<Expense> findAll() {
        logger.traceEntry("Entry Find All");
        Transaction transaction = null;
        List<Expense> result = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            result = getFindAllQuery(session).list();
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            logger.error(e);
            if (transaction != null)
                transaction.rollback();
        }
        logger.traceExit("Entry Find All result {}", result);
        return result;
    }

    private Query<Expense> getFindQuery(Session session, String id) {
        return session.createQuery("from Expense where id=:id", Expense.class)
                .setParameter("id", id);
    }

    private Query<Expense> getFindAllQuery(Session session) {
        return session.createQuery("from Expense", Expense.class);
    }
}
