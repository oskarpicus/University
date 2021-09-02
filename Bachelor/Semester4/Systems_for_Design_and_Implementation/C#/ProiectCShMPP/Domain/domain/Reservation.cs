using System;

namespace ProiectCShMPP.domain
{
    [Serializable]
    public class Reservation : Entity<long>
    {
        public Reservation(long id) : base(id)
        {
        }

        public String Client
        {
            get;
            set;
        }

        public String PhoneNumber
        {
            get;
            set;
        }

        public int Tickets
        {
            get;
            set;
        }

        public Trip Trip
        {
            get;
            set;
        }

        public User User
        {
            get;
            set;
        }

        public override string ToString()
        {
            return base.ToString()+" "+Client+" "+PhoneNumber+" "+Tickets+" "+Trip.Id+" "+User.Id;
        }

        public override bool Equals(object obj)
        {
            if (obj == null || GetType() != obj.GetType())
            {
                return false;
            }
            Reservation other = (Reservation) obj;
            return Id == other.Id && Client == other.Client && PhoneNumber == other.PhoneNumber &&
                   Tickets == other.Tickets && Trip == other.Trip && User == other.User;
        }

        public override int GetHashCode()
        {
            return base.GetHashCode();
        }
    }
}