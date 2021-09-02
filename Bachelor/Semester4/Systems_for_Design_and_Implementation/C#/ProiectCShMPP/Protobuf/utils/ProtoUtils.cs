using System;
using Google.Protobuf.WellKnownTypes;
using proto;

namespace Server.proto
{
    public class ProtoUtils 
    {
        public static User GetUser(ProiectCShMPP.domain.User user)
        {
            return new User()
            {
                Id = user.Id,
                Password = user.Password,
                Username = user.Username
            };
        }

        public static ProiectCShMPP.domain.User GetUser(User user)
        {
            return new ProiectCShMPP.domain.User(user.Id)
            {
                Password = user.Password,
                Username = user.Username
            };
        }

        public static Trip GetTrip(ProiectCShMPP.domain.Trip trip)
        {
            return new Trip()
            {
                Id = trip.Id,
                DepartureTime = GetTimestamp(trip.DepartureTime),
                Destination = trip.Destination,
                Price = trip.Price,
                Seats = trip.Seats,
                TransportFirm = trip.TransportFirm
            };
        }

        public static ProiectCShMPP.domain.Trip GetTrip(Trip trip)
        {
            return new ProiectCShMPP.domain.Trip(trip.Id)
            {
                Destination = trip.Destination,
                TransportFirm = trip.TransportFirm,
                DepartureTime = GetDateTime(trip.DepartureTime),
                Price = trip.Price,
                Seats = trip.Seats
            };
        }

        public static Timestamp GetTimestamp(DateTime time)
        {
            TimeSpan span = time.Subtract(new DateTime(1970, 1, 1, 0, 0, 0));
            return new Timestamp{ Seconds = (long)span.TotalSeconds};
        }

        public static DateTime GetDateTime(Timestamp timestamp)
        {
            return timestamp.ToDateTime();
        }

        public static TripDTO GetTripDto(ProiectCShMPP.domain.dtos.TripDTO tripDto)
        {
            return new TripDTO()
            {
                TransportFirm = tripDto.TransportFirm,
                DepartureTime = GetTimestamp(tripDto.DepartureTime),
                Price = tripDto.Price,
                Seats = tripDto.Seats
            };
        }

        public static Reservation GetReservation(ProiectCShMPP.domain.Reservation reservation)
        {
            return new Reservation()
            {
                Id = reservation.Id,
                Client = reservation.Client,
                PhoneNumber = reservation.PhoneNumber,
                Tickets = reservation.Tickets,
                Trip = GetTrip(reservation.Trip),
                User = GetUser(reservation.User)
            };
        }
    }
}