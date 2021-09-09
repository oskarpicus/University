package repository;

import domain.Game;
import domain.Guess;
import domain.Player;

import java.util.List;

public interface GuessRepository extends Repository<Long, Guess> {
    List<Guess> getGuessesByGamePlayer(Game game, Player player);
}
