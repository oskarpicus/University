package repository;

import domain.Game;
import domain.Item;
import domain.Player;

import java.util.List;

public interface ItemRepository extends Repository<Long, Item> {
    List<Item> getItemsByGameAndPlayer(Game game, Player player);
}
