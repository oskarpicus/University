package repository.hibernate;

import domain.Game;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import repository.GameRepository;
import validator.Validator;

import java.util.ArrayList;
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
        return session.createQuery("from Game", Game.class);
    }

    @Override
    public List<Object[]> getRanking(Long id) {
        List<Object[]> results = new ArrayList<>();
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            results = session.createSQLQuery("select a.player_id, sum(a.player_id) from games g inner join answers a on g.id=a.game_id where g.id=:id group by a.player_id")
                    .setParameter("id", id)
                    .getResultList();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return results;
    }
}
