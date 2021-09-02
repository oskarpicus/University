package services;

import domain.Reservation;
import domain.Trip;
import domain.User;
import domain.dtos.TripDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface Service {

    /**
     * Method for finding a user by his/her username and password
     * @param username : String, the desired username
     * @param password : String, the desired password
     * @param client: Observer, the client that initiated the method
     * @return
     *          - null, if there is no user with that {@code username} and {@code password}
     *          - the user, otherwise
     *          {@code client} is added to an observer list
     */
    User findUser(String username, String password, Observer client) throws TourismAgencyException;

    /**
     * Method for obtaining all the trips in the database
     * @return a list of all the trips
     */
    List<Trip> getAllTrips() throws TourismAgencyException;

    /**
     * Method for obtaining all the trips in a given destination and timestamp
     * @param destination: String, the desired destination of the trip
     * @param from: LocalDateTime, the beginning of the timestamp
     * @param to: LocalDateTime, the end of the timestamp
     * @return a list of all the trips in {@code destination} and between {@code from} and {@code to}
     */
    List<TripDTO> findTrips(String destination, LocalDateTime from, LocalDateTime to) throws TourismAgencyException;

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
    Reservation bookTrip(String client, String phoneNumber, int tickets, Trip trip, User user) throws TourismAgencyException;

    /**
     * Method for logging out a user
     * @param user: User, user that wants to log out
     * @param observer
     * @throws TourismAgencyException
     */
    void logOut(User user, Observer observer) throws TourismAgencyException;
}
