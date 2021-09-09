package repository.hibernate;

import domain.Country;
import org.hibernate.Session;
import org.hibernate.query.Query;
import repository.CountryRepository;
import validator.Validator;

public class CountryHbRepository extends AbstractHbRepository<Long, Country> implements CountryRepository {
    protected CountryHbRepository(Validator<Long, Country> validator) {
        super(validator);
    }

    @Override
    protected Query<Country> getFindQuery(Session session, Long aLong) {
        return session.createQuery("from Country where id=:id", Country.class)
                .setParameter("id", aLong);
    }

    @Override
    protected Query<Country> getFindAllQuery(Session session) {
        return session.createQuery("from Country", Country.class);
    }
}
