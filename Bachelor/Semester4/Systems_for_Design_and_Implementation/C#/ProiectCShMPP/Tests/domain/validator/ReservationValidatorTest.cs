using System;
using NUnit.Framework;
using ProiectCShMPP.domain;
using ProiectCShMPP.domain.validator;

namespace Tests.domain.validator
{
    [TestFixture]
    public class ReservationValidatorTest
    {
        private readonly IValidator<long, Reservation> _validator = new ReservationValidator();
        
        [Test]
        public void TestValidate()
        {
            User user = new User(1L)
            {
                Username = "Anne",
                Password = "anneMe"
            };
            Trip trip = new Trip(1L)
            {
                Destination = "Cairo",
                DepartureTime = new DateTime(2021, 1, 1, 1, 1, 1),
                Price = 120,
                Seats = 10,
                TransportFirm = "A .SRL"
            };
            Reservation reservation = new Reservation(1L)
            {
                Trip = trip,
                User = user,
                Client = "Anne",
                PhoneNumber = "082",
                Tickets = 1
            };
            _validator.Validate(reservation);
            try
            {
                _validator.Validate(new Reservation(2L)
                {
                    Client = "",
                    PhoneNumber = "",
                    Tickets = 19000,
                    Trip = trip,
                    User = user
                });
                Assert.Fail();
            }
            catch (ValidationException e)
            {
                Assert.AreEqual("Invalid client name\nInvalid phone number\nInvalid number of tickets\n", e.Message);
            }
        }
    }
}