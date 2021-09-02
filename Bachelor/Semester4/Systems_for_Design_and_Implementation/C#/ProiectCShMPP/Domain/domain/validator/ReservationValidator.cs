using System.IO;
using log4net;
using log4net.Config;

namespace ProiectCShMPP.domain.validator
{
    public class ReservationValidator : IValidator<long,Reservation>
    {
        private static readonly ILog _logger = LogManager.GetLogger(typeof(ReservationValidator));

        static ReservationValidator()
        {
            XmlConfigurator.Configure(new FileInfo("log4net.xml"));
        }
        
        public void Validate(Reservation entity)
        {
            string errors = "";
            if (!ValidateClient(entity))
                errors += "Invalid client name\n";
            if (!ValidatePhoneNumber(entity))
                errors += "Invalid phone number\n";
            if(!ValidateTickets(entity))
                errors+="Invalid number of tickets\n";
            if (!ValidateTrip(entity))
                errors += "Invalid trip\n";
            if (errors != "")
            {
                _logger.Error("Reservation Validation: "+errors);
                throw new ValidationException(errors);
            }
        }

        /// <summary>
        /// Method for verifying the client name of a reservation
        /// </summary>
        /// <param name="reservation"> the reservation to check </param>
        /// <returns>
        /// - true, if the client name of reservation is not empty
        /// - false, otherwise
        /// </returns>
        private bool ValidateClient(Reservation reservation)
        {
            return reservation.Client != "";
        }

        /// <summary>
        /// Method for verifying the phone number of a reservation
        /// </summary>
        /// <param name="reservation"> the reservation to check </param>
        /// <returns>
        /// - true, if the phone number of reservation is not empty
        /// - false, otherwise
        /// </returns>
        private bool ValidatePhoneNumber(Reservation reservation)
        {
            return reservation.PhoneNumber != "";
        }

        /// <summary>
        /// Method for verifying the number of tickets of a reservation
        /// </summary>
        /// <param name="reservation"> the reservation to check </param>
        /// <returns>
        /// - true, if the number of tickets of param reservation is between [1, number of seats of the trip]
        /// - false, otherwise
        /// </returns>
        private bool ValidateTickets(Reservation reservation)
        {
            return reservation.Tickets > 0 && reservation.Tickets <= reservation.Trip.Seats;
        }

        /// <summary>
        /// Method for verifying the trip of a reservation
        /// </summary>
        /// <param name="reservation"> the reservation to check </param>
        /// <returns>
        /// - true, if the trip of reservation is not null
        /// - false, otherwise
        /// </returns>
        private bool ValidateTrip(Reservation reservation)
        {
            return reservation.Trip != null;
        }
    }
}