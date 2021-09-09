package repository.hibernate;

import domain.Card;
import org.hibernate.Session;
import org.hibernate.query.Query;
import repository.CardRepository;
import validator.Validator;

public class CardHbRepository extends AbstractHbRepository<Long, Card> implements CardRepository {
    protected CardHbRepository(Validator<Long, Card> validator) {
        super(validator);
    }

    @Override
    protected Query<Card> getFindQuery(Session session, Long aLong) {
        return session.createQuery("from Card where id=:id", Card.class)
                .setParameter("id", aLong);
    }

    @Override
    protected Query<Card> getFindAllQuery(Session session) {
        return session.createQuery("from Card ", Card.class);
    }
}
