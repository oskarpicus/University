package domain.validator;

import domain.Trip;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TripValidator implements Validator<Long, Trip> {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public void validate(Trip entity) {
        String errors = "";
        if(!validateDestination(entity))
            errors+="Invalid destination\n";
        if(!validateTransportFirm(entity))
            errors+="Invalid transport firm\n";
        if(!validatePrice(entity))
            errors+="Invalid price\n";
        if(!validateSeats(entity))
            errors+="Invalid number of seats\n";
        if(!errors.equals(""))
            throw logger.throwing(new ValidationException(errors));
    }

    /**
     * Method for verifying the destination of a trip
     * @param trip: Trip, the trip to check
     * @return - true, if the destination of {@param trip} is not empty
     *         - false, otherwise
     */
    private boolean validateDestination(Trip trip){
        return !trip.getDestination().equals("");
    }

    /**
     * Method for verifying the transport firm of a trip
     * @param trip: Trip, the trip to check
     * @return - true, if the transport firm of {@param trip} is not empty
     *         - false, otherwise
     */
    private boolean validateTransportFirm(Trip trip){
        return !trip.getTransportFirm().equals("");
    }

    /**
     * Method for verifying the price of a trip
     * @param trip: Trip, the trip to check
     * @return - true, if the price of {@param trip} is strictly positive
     *         - false, otherwise
     */
    private boolean validatePrice(Trip trip){
        return trip.getPrice()>0;
    }

    /**
     * Method for verifying the number of seats of a trip
     * @param trip: Trip, the trip to check
     * @return - true, if the number of seats of {@param trip} is strictly positive
     *         - false, otherwise
     */
    private boolean validateSeats(Trip trip){
        return trip.getSeats()>=0;
    }
}
