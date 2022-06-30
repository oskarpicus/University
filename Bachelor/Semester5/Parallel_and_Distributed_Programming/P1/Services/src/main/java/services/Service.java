package services;

import domain.Vanzare;

public interface Service {
    void checkShows();

    /**
     * Method for buying a ticket
     * @param vanzare
     * @return true, if the {@param vanzare} is saved successfully, false, otherwise
     */
    boolean buyTicket(Vanzare vanzare);

    void addObserver(Observer observer);

    void notifyObserversServerCloses();
}
