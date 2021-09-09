package services;

import domain.Answer;
import domain.Game;
import domain.Player;
import domain.Word;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Map;

public interface Observer extends Remote {
    void startGame(Game game) throws RemoteException;

    void setPlayers(Collection<Player> players) throws RemoteException;

    void setWord(Word word) throws RemoteException;

    void displayResultsRound(Collection<Answer> answers) throws RemoteException;

    void displayRanking(Map<Player, Integer> points) throws RemoteException;
}
