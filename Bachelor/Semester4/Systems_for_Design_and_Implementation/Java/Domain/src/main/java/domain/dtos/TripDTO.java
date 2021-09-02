package domain.dtos;

import java.io.Serializable;
import java.time.LocalDateTime;

public class TripDTO implements Serializable {
    private final String transportFirm;
    private final LocalDateTime departureTime;
    private final double price;
    private final int seats;

    public TripDTO(String transportFirm, LocalDateTime departureTime, double price, int seats) {
        this.transportFirm = transportFirm;
        this.departureTime = departureTime;
        this.price = price;
        this.seats = seats;
    }

    public String getTransportFirm() {
        return transportFirm;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public double getPrice() {
        return price;
    }

    public int getSeats() {
        return seats;
    }
}
