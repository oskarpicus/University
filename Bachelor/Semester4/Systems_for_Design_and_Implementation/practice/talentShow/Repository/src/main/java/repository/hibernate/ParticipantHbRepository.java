package repository.hibernate;

import domain.Participant;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import repository.ParticipantRepository;
import validator.Validator;


public class ParticipantHbRepository extends AbstractHbRepository<Long, Participant> implements ParticipantRepository {

    protected ParticipantHbRepository(Validator<Long, Participant> validator) {
        super(validator);
    }

    @Override
    protected Query<Participant> getFindQuery(Session session, Long aLong) {
        return session.createQuery("from Participant where id=:id", Participant.class)
                .setParameter("id", aLong);
    }

    @Override
    protected Query<Participant> getFindAllQuery(Session session) {
        return session.createQuery("from Participant", Participant.class);
    }

    @Override
    public Integer getNumberOfMarksByParticipant(Participant participant) {
        Transaction transaction = null;
        long result = 0;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            result = session
                    .createQuery("select count(m) from Participant p inner join Mark m on p=m.participant where p=:par", Long.class)
                    .setParameter("par", participant)
                    .setMaxResults(1)
                    .uniqueResult();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return (int)result;
    }
}
