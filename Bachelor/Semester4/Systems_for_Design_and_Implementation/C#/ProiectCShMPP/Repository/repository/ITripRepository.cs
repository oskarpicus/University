using System;
using System.Collections.Generic;
using ProiectCShMPP.domain;

namespace ProiectCShMPP.repository
{
    public interface ITripRepository : IRepository<long,Trip>
    {
        /// <summary>
        /// Method for finding trips that are in a particular destination and timestamp
        /// </summary>
        /// <param name="destination">the desired destination of the trip</param>
        /// <param name="from">the beginning of the timestamp</param>
        /// <param name="to">the end of the timestamp</param>
        /// <returns>all the trips in destination between from and to</returns>
        IEnumerable<Trip> FindTripsByDestinationTime(String destination, DateTime from, DateTime to);
    }
}