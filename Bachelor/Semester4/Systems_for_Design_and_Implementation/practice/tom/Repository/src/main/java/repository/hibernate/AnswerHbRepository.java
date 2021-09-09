package repository.hibernate;

import domain.Answer;
import org.hibernate.Session;
import org.hibernate.query.Query;
import validator.Validator;

public class AnswerHbRepository extends AbstractHbRepository<Long, Answer> implements repository.AnswerRepository {
    protected AnswerHbRepository(Validator<Long, Answer> validator) {
        super(validator);
    }

    @Override
    protected Query<Answer> getFindQuery(Session session, Long aLong) {
        return session.createQuery("from Answer where id=:id", Answer.class)
                .setParameter("id", aLong);
    }

    @Override
    protected Query<Answer> getFindAllQuery(Session session) {
        return session.createQuery("from Answer", Answer.class);
    }
}
