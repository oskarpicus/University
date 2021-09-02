using System;
using NUnit.Framework;
using ProiectCShMPP.domain;

namespace Tests.domain
{
    [TestFixture]
    public class ReservationTest
    {
        private readonly User _user = new User(1L)
        {
            Username = "Anne",
            Password = "anneMe"
        };
        private readonly Trip _trip = new Trip(1L)
        {
            Destination = "Cairo",
            DepartureTime = new DateTime(2021, 1, 1, 1, 1, 1),
            Price = 120,
            Seats = 10,
            TransportFirm = "A .SRL"
        };
        
        [Test]
        public void TestClient()
        {
            Reservation reservation = new Reservation(1L)
            {
                Trip = _trip,
                User = _user,
                Client = "Steve",
                PhoneNumber = "082",
                Tickets = 2
            };
            Assert.AreEqual("Steve", reservation.Client);
            reservation.Client = "Bob";
            Assert.AreEqual("Bob",reservation.Client);
        }

        [Test]
        public void TestPhoneNumber()
        {
            Reservation reservation = new Reservation(1L)
            {
                Trip = _trip,
                User = _user,
                Client = "Steve",
                PhoneNumber = "082",
                Tickets = 2
            };
            Assert.AreEqual("082", reservation.PhoneNumber);
            reservation.PhoneNumber = "912";
            Assert.AreEqual("912", reservation.PhoneNumber);
        }

        [Test]
        public void TestTickets()
        {
            Reservation reservation = new Reservation(1L)
            {
                Trip = _trip,
                User = _user,
                Client = "Steve",
                PhoneNumber = "082",
                Tickets = 2
            };
            Assert.AreEqual(2, reservation.Tickets);
            reservation.Tickets = 12;
            Assert.AreEqual(12, reservation.Tickets);
        }

        [Test]
        public void TestUser()
        {
            Reservation reservation = new Reservation(1L)
            {
                Trip = _trip,
                User = _user,
                Client = "Steve",
                PhoneNumber = "082",
                Tickets = 2
            };
            Assert.AreEqual(_user, reservation.User);
            User user = new User(2L)
            {
                Password = "idk",
                Username = "Bibi"
            };
            reservation.User = user;
            Assert.AreEqual(user, reservation.User);
        }

        [Test]
        public void TestTrip()
        {
            Reservation reservation = new Reservation(1L)
            {
                Trip = _trip,
                User = _user,
                Client = "Steve",
                PhoneNumber = "082",
                Tickets = 2
            };
            Assert.AreEqual(_trip, reservation.Trip);
            Trip trip = new Trip(2L)
            {
                DepartureTime = new DateTime(1900, 7, 7, 7, 7, 7),
                Destination = "Maldives",
                Price = 1900,
                Seats = 19,
                TransportFirm = "D .SRL"
            };
            reservation.Trip = trip;
            Assert.AreEqual(trip, reservation.Trip);
        }
    }
}