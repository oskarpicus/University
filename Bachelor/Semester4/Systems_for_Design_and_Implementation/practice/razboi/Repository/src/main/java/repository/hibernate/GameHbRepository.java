package repository.hibernate;

import domain.Game;
import domain.Player;
import domain.Round;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.annotations.common.util.impl.Log;
import org.hibernate.query.Query;
import repository.GameRepository;
import validator.Validator;

public class GameHbRepository extends AbstractHbRepository<Long, Game> implements GameRepository {
    protected GameHbRepository(Validator<Long, Game> validator) {
        super(validator);
    }

    @Override
    protected Query<Game> getFindQuery(Session session, Long aLong) {
        return session.createQuery("from Game where id=:id", Game.class).setParameter("id", aLong);
    }

    @Override
    protected Query<Game> getFindAllQuery(Session session) {
        return session.createQuery("from Game ", Game.class);
    }

    @Override
    public Long findWinner(Game game) {
        Long playerId = null;
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            playerId = session.createQuery("select r.player.id from Round as r where r.game=:game group by r.player.id order by sum(r.cardsWon.size) desc", Long.class)
                    .setParameter("game", game)
                    .setMaxResults(1)
                    .uniqueResult();
            transaction.rollback();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return playerId;
    }
}
