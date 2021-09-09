package repository.hibernate;

import domain.Game;
import domain.Item;
import domain.Player;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import repository.ItemRepository;
import validator.Validator;

import java.util.List;


public class ItemHbRepository extends AbstractHbRepository<Long, Item> implements ItemRepository {

    protected ItemHbRepository(Validator<Long, Item> validator) {
        super(validator);
    }

    @Override
    protected Query<Item> getFindQuery(Session session, Long aLong) {
        return session.createQuery("from Item where id=:id", Item.class)
                .setParameter("id", aLong);
    }

    @Override
    protected Query<Item> getFindAllQuery(Session session) {
        return session.createQuery("from Item ", Item.class);
    }

    @Override
    public List<Item> getItemsByGameAndPlayer(Game game, Player player) {
        List<Item> result = null;
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            result = session.createQuery("from Item where game=:g and player=:p", Item.class)
                    .setParameter("g", game)
                    .setParameter("p", player)
                    .list();
            transaction.commit();
        } catch (Exception e){
            System.out.println(e.getMessage());
            logger.error(e);
            if (transaction != null)
                transaction.rollback();
        }
        return result;
    }
}
