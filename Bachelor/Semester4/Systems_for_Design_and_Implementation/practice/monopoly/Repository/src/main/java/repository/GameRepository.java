package repository;

import domain.Game;

import java.util.List;

public interface GameRepository extends Repository<Long, Game> {
    List<Long> getRanking(Game game);
}
