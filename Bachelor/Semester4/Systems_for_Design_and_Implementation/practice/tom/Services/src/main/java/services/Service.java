package services;

import domain.Answer;
import domain.Player;

public interface Service {
    void startGame(Player player);

    void addObserver(Player player, Observer observer);

    Player login(String username, String password);

    void sendAnswer(Answer answer);

    void removeObserver(Player player);
}
