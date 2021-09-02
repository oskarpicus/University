package repository;

import domain.Trip;

import java.time.LocalDateTime;

public interface TripRepository extends Repository<Long, Trip> {
    /**
     * Method for finding trips that are in a particular destination and timestamp
     * @param destination: String, the desired destination of the trip
     * @param from: LocalDateTime, the beginning of the timestamp
     * @param to: LocalDateTime, the end of the timestamp
     * @return an {@code Iterable} of all the trips in {@param destination} between {@param from} and {@param to}
     */
    Iterable<Trip> findTripsByDestinationTime(String destination, LocalDateTime from, LocalDateTime to);
}
