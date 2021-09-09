package repository.hibernate;

import domain.Answer;
import domain.Game;
import domain.Player;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import repository.AnswerRepository;
import validator.Validator;

import java.util.List;

public class AnswerHbRepository extends AbstractHbRepository<Long, Answer> implements AnswerRepository {

    protected AnswerHbRepository(Validator<Long, Answer> validator) {
        super(validator);
    }

    @Override
    protected Query<Answer> getFindQuery(Session session, Long aLong) {
        return session.createQuery("from Answer where id=:id", Answer.class)
                .setParameter("id", aLong);
    }

    @Override
    protected Query<Answer> getFindAllQuery(Session session) {
        return session.createQuery("from Answer ", Answer.class);
    }

    @Override
    public List<Answer> getAnswersByGameAndRound(Game game, int indexRound) {
        List<Answer> answers = null;
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            answers = session
                    .createQuery("from Answer where game=:g and indexRound=:i", Answer.class)
                    .setParameter("g", game)
                    .setParameter("i", indexRound)
                    .list();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return answers;
    }

    @Override
    public List<Answer> getAnswersByGameAndPlayer(Game game, Player player) {
        List<Answer> answers = null;
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            answers = session
                    .createQuery("from Answer where game=:g and player=:p", Answer.class)
                    .setParameter("g", game)
                    .setParameter("p", player)
                    .list();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return answers;
    }
}
