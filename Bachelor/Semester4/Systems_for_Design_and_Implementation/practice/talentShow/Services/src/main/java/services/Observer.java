package services;

import domain.Mark;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Observer extends Remote {
    void markAdded(Mark mark) throws RemoteException;
}
