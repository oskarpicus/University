package services;

import domain.Game;
import domain.Guess;
import domain.Item;
import domain.Player;

import java.util.List;

public interface Service {
    Player login(String username, String password);

    void addObserver(Player player, Observer observer);

    void removeObserver(Player player);

    Game startGame(List<Item> items);

    void addGuess(Guess guess);
}
