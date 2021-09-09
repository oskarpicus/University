package repository.hibernate;

import domain.Game;
import domain.Player;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import repository.GameRepository;
import validator.Validator;

import java.util.Optional;


public class GameHbRepository extends AbstractHbRepository<Long, Game> implements GameRepository {

    protected GameHbRepository(Validator<Long, Game> validator) {
        super(validator);
    }

    @Override
    protected Query<Game> getFindQuery(Session session, Long aLong) {
        return session.createQuery("from Game where id=:id", Game.class)
                .setParameter("id", aLong);
    }

    @Override
    protected Query<Game> getFindAllQuery(Session session) {
        return session.createQuery("from Game ", Game.class);
    }

    @Override
    public int getPointsByPlayer(Game game, Player player) {
        int result = 0;
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            result = session
                    .createQuery("select sum(points) from Guess where game=:g and guesser=:p", Long.class)
                    .setParameter("g", game)
                    .setParameter("p", player)
                    .setMaxResults(1)
                    .uniqueResult()
                    .intValue();
            transaction.commit();
        } catch (Exception e){
            System.out.println(e.getMessage());
            logger.error(e);
            if (transaction != null)
                transaction.rollback();
        }
        logger.traceExit("Find By Username Password result {}", result);
        return result;
    }
}
