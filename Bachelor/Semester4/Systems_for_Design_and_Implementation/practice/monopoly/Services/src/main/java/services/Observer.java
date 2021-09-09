package services;

import domain.Game;
import domain.Player;
import domain.Position;
import domain.Round;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;

public interface Observer extends Remote {
    void startGame(Game game) throws RemoteException;

    void displayOpponents(Collection<Player> players) throws RemoteException;

    void displayPositions(Collection<Position> positions) throws RemoteException;

    void displayRound(Round round) throws RemoteException;

    void allowToGenerateNumber() throws RemoteException;

    void displayRanking(List<Player> players) throws RemoteException;
}
