using System;
using System.Collections.Generic;
using log4net;
using ProiectCShMPP.domain;
using ProiectCShMPP.domain.dtos;
using Services;

namespace ProiectCShMPP.service
{
    public class MasterService : IService
    {
        private readonly UserService _userService;
        private readonly TripService _tripService;
        private readonly ReservationService _reservationService;
        private static readonly ILog Logger = LogManager.GetLogger(typeof(MasterService));
        private readonly List<IObserver> _observers = new List<IObserver>();

        public MasterService(UserService userService, TripService tripService, ReservationService reservationService)
        {
            _userService = userService;
            _tripService = tripService;
            _reservationService = reservationService;
        }

        public User FindUser(String username, String password, IObserver client)
        {
            Logger.InfoFormat("Entry find user {0} {1}", username, password);
            var result = _userService.Find(username, password);
            if (result!=null && client!=null)
            {
                _observers.Add(client);
            }
            Logger.InfoFormat("Exit find user {0}", result);
            return result;
        }

        public IList<Trip> GetAllTrips()
        {
            Logger.InfoFormat("Entry Get All Trips");
            var result = _tripService.GetAll();
            Logger.InfoFormat("Exit Get All Trips {0}", result);
            return result;
        }

        public IList<TripDTO> FindTrips(String destination, DateTime from, DateTime to)
        {
            Logger.InfoFormat("Entry Find Trips {0} {1} {2}", destination, from, to);
            var result = _tripService.FindTrips(destination, from, to);
            Logger.InfoFormat("Exit Find Trips {0}", result);
            return result;
        }

        public Reservation BookTrip(String client, String phoneNumber, int tickets, Trip trip, User user)
        {
            Logger.InfoFormat("Entry Book trip {0} {1} {2} {3} {4}", client, phoneNumber, tickets, trip, user);
            var result = _reservationService.Save(client, phoneNumber, tickets, trip, user);
            if (result == null)
            {
                trip.Seats -= tickets;
                _tripService.Update(trip);
                _observers.ForEach(o => o.TripChanged(trip));
            }
            Logger.InfoFormat("Exit book trip {0}", result);
            return result;
        }

        public void LogOut(User user, IObserver observer)
        {
            if (observer!=null)
            {
                _observers.Remove(observer);
            }
        }
    }
}