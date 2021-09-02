package domain;

import java.util.Objects;

public class Reservation extends Entity<Long>{
    private String client;
    private String phoneNumber;
    private int tickets;
    private Trip trip;
    private User user;

    public Reservation(Long aLong, String client, String phoneNumber, int tickets, Trip trip, User user) {
        super(aLong);
        this.client = client;
        this.phoneNumber = phoneNumber;
        this.tickets = tickets;
        this.trip = trip;
        this.user = user;
    }

    /**
     * Getter for the client of a Reservation
     * @return client: String, the client name of the current Reservation
     */
    public String getClient() {
        return client;
    }

    /**
     * Setter for the client of a Reservation
     * @param client: String, the desired client name
     * client name of the current Reservation is set to {@param client}
     */
    public void setClient(String client) {
        this.client = client;
    }

    /**
     * Getter for the phone number of a Reservation
     * @return phoneNumber: String, the phone number of the current Reservation
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Setter for the phone number of a Reservation
     * @param phoneNumber: String, the desired phone number
     * phone number of the current Reservation is set to {@param phoneNumber}
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Getter for the number of tickets of a Reservation
     * @return tickets: int, the number of tickets of the current Reservation
     */
    public int getTickets() {
        return tickets;
    }

    /**
     * Setter for the number of tickets of a Reservation
     * @param tickets: int, the desired number of tickets
     * number of tickets of the current Reservation is set to {@param tickets}
     */
    public void setTickets(int tickets) {
        this.tickets = tickets;
    }

    /**
     * Getter for the trip of a Reservation
     * @return trip: Trip, the trip of the current Reservation
     */
    public Trip getTrip() {
        return trip;
    }

    /**
     * Setter for the trip of a Reservation
     * @param trip: Trip, the desired trip
     * trip of the current Reservation is set to {@param trip}
     */
    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    /**
     * Getter for the user of a Reservation that made it
     * @return user: User, the user that made the current Reservation
     */
    public User getUser() {
        return user;
    }

    /**
     * Setter for the user of a Reservation that made it
     * @param user : User, the desired user
     * the user of the current Reservation is set to {@param user}
     */
    public void setUser(User user) {
        this.user = user;
    }


    @Override
    public String toString() {
        return "Reservation{" +
                "client='" + client + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", tickets=" + tickets +
                ", trip=" + trip +
                ", user=" + user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return tickets == that.tickets &&
                Objects.equals(client, that.client) &&
                Objects.equals(phoneNumber, that.phoneNumber) &&
                Objects.equals(trip, that.trip) &&
                Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(client, phoneNumber, tickets, trip, user);
    }
}
