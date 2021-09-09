package repository.hibernate;

import domain.Initialisation;
import org.hibernate.Session;
import org.hibernate.query.Query;
import repository.InitialisationRepository;
import validator.Validator;

public class InitialisationHbRepository extends AbstractHbRepository<Long, Initialisation> implements InitialisationRepository {

    protected InitialisationHbRepository(Validator<Long, Initialisation> validator) {
        super(validator);
    }

    @Override
    protected Query<Initialisation> getFindQuery(Session session, Long aLong) {
        return session.createQuery("from Initialisation where id=:id", Initialisation.class)
                .setParameter("id", aLong);
    }

    @Override
    protected Query<Initialisation> getFindAllQuery(Session session) {
        return session.createQuery("from Initialisation", Initialisation.class);
    }
}
