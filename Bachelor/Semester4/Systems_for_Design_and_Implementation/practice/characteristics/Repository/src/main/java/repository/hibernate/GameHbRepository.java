package repository.hibernate;

import domain.Game;
import domain.Player;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import repository.GameRepository;
import validator.Validator;

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
    public Integer getPointsByPlayer(Game game, Player player) {
        int result = 0;
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            result = session
                    .createQuery("select sum(points) from Answer where game=:g and player=:p", Long.class)
                    .setParameter("g", game)
                    .setParameter("p", player)
                    .setMaxResults(1)
                    .uniqueResult()
                    .intValue();
            transaction.rollback();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return result;
    }
}
