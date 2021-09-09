package repository.hibernate;

import domain.Game;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import repository.GameRepository;
import validator.Validator;

import java.util.List;

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
    public List<Long> getRanking(Game game) {
        List<Long> players = null;
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            players = session
                    .createQuery("select r.player.id from Round r  " +
                            "where r.game=:g  " +
                            "group by r.player.id " +
                            "order by sum(r.received) desc ", Long.class)
                    .setParameter("g", game)
                    .list();
            transaction.rollback();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return players;
    }
}
