package services;

import domain.Game;
import domain.Guess;
import domain.Plane;
import domain.Player;

public interface Service {
    Player login(String username, String password);

    void addObserver(Player player, Observer observer);

    void removeObserver(Player player);

    Game startGame(Plane plane);

    void addGuess(Guess guess);
}
