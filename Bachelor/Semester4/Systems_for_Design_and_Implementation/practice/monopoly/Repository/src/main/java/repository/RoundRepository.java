package repository;

import domain.Game;
import domain.Player;
import domain.Round;

public interface RoundRepository extends Repository<Long, Round> {
    Round getLatestRoundByPlayer(Game game, Player player);
}
