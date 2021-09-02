using System;
using NUnit.Framework;
using ProiectCShMPP.domain;
using ProiectCShMPP.domain.validator;

namespace Tests.domain.validator
{
    [TestFixture]
    public class TripValidatorTest
    {
        private readonly IValidator<long, Trip> _validator = new TripValidator();
        
        [Test]
        public void TestValidate()
        {
            Trip trip = new Trip(1L)
            {
                Destination = "Cairo",
                DepartureTime = new DateTime(2021, 1, 1, 1, 1, 1),
                Price = 120,
                Seats = 10,
                TransportFirm = "A .SRL"
            };
            _validator.Validate(trip);
            try
            {
                _validator.Validate(new Trip(2L)
                {
                    Destination = "",
                    DepartureTime = DateTime.MaxValue,
                    Price = -32,
                    Seats = 10,
                    TransportFirm = "IO"
                });
                Assert.Fail();
            }
            catch (ValidationException e)
            {
                Assert.AreEqual("Invalid destination\nInvalid price\n", e.Message);
            }
        }
    }
}