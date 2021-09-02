package domain;

import java.time.LocalDateTime;

public class Trip extends Entity<Long>{
    private String destination;
    private String transportFirm;
    private LocalDateTime departureTime;
    private double price;
    private int seats;

    public Trip(Long aLong, String destination, String transportFirm, LocalDateTime departureTime, double price, int seats) {
        super(aLong);
        this.destination = destination;
        this.transportFirm = transportFirm;
        this.departureTime = departureTime;
        this.price = price;
        this.seats = seats;
    }

    /**
     * Getter for the destination of a Trip
     * @return destination: String, the destination of the current Trip
     */
    public String getDestination() {
        return destination;
    }

    /**
     * Setter for the destination of a Trip
     * @param destination: String, the desired destination
     * destination of the current Trip is set to {@param destination}
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }

    /**
     * Getter for the transport firm of a Trip
     * @return transportFirm: String, the transport firm of the current Trip
     */
    public String getTransportFirm() {
        return transportFirm;
    }

    /**
     * Setter for the transport firm of a Trip
     * @param transportFirm: String, the desired transport firm
     * transport firm of the current Trip is set to {@param transportFirm}
     */
    public void setTransportFirm(String transportFirm) {
        this.transportFirm = transportFirm;
    }

    /**
     * Getter for the departure time firm of a Trip
     * @return departureTime: LocalDateTime, the departure time of the current Trip
     */
    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    /**
     * Setter for the departure time of a Trip
     * @param departureTime: LocalDateTime, the desired departure time
     * departure time of the current Trip is set to {@param departureTime}
     */
    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    /**
     * Getter for the price of a Trip
     * @return price: double, the price of the current Trip
     */
    public double getPrice() {
        return price;
    }

    /**
     * Setter for the price of a Trip
     * @param price: double, the desired price
     * price of the current Trip is set to {@param price}
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Getter for the number of seats of a Trip
     * @return seats: int, the number of seats of the current Trip
     */
    public int getSeats() {
        return seats;
    }

    /**
     * Setter for the number of seats of a Trip
     * @param seats: int, the desired destination
     * number of seats of the current Trip is set to {@param seats}
     */
    public void setSeats(int seats) {
        this.seats = seats;
    }

    @Override
    public boolean equals(Object obj) {
        if(this==obj)
            return true;
        if(obj instanceof Trip){
            Trip other = (Trip) obj;
            return other.getId().equals(this.getId()) && other.getDestination().equals(this.getDestination())
                    && other.getTransportFirm().equals(this.getTransportFirm());
        }
        return false;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "destination='" + destination + '\'' +
                ", transportFirm='" + transportFirm + '\'' +
                ", departureTime=" + departureTime +
                ", price=" + price +
                ", seats=" + seats +
                '}';
    }
}
