package repository.hibernate;

import domain.Plane;
import org.hibernate.Session;
import org.hibernate.query.Query;
import repository.PlaneRepository;
import validator.Validator;

public class PlaneHbRepository extends AbstractHbRepository<Long, Plane> implements PlaneRepository {

    protected PlaneHbRepository(Validator<Long, Plane> validator) {
        super(validator);
    }

    @Override
    protected Query<Plane> getFindQuery(Session session, Long aLong) {
        return session.createQuery("from Plane where id=:id", Plane.class)
                .setParameter("id", aLong);
    }

    @Override
    protected Query<Plane> getFindAllQuery(Session session) {
        return session.createQuery("from Plane ", Plane.class);
    }
}
