using System;
using log4net;
using ProiectCShMPP.domain;
using ProiectCShMPP.repository;

namespace ProiectCShMPP.service
{
    public class ReservationService
    {
        private readonly IReservationRepository _repository;
        private static readonly ILog Logger = LogManager.GetLogger(typeof(ReservationService));

        public ReservationService(IReservationRepository repository)
        {
            _repository = repository;
        }

        /// <summary>
        /// Method for saving a reservation
        /// </summary>
        /// <param name="client">the client that made the reservation</param>
        /// <param name="phoneNumber">the client's phone number</param>
        /// <param name="tickets">the desired number of tickets</param>
        /// <param name="trip">the trip to book</param>
        /// <param name="user">the user that registers</param>
        /// <returns>
        /// - null, if the reservation is successfully saved
        /// - the reservation, otherwise
        /// </returns>
        public Reservation Save(String client, String phoneNumber, int tickets, Trip trip, User user)
        {
            Logger.InfoFormat("Entry Reservation Save {0} {1} {2} {3} {4}", client, phoneNumber, tickets, trip, user);
            Reservation reservation = new Reservation(1L)
            {
                Client = client,
                PhoneNumber = phoneNumber,
                Tickets = tickets,
                Trip = trip,
                User = user
            };
            var result = _repository.Save(reservation);
            Logger.InfoFormat("Exit Reservation Save {0}", result);
            return result;
        }
    }
}