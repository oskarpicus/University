using System;

namespace ProiectCShMPP.domain
{
    [Serializable]
    public class Trip : Entity<long>
    {
        public Trip(long id) : base(id)
        {
        }

        public string Destination
        {
            get;
            set;
        }

        public string TransportFirm
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
            return base.ToString()+" "+Destination+" "+TransportFirm+" "+DepartureTime+" "+Price+" "+Seats;
        }

        public override bool Equals(object obj)
        {
            if (obj == null || GetType() != obj.GetType())
            {
                return false;
            }
            Trip other = (Trip) obj;
            return Id == other.Id && Destination == other.Destination && TransportFirm == other.TransportFirm &&
                   DepartureTime == other.DepartureTime && Price.Equals(other.Price) && Seats == other.Seats;
        }

        public override int GetHashCode()
        {
            return base.GetHashCode();
        }
    }
}