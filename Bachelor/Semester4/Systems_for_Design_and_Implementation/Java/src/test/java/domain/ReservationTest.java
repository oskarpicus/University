package domain;

import junit.framework.TestCase;

import java.time.LocalDateTime;

public class ReservationTest extends TestCase {

    public void testGetClient() {
        Trip trip = new Trip(1L, "Paris","Auto", LocalDateTime.MIN, 129, 20);
        User user = new User(1L,"Ana","Apples");
        Reservation reservation = new Reservation(1L, "Anne","072",20, trip, user);
        assertEquals("Anne", reservation.getClient());
    }

    public void testSetClient() {
        Trip trip = new Trip(1L, "Paris","Auto", LocalDateTime.MIN, 129, 20);
        User user = new User(1L,"Ana","Apples");
        Reservation reservation = new Reservation(1L, "Anne","072",20, trip, user);
        reservation.setClient("Britney");
        assertEquals("Britney", reservation.getClient());
    }

    public void testGetPhoneNumber() {
        Trip trip = new Trip(1L, "Paris","Auto", LocalDateTime.MIN, 129, 20);
        User user = new User(1L,"Ana","Apples");
        Reservation reservation = new Reservation(1L, "Anne","072",20, trip, user);
        assertEquals("072", reservation.getPhoneNumber());
    }

    public void testSetPhoneNumber() {
        Trip trip = new Trip(1L, "Paris","Auto", LocalDateTime.MIN, 129, 20);
        User user = new User(1L,"Ana","Apples");
        Reservation reservation = new Reservation(1L, "Anne","072",20, trip, user);
        reservation.setPhoneNumber("032");
        assertEquals("032", reservation.getPhoneNumber());
    }

    public void testGetTickets() {
        Trip trip = new Trip(1L, "Paris","Auto", LocalDateTime.MIN, 129, 20);
        User user = new User(1L,"Ana","Apples");
        Reservation reservation = new Reservation(1L, "Anne","072",4, trip, user);
        assertEquals(4, reservation.getTickets());
    }

    public void testSetTickets() {
        Trip trip = new Trip(1L, "Paris","Auto", LocalDateTime.MIN, 129, 20);
        User user = new User(1L,"Ana","Apples");
        Reservation reservation = new Reservation(1L, "Anne","072",20, trip, user);
        reservation.setTickets(9);
        assertEquals(9, reservation.getTickets());
    }

    public void testGetTrip() {
        Trip trip = new Trip(1L, "Paris","Auto", LocalDateTime.MIN, 129, 20);
        User user = new User(1L,"Ana","Apples");
        Reservation reservation = new Reservation(1L, "Anne","072",20, trip, user);
        assertEquals(trip, reservation.getTrip());
    }

    public void testSetTrip() {
        Trip trip = new Trip(1L, "Paris","Auto", LocalDateTime.MIN, 129, 20);
        User user = new User(1L,"Ana","Apples");
        Reservation reservation = new Reservation(1L, "Anne","072",20, trip, user);
        reservation.setTrip(null);
        assertNotSame(trip, reservation.getTrip());
        assertNull(reservation.getTrip());
    }
}