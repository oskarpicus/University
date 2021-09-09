package repository;

import domain.Game;
import domain.Player;

import java.util.List;

public interface GameRepository extends Repository<Long, Game> {
    List<Game> getGamesByPlayer(Player player);

    Long getWinner(Game game);
}
