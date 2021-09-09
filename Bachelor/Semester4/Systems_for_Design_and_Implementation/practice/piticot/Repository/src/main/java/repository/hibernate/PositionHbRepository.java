package repository.hibernate;

import domain.Game;
import domain.Position;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import repository.PositionRepository;
import validator.Validator;

public class PositionHbRepository extends AbstractHbRepository<Long, Position> implements PositionRepository {

    protected PositionHbRepository(Validator<Long, Position> validator) {
        super(validator);
    }

    @Override
    protected Query<Position> getFindQuery(Session session, Long aLong) {
        return session.createQuery("from Position where id=:id", Position.class)
                .setParameter("id", aLong);
    }

    @Override
    protected Query<Position> getFindAllQuery(Session session) {
        return session.createQuery("from Position ", Position.class);
    }

    @Override
    public Position getPositionByIndex(Game game, int index) {
        Position position = null;
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            position = session.createQuery("from Position where game=:g and indexPosition=:i", Position.class)
                    .setParameter("g", game)
                    .setParameter("i", index)
                    .setMaxResults(1)
                    .uniqueResult();
            transaction.commit();
        } catch (Exception e ) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return position;
    }
}
