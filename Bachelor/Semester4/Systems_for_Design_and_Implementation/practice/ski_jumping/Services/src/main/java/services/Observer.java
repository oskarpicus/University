package services;

import domain.Participant;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Observer extends Remote {
    void setParticipants(List<Participant> participants) throws RemoteException;

    void updateParticipant(Participant participant) throws RemoteException;
}
