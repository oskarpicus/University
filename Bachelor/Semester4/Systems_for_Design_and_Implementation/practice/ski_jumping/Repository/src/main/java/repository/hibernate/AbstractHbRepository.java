package repository.hibernate;

import domain.Entity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import repository.Repository;
import validator.Validator;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

abstract class AbstractHbRepository<ID extends Serializable, E extends Entity<ID>> implements Repository<ID,E> {

    protected static SessionFactory sessionFactory;
    protected static final Logger logger = LogManager.getLogger();
    protected final Validator<ID, E> validator;

    static {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        try{
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            System.out.println("Successfully created session factory");
        }catch (Exception e){
            System.out.println("Failed to create session factory");
            System.out.println(e.getMessage());
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    protected AbstractHbRepository(Validator<ID, E> validator) {
        this.validator = validator;
    }

    @Override
    public Optional<E> save(E entity) {
        logger.traceEntry("Entry Save {}", entity);
        if (entity == null){
            throw logger.throwing(new IllegalArgumentException());
        }
        validator.validate(entity);
        Transaction transaction = null;
        Optional<E> result = Optional.empty();
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
        } catch (Exception e){
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
    public Optional<E> delete(ID id) {
        logger.traceEntry("Entry Delete id {}", id);
        if (id == null){
            throw logger.throwing(new IllegalArgumentException());
        }
        Transaction transaction = null;
        Optional<E> result = Optional.empty();
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            result = find(id);
            result.ifPresent(session::delete);
            transaction.commit();
        } catch (Exception e){
            System.out.println(e.getMessage());
            logger.error(e);
            if (transaction != null)
                transaction.rollback();
        }
        logger.traceExit("Exit Delete result {}", result);
        return result;
    }

    @Override
    public Optional<E> update(E entity) {
        logger.traceEntry("Entry Update {}", entity);
        if (entity == null){
            throw logger.throwing(new IllegalArgumentException());
        }
        validator.validate(entity);
        Transaction transaction = null;
        Optional<E> result = Optional.empty();
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.update(entity);
            transaction.commit();
        } catch (Exception e){
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
    public Optional<E> find(ID id) {
        logger.traceEntry("Entry Find id {}", id);
        if (id == null){
            throw logger.throwing(new IllegalArgumentException());
        }
        Optional<E> result = Optional.empty();
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            E entity = getFindQuery(session, id)
                    .setMaxResults(1)
                    .uniqueResult();
            transaction.commit();
            result = entity != null ? Optional.of(entity) : result;
        } catch (Exception e){
            System.out.println(e.getMessage());
            logger.error(e);
            if (transaction != null)
                transaction.rollback();
        }
        logger.traceExit("Exit Find result {}", result);
        return result;
    }

    @Override
    public Iterable<E> findAll() {
        logger.traceEntry("Entry Find All");
        Transaction transaction = null;
        List<E> result = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            result = getFindAllQuery(session).list();
            transaction.commit();
        }catch (Exception e){
            System.out.println(e.getMessage());
            logger.error(e);
            if (transaction != null)
                transaction.rollback();
        }
        logger.traceExit("Entry Find All result {}", result);
        return result;
    }

    protected abstract Query<E> getFindQuery(Session session, ID id);

    protected abstract Query<E> getFindAllQuery(Session session);
}
