package services;

import domain.Guess;
import domain.Player;
import domain.Word;


public interface Service {
    Player login(String username, String password);

    void addObserver(Player player, Observer observer);

    void removeObserver(Player player);

    String startGame(Word word);

    void addGuess(Guess guess);
}
