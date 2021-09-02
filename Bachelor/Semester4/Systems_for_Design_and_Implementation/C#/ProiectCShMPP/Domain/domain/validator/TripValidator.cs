using System.IO;
using log4net;
using log4net.Config;

namespace ProiectCShMPP.domain.validator
{
    public class TripValidator : IValidator<long,Trip>
    {
        private static readonly ILog _logger = LogManager.GetLogger(typeof(TripValidator));

        static TripValidator()
        {
            XmlConfigurator.Configure(new FileInfo("log4net.xml"));
        }
        
        public void Validate(Trip entity)
        {
            string errors = "";
            if (!ValidateDestination(entity))
                errors += "Invalid destination\n";
            if (!ValidateTransportFirm(entity))
                errors += "Invalid transport firm\n";
            if (!ValidatePrice(entity))
                errors += "Invalid price\n";
            if (!ValidateSeats(entity))
                errors += "Invalid number of seats\n";
            if (errors != "")
            {
                _logger.Error("Trip Validation: "+errors);
                throw new ValidationException(errors);
            }
        }

        /// <summary>
        /// Method for verifying the destination of a trip
        /// </summary>
        /// <param name="trip"> the trip to check </param>
        /// <returns>
        /// - true, if the destination of trip is not empty
        /// - false, otherwise
        /// </returns>
        private bool ValidateDestination(Trip trip)
        {
            return trip.Destination != "";
        }

        /// <summary>
        /// Method for verifying the transport firm of a trip
        /// </summary>
        /// <param name="trip"> the trip to check </param>
        /// <returns>
        /// - true, if the transport firm of trip is not empty
        /// - false, otherwise
        /// </returns>
        private bool ValidateTransportFirm(Trip trip)
        {
            return trip.TransportFirm != "";
        }

        /// <summary>
        /// Method for verifying the price of a trip
        /// </summary>
        /// <param name="trip"> the trip to check </param>
        /// <returns>
        /// - true, if the price of trip is strictly positive
        /// - false, otherwise
        /// </returns>
        private bool ValidatePrice(Trip trip)
        {
            return trip.Price > 0;
        }

        /// <summary>
        /// Method for verifying the number of seats of a trip
        /// </summary>
        /// <param name="trip"> the trip to check </param>
        /// <returns>
        /// - true, if the number of seats of trip is strictly positive
        /// - false, otherwise
        /// </returns>
        private bool ValidateSeats(Trip trip)
        {
            return trip.Seats >= 0;
        }
    }
}