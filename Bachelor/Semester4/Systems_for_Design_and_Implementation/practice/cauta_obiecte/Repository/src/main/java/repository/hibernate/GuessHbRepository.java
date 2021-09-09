package repository.hibernate;

import domain.Game;
import domain.Guess;
import domain.Player;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import repository.GuessRepository;
import validator.Validator;

import java.util.List;

public class GuessHbRepository extends AbstractHbRepository<Long, Guess> implements GuessRepository {

    protected GuessHbRepository(Validator<Long, Guess> validator) {
        super(validator);
    }

    @Override
    protected Query<Guess> getFindQuery(Session session, Long aLong) {
        return session.createQuery("from Guess where id=:id", Guess.class)
                .setParameter("id", aLong);
    }

    @Override
    protected Query<Guess> getFindAllQuery(Session session) {
        return session.createQuery("from Guess ", Guess.class);
    }

    @Override
    public List<Guess> getGuessesByGamePlayer(Game game, Player player) {
        List<Guess> result = null;
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            result = session.createQuery("from Guess where game=:g and guesser=:p", Guess.class)
                    .setParameter("g", game)
                    .setParameter("p", player)
                    .list();
            transaction.commit();
        } catch (Exception e){
            System.out.println(e.getMessage());
            logger.error(e);
            if (transaction != null)
                transaction.rollback();
        }
        return result;
    }
}
