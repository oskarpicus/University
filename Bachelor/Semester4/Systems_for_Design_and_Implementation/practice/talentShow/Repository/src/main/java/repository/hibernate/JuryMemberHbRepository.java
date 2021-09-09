package repository.hibernate;

import domain.JuryMember;
import domain.Mark;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import repository.JuryMemberRepository;
import validator.Validator;

import java.util.List;
import java.util.Optional;

public class JuryMemberHbRepository extends AbstractHbRepository<Long, JuryMember> implements JuryMemberRepository {

    protected JuryMemberHbRepository(Validator<Long, JuryMember> validator) {
        super(validator);
    }

    @Override
    protected Query<JuryMember> getFindQuery(Session session, Long aLong) {
        return session.createQuery("from JuryMember where id=:id", JuryMember.class)
                .setParameter("id", aLong);
    }

    @Override
    protected Query<JuryMember> getFindAllQuery(Session session) {
        return session.createQuery("from JuryMember", JuryMember.class);
    }

    @Override
    public Optional<JuryMember> findByUsernamePassword(String username, String password) {
        logger.traceEntry("Find By Username Password {} {}", username, password);
        Optional<JuryMember> result = Optional.empty();
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            JuryMember user = session.createQuery("from JuryMember where username=:username and password=:password", JuryMember.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .setMaxResults(1)
                    .uniqueResult();
            transaction.commit();
            result = user != null ? Optional.of(user) : result;
        } catch (Exception e){
            System.out.println(e.getMessage());
            logger.error(e);
            if (transaction != null)
                transaction.rollback();
        }
        logger.traceExit("Find By Username Password result {}", result);
        return result;
    }
}
