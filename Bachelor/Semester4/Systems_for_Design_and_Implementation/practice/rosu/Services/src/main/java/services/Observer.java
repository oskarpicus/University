package services;

import domain.Card;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Observer extends Remote {

    void setPlayers(List<String> usernames) throws RemoteException;

    void setStartCards(Set<Card> cards) throws RemoteException;

    void displayResultRound(Set<Card> cards, String usernameWinner) throws RemoteException;

    /**
     * Method for displaying the result of a game
     * @param result: Map, key=username, value=number of cards obtained
     * @throws RemoteException, when something bad happens
     */
    void displayFinalResult(Map<String, Integer> result) throws RemoteException;

}
