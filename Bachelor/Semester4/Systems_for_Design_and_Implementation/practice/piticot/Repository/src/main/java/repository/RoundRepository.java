package repository;

import domain.Game;
import domain.Player;
import domain.Round;

import java.util.List;

public interface RoundRepository extends Repository<Long, Round> {
    List<Round> getRoundsByGameAndIndex(Game game, Integer index);

    Round getLatestRound(Game game, Player player);
}
