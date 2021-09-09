package repository.hibernate;

import domain.JuryMember;
import domain.Mark;
import domain.Participant;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import repository.MarkRepository;
import validator.Validator;

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
        return session.createQuery("from Mark", Mark.class);
    }

    @Override
    public boolean isMarkAdded(Participant participant, JuryMember juryMember) {
        boolean added = true;
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            Mark mark = session.createQuery("from Mark where participant=:p and juryMember=:j", Mark.class)
                    .setParameter("p", participant)
                    .setParameter("j", juryMember)
                    .setMaxResults(1)
                    .uniqueResult();
            added = mark != null;
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
        }
        return added;
    }
}
