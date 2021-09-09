package services;

import domain.Card;
import domain.Player;

public interface Service {
    Player login(String username, String password);

    void startGame(Long playerId, Observer observer);

    void sendCard(Long playerId, Card card);
}
