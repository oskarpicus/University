package repository.hibernate;

import domain.Guess;
import org.hibernate.Session;
import org.hibernate.query.Query;
import repository.GuessRepository;
import validator.Validator;

public class GuessHbRepository extends AbstractHbRepository<Long, Guess> implements GuessRepository {

    protected GuessHbRepository(Validator<Long, Guess> validator) {
        super(validator);
    }

    @Override
    protected Query<Guess> getFindQuery(Session session, Long aLong) {
        return session.createQuery("from Guess where id=:id", Guess.class)
                .setParameter("id", aLong);
    }

    @Override
    protected Query<Guess> getFindAllQuery(Session session) {
        return session.createQuery("from Guess ", Guess.class);
    }
}
