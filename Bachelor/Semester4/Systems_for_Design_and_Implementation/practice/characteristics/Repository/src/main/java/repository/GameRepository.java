package repository;

import domain.Game;
import domain.Player;

public interface GameRepository extends Repository<Long, Game> {
    Integer getPointsByPlayer(Game game, Player player);
}
