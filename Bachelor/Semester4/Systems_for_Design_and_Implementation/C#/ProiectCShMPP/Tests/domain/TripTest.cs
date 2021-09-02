using System;
using NUnit.Framework;
using ProiectCShMPP.domain;

namespace Tests.domain
{
    [TestFixture]
    public class TripTest
    {
        [Test]
        public void TestDestination()
        {
            Trip trip = new Trip(1L)
            {
                Destination = "Cairo",
                DepartureTime = new DateTime(2021, 1, 1, 1, 1, 1),
                Price = 120,
                Seats = 10,
                TransportFirm = "A .SRL"
            };
            Assert.AreEqual("Cairo",trip.Destination);
            trip.Destination = "Paris";
            Assert.AreEqual("Paris",trip.Destination);
        }

        [Test]
        public void TestTransportFirm()
        {
            Trip trip = new Trip(1L)
            {
                Destination = "Cairo",
                DepartureTime = new DateTime(2021, 1, 1, 1, 1, 1),
                Price = 120,
                Seats = 10,
                TransportFirm = "A .SRL"
            };
            Assert.AreEqual("A .SRL",trip.TransportFirm);
            trip.TransportFirm = "B .SA";
            Assert.AreEqual("B .SA",trip.TransportFirm);
        }

        [Test]
        public void TestDepartureTime()
        {
            Trip trip = new Trip(1L)
            {
                Destination = "Cairo",
                DepartureTime = new DateTime(2021, 1, 1, 1, 1, 1),
                Price = 120,
                Seats = 10,
                TransportFirm = "A .SRL"
            };
            Assert.AreEqual(new DateTime(2021,1,1,1,1,1),trip.DepartureTime);
            trip.DepartureTime = new DateTime(2025, 5, 5, 5, 5, 5);
            Assert.AreEqual(new DateTime(2025, 5, 5, 5, 5, 5), trip.DepartureTime);
        }

        [Test]
        public void TestPrice()
        {
            Trip trip = new Trip(1L)
            {
                Destination = "Cairo",
                DepartureTime = new DateTime(2021, 1, 1, 1, 1, 1),
                Price = 120,
                Seats = 10,
                TransportFirm = "A .SRL"
            };
            Assert.AreEqual(120, trip.Price);
            trip.Price = 400;
            Assert.AreEqual(400, trip.Price);
        }

        [Test]
        public void TestSeats()
        {
            Trip trip = new Trip(1L)
            {
                Destination = "Cairo",
                DepartureTime = new DateTime(2021, 1, 1, 1, 1, 1),
                Price = 120,
                Seats = 10,
                TransportFirm = "A .SRL"
            };
            Assert.AreEqual(10, trip.Seats);
            trip.Seats = 99;
            Assert.AreEqual(99, trip.Seats);
        }
    }
}