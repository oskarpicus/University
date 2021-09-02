package utils;

import com.google.protobuf.Timestamp;
import domain.User;
import domain.dtos.TripDTO;
import proto.Reservation;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class ProtobufUtil {
    public static proto.User getUser(domain.User user){
        return proto.User
                .newBuilder()
                .setId(user.getId())
                .setUsername(user.getUsername())
                .setPassword(user.getPassword())
                .build();
    }

    public static domain.User getUser(proto.User user){
        return new User(
                user.getId(),
                user.getUsername(),
                user.getPassword()
        );
    }

    public static proto.Trip getTrip(domain.Trip trip){
        return proto.Trip
                .newBuilder()
                .setId(trip.getId())
                .setDestination(trip.getDestination())
                .setTransportFirm(trip.getTransportFirm())
                .setPrice(trip.getPrice())
                .setSeats(trip.getSeats())
                .setDepartureTime(getTimestamp(trip.getDepartureTime()))
                .build();
    }

    public static domain.Trip getTrip(proto.Trip trip){
        return new domain.Trip(
                trip.getId(),
                trip.getDestination(),
                trip.getTransportFirm(),
                ProtobufUtil.getLocalDateTime(trip.getDepartureTime()),
                trip.getPrice(),
                trip.getSeats()
        );
    }

    public static Timestamp getTimestamp(LocalDateTime time){
        Instant instant = time.toInstant(ZoneOffset.UTC);
        return Timestamp.newBuilder()
                .setSeconds(instant.getEpochSecond())
                .setNanos(instant.getNano())
                .build();
    }

    public static LocalDateTime getLocalDateTime(Timestamp timestamp){
        return LocalDateTime.ofEpochSecond(
                timestamp.getSeconds(),
                timestamp.getNanos(),
                ZoneOffset.UTC
        );
    }

    public static proto.TripDTO getTripDTO(domain.dtos.TripDTO trip){
        return proto.TripDTO.newBuilder()
                .setTransportFirm(trip.getTransportFirm())
                .setDepartureTime(getTimestamp(trip.getDepartureTime()))
                .setPrice(trip.getPrice())
                .setSeats(trip.getSeats())
                .build();
    }

    public static domain.dtos.TripDTO getTripDTO(proto.TripDTO trip){
        return new TripDTO(
                trip.getTransportFirm(),
                getLocalDateTime(trip.getDepartureTime()),
                trip.getPrice(),
                trip.getSeats()
        );
    }

    public static proto.Reservation getReservation(domain.Reservation reservation){
        return Reservation.newBuilder()
                .setId(reservation.getId())
                .setClient(reservation.getClient())
                .setPhoneNumber(reservation.getPhoneNumber())
                .setTickets(reservation.getTickets())
                .setTrip(getTrip(reservation.getTrip()))
                .setUser(getUser(reservation.getUser()))
                .build();
    }

    public static domain.Reservation getReservation(proto.Reservation reservation){
        return new domain.Reservation(
                reservation.getId(),
                reservation.getClient(),
                reservation.getPhoneNumber(),
                reservation.getTickets(),
                getTrip(reservation.getTrip()),
                getUser(reservation.getUser())
        );
    }
}
