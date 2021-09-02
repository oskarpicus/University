package domain.validator;

import domain.Reservation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReservationValidator implements Validator<Long, Reservation> {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public void validate(Reservation entity) {
        String errors="";
        if(!validateClient(entity))
            errors+="Invalid client name\n";
        if(!validatePhoneNumber(entity))
            errors+="Invalid phone number\n";
        if(!validateTickets(entity))
            errors+="Invalid number of tickets\n";
        if(!validateTrip(entity))
            errors+="Invalid trip\n";
        if(!errors.equals(""))
            throw logger.throwing(new ValidationException(errors));
    }

    /**
     * Method for verifying the client name of a reservation
     * @param reservation: Reservation, the reservation to check
     * @return - true, if the client name of {@param reservation} is not empty
     *         - false, otherwise
     */
    private boolean validateClient(Reservation reservation){
        return !reservation.getClient().equals("");
    }

    /**
     * Method for verifying the phone number of a reservation
     * @param reservation: Reservation, the reservation to check
     * @return - true, if the phone number of {@param reservation} is not empty
     *         - false, otherwise
     */
    private boolean validatePhoneNumber(Reservation reservation){
        return !reservation.getPhoneNumber().equals("");
    }

    /**
     * Method for verifying the number of tickets of a reservation
     * @param reservation: Reservation, the reservation to check
     * @return - true, if the number of tickets of {@param reservation} is between [1, number of seats of the trip]
     *         - false, otherwise
     */
    private boolean validateTickets(Reservation reservation){
        return reservation.getTickets()>0 && reservation.getTickets()<=reservation.getTrip().getSeats();
    }

    /**
     * Method for verifying the trip of a reservation
     * @param reservation: Reservation, the reservation to check
     * @return - true, if the trip of {param reservation} is not null
     *         - false, otherwise
     */
    private boolean validateTrip(Reservation reservation){
        return reservation.getTrip()!=null;
    }
}
