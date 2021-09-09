package repository.hibernate;

import domain.Game;
import org.hibernate.Session;
import org.hibernate.query.Query;
import repository.GameRepository;
import validator.Validator;

public class GameHbRepository extends AbstractHbRepository<Long, Game> implements GameRepository {

    protected GameHbRepository(Validator<Long, Game> validator) {
        super(validator);
    }

    @Override
    protected Query<Game> getFindQuery(Session session, Long aLong) {
        return session.createQuery("from Game where id=:id", Game.class)
                .setParameter("id", aLong);
    }

    @Override
    protected Query<Game> getFindAllQuery(Session session) {
        return session.createQuery("from Game ", Game.class);
    }
}
