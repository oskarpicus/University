package repository;

import domain.Answer;
import domain.Game;
import domain.Player;

import java.util.List;

public interface AnswerRepository extends Repository<Long, Answer> {
    List<Answer> getAnswersByGameAndRound(Game game, int indexRound);

    List<Answer> getAnswersByGameAndPlayer(Game game, Player player);
}
