package service;

import domain.Reservation;
import domain.Trip;
import domain.User;
import domain.dtos.TripDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import services.Observer;
import services.Service;
import services.TourismAgencyException;

import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MasterService implements Service {
    private final UserService userService;
    private final TripService tripService;
    private final ReservationService reservationService;
    private final List<Observer> observers = new ArrayList<>();
    private final Logger logger = LogManager.getLogger();

    public MasterService(UserService userService, TripService tripService, ReservationService reservationService){
        this.userService = userService;
        this.tripService = tripService;
        this.reservationService = reservationService;
    }

    /**
     * Method for finding a user by his/her username and password
     * @param username : String, the desired username
     * @param password : String, the desired password
     * @return
     *          - null, if there is no user with that {@code username} and {@code password}
     *          - the user, otherwise
     */
    @Override
    public synchronized User findUser(String username, String password, Observer observer){
        logger.traceEntry("Entry find user {} {}", username, password);
        var result = userService.find(username, password);
        logger.traceExit(result);
        if (result.isPresent() && observer!=null)
            observers.add(observer);
        return result.orElse(null);
    }

    /**
     * Method for obtaining all the trips in the database
     * @return a list of all the trips
     */
    @Override
    public synchronized List<Trip> getAllTrips(){
        logger.traceEntry();
        var result = tripService.getAll();
        logger.traceExit(result);
        return result;
    }

    /**
     * Method for obtaining all the trips in a given destination and timestamp
     * @param destination: String, the desired destination of the trip
     * @param from: LocalDateTime, the beginning of the timestamp
     * @param to: LocalDateTime, the end of the timestamp
     * @return a list of all the trips in {@code destination} and between {@code from} and {@code to}
     */
    @Override
    public synchronized List<TripDTO> findTrips(String destination, LocalDateTime from, LocalDateTime to){
        logger.traceEntry("Find trips {} {} {}", destination, from, to);
        var result = tripService.findTrips(destination, from, to);
        logger.traceExit(result);
        return result;
    }

    /**
     * Method for saving a reservation
     * @param client: String, the client that made the reservation
     * @param phoneNumber: String, the client's phone number
     * @param tickets: int, the desired number of tickets
     * @param trip: Trip, the trip to book
     * @param user: User, the user that registers
     * @return
     *          - null, if the reservation is successfully saved
     *          - the reservation, otherwise
     */
    @Override
    public synchronized Reservation bookTrip(String client, String phoneNumber, int tickets, Trip trip, User user){
        logger.traceEntry("Entry book trip {} {} {} {} {}", client, phoneNumber, tickets, trip, user);
        Optional<Reservation> result = reservationService.save(client, phoneNumber, tickets, trip, user);
        if(result.isEmpty()){
            trip.setSeats(trip.getSeats()-tickets);
            tripService.update(trip);
            observers.forEach(observer -> {
                try {
                    observer.tripChanged(trip);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            });
        }
        logger.traceExit(result);
        return result.orElse(null);
    }

    @Override
    public synchronized void logOut(User user, Observer observer) throws TourismAgencyException {
        observers.remove(observer);
    }
}
