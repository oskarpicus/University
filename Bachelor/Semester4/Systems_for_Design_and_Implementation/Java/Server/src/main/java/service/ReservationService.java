package service;

import domain.Reservation;
import domain.Trip;
import domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.ReservationRepository;

import java.util.Optional;

public class ReservationService {
    private final ReservationRepository repository;
    private final static Logger logger = LogManager.getLogger();

    public ReservationService(ReservationRepository repository) {
        this.repository = repository;
    }

    /**
     * Method for saving a reservation
     * @param client: String, the client that made the reservation
     * @param phoneNumber: String, the client's phone number
     * @param tickets: int, the desired number of tickets
     * @param trip: Trip, the trip to book
     * @param user: User, the user that registers
     * @return an {@code Optional}
     *          - null, if the reservation is successfully saved
     *          - the reservation, otherwise
     */
    public Optional<Reservation> save(String client, String phoneNumber, int tickets, Trip trip, User user){
        logger.traceEntry();
        Reservation reservation = new Reservation(1L, client, phoneNumber, tickets, trip, user);
        var result = repository.save(reservation);
        logger.traceExit(result);
        return result;
    }
}
