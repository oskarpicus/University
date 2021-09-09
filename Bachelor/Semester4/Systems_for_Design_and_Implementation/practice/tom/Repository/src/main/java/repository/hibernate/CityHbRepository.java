package repository.hibernate;

import domain.City;
import org.hibernate.Session;
import org.hibernate.query.Query;
import repository.CityRepository;
import validator.Validator;

public class CityHbRepository extends AbstractHbRepository<Long, City> implements CityRepository {
    protected CityHbRepository(Validator<Long, City> validator) {
        super(validator);
    }

    @Override
    protected Query<City> getFindQuery(Session session, Long aLong) {
        return session.createQuery("from City where id=:id", City.class)
                .setParameter("id", aLong);
    }

    @Override
    protected Query<City> getFindAllQuery(Session session) {
        return session.createQuery("from City", City.class);
    }
}
