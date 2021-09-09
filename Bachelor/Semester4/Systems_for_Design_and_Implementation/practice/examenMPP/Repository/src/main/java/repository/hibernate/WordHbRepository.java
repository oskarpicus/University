package repository.hibernate;

import domain.Word;
import org.hibernate.Session;
import org.hibernate.query.Query;
import repository.WordRepository;
import validator.Validator;

public class WordHbRepository extends AbstractHbRepository<Long, Word> implements WordRepository {

    protected WordHbRepository(Validator<Long, Word> validator) {
        super(validator);
    }

    @Override
    protected Query<Word> getFindQuery(Session session, Long aLong) {
        return session.createQuery("from Word where id=:id", Word.class)
                .setParameter("id", aLong);
    }

    @Override
    protected Query<Word> getFindAllQuery(Session session) {
        return session.createQuery("from Word ", Word.class);
    }
}
