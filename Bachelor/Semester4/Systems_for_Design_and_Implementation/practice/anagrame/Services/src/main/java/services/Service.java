package services;

import domain.Game;
import domain.Player;
import domain.Round;

public interface Service {
    Player login(String username, String password);

    void addObserver(Player player, Observer observer);

    void removeObserver(Player player);

    Game startGame();

    void sendAnswer(Round round);
}
