package domain;

import junit.framework.TestCase;

import java.time.LocalDateTime;

public class TripTest extends TestCase {

    public void testGetDestination() {
        Trip trip = new Trip(1L, "Paris","Auto", LocalDateTime.MIN, 129, 20);
        assertEquals("Paris", trip.getDestination());
    }

    public void testSetDestination() {
        Trip trip = new Trip(1L, "Paris","Auto", LocalDateTime.MIN, 129, 20);
        trip.setDestination("London");
        assertEquals("London", trip.getDestination());
    }

    public void testGetTransportFirm() {
        Trip trip = new Trip(1L, "Paris","Auto", LocalDateTime.MIN, 129, 20);
        assertEquals("Auto", trip.getTransportFirm());
    }

    public void testSetTransportFirm() {
        Trip trip = new Trip(1L, "Paris","Auto", LocalDateTime.MIN, 129, 20);
        trip.setTransportFirm("Plane");
        assertEquals("Plane", trip.getTransportFirm());
    }

    public void testGetDepartureTime() {
        Trip trip = new Trip(1L, "Paris","Auto", LocalDateTime.MIN, 129, 20);
        assertEquals(LocalDateTime.MIN, trip.getDepartureTime());
    }

    public void testSetDepartureTime() {
        Trip trip = new Trip(1L, "Paris","Auto", LocalDateTime.MIN, 129, 20);
        trip.setDepartureTime(LocalDateTime.MAX);
        assertEquals(LocalDateTime.MAX, trip.getDepartureTime());
    }

    public void testGetPrice() {
        Trip trip = new Trip(1L, "Paris","Auto", LocalDateTime.MIN, 129, 20);
        assertEquals((double)129, trip.getPrice());
    }

    public void testSetPrice() {
        Trip trip = new Trip(1L, "Paris","Auto", LocalDateTime.MIN, 129, 20);
        trip.setPrice(19402.42);
        assertEquals(19402.42, trip.getPrice());
    }

    public void testGetSeats() {
        Trip trip = new Trip(1L, "Paris","Auto", LocalDateTime.MIN, 129, 20);
        assertEquals(20, trip.getSeats());
    }

    public void testSetSeats() {
        Trip trip = new Trip(1L, "Paris","Auto", LocalDateTime.MIN, 129, 20);
        trip.setSeats(9999);
        assertEquals(9999, trip.getSeats());
    }
}