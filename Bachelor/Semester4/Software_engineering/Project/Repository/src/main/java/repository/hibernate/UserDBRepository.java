package repository.hibernate;

import domain.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import repository.UserRepository;
import validator.Validator;

import java.util.Optional;

public class UserDBRepository extends AbstractDBRepository<Long, User> implements UserRepository{

    public UserDBRepository(Validator<Long, User> validator) {
        super(validator);
    }

    @Override
    public Optional<User> findByUsernamePassword(String username, String password) {
        logger.traceEntry("Find By Username Password {} {}", username, password);
        Optional<User> result = Optional.empty();
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            User user = session.createQuery("from User where username=:username and password=:password", User.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .setMaxResults(1)
                    .uniqueResult();
            transaction.commit();
            result = user != null ? Optional.of(user) : result;
        } catch (Exception e){
            System.out.println(e.getMessage());
            logger.error(e);
            if (transaction != null)
                transaction.rollback();
        }
        logger.traceExit("Find By Username Password result {}", result);
        return result;
    }

    @Override
    protected Query<User> getFindQuery(Session session, Long aLong) {
        return session.createQuery("from User where id = :id", User.class)
                .setParameter("id", aLong);
    }

    @Override
    protected Query<User> getFindAllQuery(Session session) {
        return session.createQuery("from User", User.class);
    }
}
