using System;
using System.Collections.Generic;
using ProiectCShMPP.domain;
using ProiectCShMPP.domain.dtos;

namespace Services
{
    public interface IService
    {
        /// <summary>
        /// Method for finding a user by his/her username and password
        /// </summary>
        /// <param name="username">the desired username</param>
        /// <param name="password">the desired password</param>
        /// <param name="client">the client that initiated the method</param>
        /// <returns>
        /// - null, if there is no user with that username and password
        /// - the user, otherwise
        /// client is added to an observer list
        /// </returns>
        User FindUser(string username, string password, IObserver client);

        /// <summary>
        /// Method for obtaining all the trips in the database
        /// </summary>
        /// <returns>a list of all the trips</returns>
        IList<Trip> GetAllTrips();

        /// <summary>
        /// Method for obtaining all the trips in a given destination and timestamp
        /// </summary>
        /// <param name="destination">the desired destination of the trip</param>
        /// <param name="from">the beginning of the timestamp</param>
        /// <param name="to">the end of the timestamp</param>
        /// <returns>a list of all the trips in destination and between from and to</returns>
        IList<TripDTO> FindTrips(string destination, DateTime from, DateTime to);

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
        Reservation BookTrip(string client, string phoneNumber, int tickets, Trip trip, User user);

        /// <summary>
        /// Method for logging out a user
        /// </summary>
        /// <param name="user">user that wants to log out</param>
        /// <param name="observer">the client that wants to log out</param>
        void LogOut(User user, IObserver observer);
    }
}