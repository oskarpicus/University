package services;

import domain.Trip;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Observer extends Remote {
    void tripChanged(Trip trip) throws RemoteException;
}
