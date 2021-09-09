package services;

import domain.Game;
import domain.Player;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public interface Observer extends Remote {
    void startGame(Game game) throws RemoteException;

    void displayPositions(Map<Player, String> positions) throws RemoteException;

    void displayRanking(Map<Player, Integer> points) throws RemoteException;
}
