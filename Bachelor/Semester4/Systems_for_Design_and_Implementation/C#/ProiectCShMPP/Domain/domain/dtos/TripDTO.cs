using System;

namespace ProiectCShMPP.domain.dtos
{
    [Serializable]
    public class TripDTO
    {
        public String TransportFirm
        {
            get;
            set;
        }

        public DateTime DepartureTime
        {
            get;
            set;
        }

        public double Price
        {
            get;
            set;
        }

        public int Seats
        {
            get;
            set;
        }

        public override string ToString()
        {
            return base.ToString()+" "+TransportFirm+" "+DepartureTime+" "+Price+" "+Seats;
        }
    }
}