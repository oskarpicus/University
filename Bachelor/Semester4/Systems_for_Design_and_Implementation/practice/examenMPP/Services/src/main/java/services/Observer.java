package services;

import domain.Game;
import domain.Player;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public interface Observer extends Remote {
    void canStartGame(Game game) throws RemoteException;

    void displayWordLetters(Map<Player, String> playerWord) throws RemoteException;

    void displayPoints(Map<Player, Integer> points) throws RemoteException;

    void displayRanking(Map<Player, Integer> points) throws RemoteException;
}