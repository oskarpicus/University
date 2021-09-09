package repository.hibernate;

import domain.Round;
import org.hibernate.Session;
import org.hibernate.query.Query;
import repository.RoundRepository;
import validator.Validator;

public class RoundHbRepository extends AbstractHbRepository<Long, Round> implements RoundRepository {
    protected RoundHbRepository(Validator<Long, Round> validator) {
        super(validator);
    }

    @Override
    protected Query<Round> getFindQuery(Session session, Long aLong) {
        return session.createQuery("from Round where id=:id", Round.class).setParameter("id", aLong);
    }

    @Override
    protected Query<Round> getFindAllQuery(Session session) {
        return session.createQuery("from Round", Round.class);
    }
}
