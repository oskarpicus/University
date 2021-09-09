package repository.hibernate;

import domain.Game;
import domain.Player;
import domain.Round;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import repository.RoundRepository;
import validator.Validator;

public class RoundHbRepository extends AbstractHbRepository<Long, Round> implements RoundRepository {

    protected RoundHbRepository(Validator<Long, Round> validator) {
        super(validator);
    }

    @Override
    protected Query<Round> getFindQuery(Session session, Long aLong) {
        return session.createQuery("from Round where id=:id", Round.class)
                .setParameter("id", aLong);
    }

    @Override
    protected Query<Round> getFindAllQuery(Session session) {
        return session.createQuery("from Round ", Round.class);
    }

    @Override
    public Round getLatestRoundByPlayer(Game game, Player player) {
        Round round = null;
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            round = session.createQuery("from Round where game=:g and player=:p order by indexRound desc ", Round.class)
                    .setParameter("g", game)
                    .setParameter("p", player)
                    .setMaxResults(1)
                    .uniqueResult();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return round;
    }
}
