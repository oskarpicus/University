package services;

import domain.Game;
import domain.Player;
import domain.Word;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface Observer extends Remote {
    void setOnlinePlayers(List<Player> players) throws RemoteException;

    void prepareStartGame(Game game) throws RemoteException;

    void setAnagram(String anagram) throws RemoteException;

    void displayResultsRound(Map<Player, Integer> points, Word correctWord) throws RemoteException;

    void endGame() throws RemoteException;
}
