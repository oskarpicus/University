package services;

import domain.Card;
import domain.Game;
import domain.Player;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Observer extends Remote {

    void setOnlinePlayers(List<Player> players) throws RemoteException;

    void setCards(Set<Card> cards) throws RemoteException;

    void startGame(Game game) throws RemoteException;

    void displayCardsChosen(Map<Player, Card> cards, String roundWinnerUsername) throws RemoteException;

    void displayWinner(Player winner) throws RemoteException;
}
