package services;

import domain.Game;
import domain.Player;
import domain.Position;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Observer extends Remote {
    void startGame(Game game) throws RemoteException;  // contains the positions

    void setOpponent(Player player) throws RemoteException;

    void displayRound(Integer n, Position myPosition, Position opponentPosition) throws RemoteException;

    void displayWinner(Player player) throws RemoteException;
}
