using System;
using System.Collections.Generic;
using System.Linq;
using log4net;
using ProiectCShMPP.domain;
using ProiectCShMPP.domain.dtos;
using ProiectCShMPP.repository;

namespace ProiectCShMPP.service
{
    public class TripService
    {
        private readonly ITripRepository _repository;
        private static readonly ILog Logger = LogManager.GetLogger(typeof(TripService));

        public TripService(ITripRepository repository)
        {
            _repository = repository;
        }

        /// <summary>
        /// Method for obtaining all the trips in the database
        /// </summary>
        /// <returns>list of all the trips</returns>
        public IList<Trip> GetAll()
        {
            Logger.InfoFormat("Entry Get All Trips");
            var result = _repository.FindAll().ToList();
            Logger.InfoFormat("Exit Get All Trips {0}", result);
            return result;
        }

        /// <summary>
        /// Method for finding trips that are in a particular destination and timestamp
        /// </summary>
        /// <param name="destination">the desired destination of the trip</param>
        /// <param name="from">the beginning of the timestamp</param>
        /// <param name="to">the end of the timestamp</param>
        /// <returns>list of all the trips in destination between from and to</returns>
        public IList<TripDTO> FindTrips(String destination, DateTime from, DateTime to)
        {
            Logger.InfoFormat("Entry Find trips {0} {1} {2}", destination, from, to);
            var result = _repository.FindTripsByDestinationTime(destination, from, to)
                .Select(trip => new TripDTO()
                {
                    DepartureTime = trip.DepartureTime,
                    TransportFirm = trip.TransportFirm,
                    Price = trip.Price,
                    Seats = trip.Seats
                }).ToList();
            Logger.InfoFormat("Exit Find trips {0}", result);
            return result;
        }

        /// <summary>
        /// Method for updating a trip
        /// </summary>
        /// <param name="trip">the trip to be updated</param>
        /// <returns>
        /// - null, if trip was successfully updated
        /// - trip, otherwise
        /// </returns>
        public Trip Update(Trip trip)
        {
            Logger.InfoFormat("Entry Update trip {0}", trip);
            var result = _repository.Update(trip);
            Logger.InfoFormat("Exit Update Trips {0}", result);
            return result;
        }
    }
}