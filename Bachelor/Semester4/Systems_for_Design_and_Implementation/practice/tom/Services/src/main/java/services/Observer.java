package services;

import domain.Game;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public interface Observer extends Remote {
    void canStartGame() throws RemoteException;

    void setLetter(String letter) throws RemoteException;

    void startGame(Game game) throws RemoteException;

    void displayResultsRound(Map<String, Integer> points) throws RemoteException;

    void displayRanking(Map<String, Integer> points) throws RemoteException;
}
