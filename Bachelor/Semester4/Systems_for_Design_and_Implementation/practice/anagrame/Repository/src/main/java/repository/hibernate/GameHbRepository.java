package repository.hibernate;

import domain.Game;
import domain.Player;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import repository.GameRepository;
import validator.Validator;

import java.math.BigInteger;
import java.util.List;

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
        return session.createQuery("from Game", Game.class);
    }

    @Override
    public List<Game> getGamesByPlayer(Player player) {
        Transaction transaction = null;
        List<Game> games = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            games = session.createQuery("select distinct g from Game g inner join Round r on g=r.game where r.player=:player", Game.class)
                    .setParameter("player", player)
                    .list();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null)
                transaction.rollback();
        }
        return games;
    }

    @Override
    public Long getWinner(Game game) {
        Transaction transaction = null;
        Long player = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            player = ((BigInteger) session
                    .createSQLQuery("select r.player_id " +
                            "from games g inner join rounds r on g.id=r.game_id " +
                            "where g.id=:id " +
                            "group by r.player_id " +
                            "order by sum(r.points) desc")
                    .setParameter("id", game.getId())
                    .setMaxResults(1)
                    .uniqueResult()).longValue();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null)
                transaction.rollback();
        }
        return player;
    }
}
