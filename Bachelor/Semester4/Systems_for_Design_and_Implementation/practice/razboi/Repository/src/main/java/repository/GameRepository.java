package repository;

import domain.Game;

public interface GameRepository extends Repository<Long, Game> {
    Long findWinner(Game game);
}
