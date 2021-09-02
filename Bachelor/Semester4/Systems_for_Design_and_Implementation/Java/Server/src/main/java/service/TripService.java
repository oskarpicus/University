package service;

import domain.Trip;
import domain.dtos.TripDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.TripRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class TripService {
    private final TripRepository repository;
    private final static Logger logger = LogManager.getLogger();

    public TripService(TripRepository repository) {
        this.repository = repository;
    }

    /**
     * Method for obtaining all the trips in the database
     * @return a list of all the trips
     */
    public List<Trip> getAll(){
        logger.traceEntry();
        var result = StreamSupport.stream(repository.findAll().spliterator(), false).collect(Collectors.toList());
        logger.traceExit(result);
        return result;
    }

    /**
     * Method for obtaining all the trips in a given destination and timestamp
     * @param destination: String, the desired destination of the trip
     * @param from: LocalDateTime, the beginning of the timestamp
     * @param to: LocalDateTime, the end of the timestamp
     * @return a list of all the trips in {@param destination} and between {@param from} and {@param to}
     */
    public List<TripDTO> findTrips(String destination, LocalDateTime from, LocalDateTime to){
        logger.traceEntry("Entry find trips {} {} {}", destination, from, to);
        var result = StreamSupport.stream(repository.findTripsByDestinationTime(destination, from, to).spliterator(), false)
                .map(trip -> new TripDTO(trip.getTransportFirm(), trip.getDepartureTime(), trip.getPrice(), trip.getSeats()))
                .collect(Collectors.toList());
        logger.traceExit(result);
        return result;
    }

    /**
     * Method for updating a trip
     * @param trip: Trip, the trip to be updated
     * @return an {@code Optional}
     *            - null, if trip was successfully updated
     *            - trip, otherwise
     */
    public Optional<Trip> update(Trip trip){
        logger.traceEntry("Entry update Trip {}", trip);
        var result = repository.update(trip);
        logger.traceExit(result);
        return result;
    }
}
