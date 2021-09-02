package domain.dtos;

import java.io.Serializable;
import java.time.LocalDateTime;

public class TripDataDTO implements Serializable {
    private final String destination;
    private final LocalDateTime from;
    private final LocalDateTime to;

    public TripDataDTO(String destination, LocalDateTime from, LocalDateTime to) {
        this.destination = destination;
        this.from = from;
        this.to = to;
    }

    public String getDestination() {
        return destination;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTo() {
        return to;
    }
}
