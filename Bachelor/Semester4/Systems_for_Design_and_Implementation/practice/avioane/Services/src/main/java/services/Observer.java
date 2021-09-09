package services;

import domain.Game;
import domain.Guess;
import domain.Player;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Observer extends Remote {
    void startGame(Game game) throws RemoteException;

    void displayGuessOpponent(Guess guess) throws RemoteException;

    void displayWinner(Player player) throws RemoteException;
}
