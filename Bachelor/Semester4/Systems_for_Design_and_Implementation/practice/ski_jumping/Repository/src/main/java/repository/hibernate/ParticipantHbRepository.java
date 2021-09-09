package repository.hibernate;

import domain.Participant;
import domain.Status;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import repository.ParticipantRepository;
import validator.Validator;

import java.util.List;

public class ParticipantHbRepository extends AbstractHbRepository<Long, Participant> implements ParticipantRepository {
    protected ParticipantHbRepository(Validator<Long, Participant> validator) {
        super(validator);
    }

    @Override
    public List<Participant> findParticipantByStatus(Status status) {
        Transaction transaction = null;
        List<Participant> participants = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            participants = session
                    .createQuery("from Participant where status=:s", Participant.class)
                    .setParameter("s", status)
                    .list();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return participants;
    }

    @Override
    protected Query<Participant> getFindQuery(Session session, Long aLong) {
        return session.createQuery("from Participant where id=:id", Participant.class)
                .setParameter("id", aLong);
    }

    @Override
    protected Query<Participant> getFindAllQuery(Session session) {
        return session.createQuery("from Participant ", Participant.class);
    }
}
