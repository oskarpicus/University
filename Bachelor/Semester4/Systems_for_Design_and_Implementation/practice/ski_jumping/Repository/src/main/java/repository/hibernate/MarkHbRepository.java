package repository.hibernate;

import domain.JuryMember;
import domain.Mark;
import domain.Participant;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import repository.MarkRepository;
import validator.Validator;

import java.util.Optional;

public class MarkHbRepository extends AbstractHbRepository<Long, Mark> implements MarkRepository {
    protected MarkHbRepository(Validator<Long, Mark> validator) {
        super(validator);
    }

    @Override
    protected Query<Mark> getFindQuery(Session session, Long aLong) {
        return session.createQuery("from Mark where id=:id", Mark.class)
                .setParameter("id", aLong);
    }

    @Override
    protected Query<Mark> getFindAllQuery(Session session) {
        return session.createQuery("from Mark ", Mark.class);
    }

    @Override
    public Optional<Mark> getMarkByJuryParticipant(JuryMember juryMember, Participant participant, Integer jumpNumber) {
        Mark result = null;
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            result = session.createQuery("from Mark where juryMember=:j and participant=:p and jumpNumber=:jn", Mark.class)
                    .setParameter("j", juryMember)
                    .setParameter("p", participant)
                    .setParameter("jn", jumpNumber)
                    .setMaxResults(1)
                    .uniqueResult();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return result == null ? Optional.empty() : Optional.of(result);
    }
}
