package repository.hibernate;

import domain.Player;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import repository.PlayerRepository;
import validator.Validator;

import java.util.Optional;

public class PlayerHbRepository extends AbstractHbRepository<Long, Player> implements PlayerRepository {
    protected PlayerHbRepository(Validator<Long, Player> validator) {
        super(validator);
    }

    @Override
    public Optional<Player> findByUsernamePassword(String username, String password) {
        logger.traceEntry("Find By Username Password {} {}", username, password);
        Optional<Player> result = Optional.empty();
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            Player player = session.createQuery("from Player where username=:username and password=:password", Player.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .setMaxResults(1)
                    .uniqueResult();
            transaction.commit();
            result = player != null ? Optional.of(player) : result;
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
    protected Query<Player> getFindQuery(Session session, Long aLong) {
        return session.createQuery("from Player where id=:id", Player.class)
                .setParameter("id", aLong);
    }

    @Override
    protected Query<Player> getFindAllQuery(Session session) {
        return session.createQuery("from Player", Player.class);
    }
}
