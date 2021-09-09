package services;

import domain.Answer;
import domain.Game;
import domain.Player;

public interface Service {
    Player login(String username, String password);

    void addObserver(Player player, Observer observer);

    void removeObserver(Player player);

    Game startGame(Player player);

    void addAnswer(Answer answer);
}
