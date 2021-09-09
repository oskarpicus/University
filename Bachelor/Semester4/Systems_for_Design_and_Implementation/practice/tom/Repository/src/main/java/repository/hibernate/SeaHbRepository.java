package repository.hibernate;

import domain.Sea;
import org.hibernate.Session;
import org.hibernate.query.Query;
import repository.SeaRepository;
import validator.Validator;

public class SeaHbRepository extends AbstractHbRepository<Long, Sea> implements SeaRepository {
    protected SeaHbRepository(Validator<Long, Sea> validator) {
        super(validator);
    }

    @Override
    protected Query<Sea> getFindQuery(Session session, Long aLong) {
        return session.createQuery("from Sea where id=:id", Sea.class)
                .setParameter("id", aLong);
    }

    @Override
    protected Query<Sea> getFindAllQuery(Session session) {
        return session.createQuery("from Sea", Sea.class);
    }
}
