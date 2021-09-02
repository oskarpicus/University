using System;

namespace ProiectCShMPP.domain.dtos
{
    [Serializable]
    public class TripDataDTO
    {
        public string Destination
        {
            get;
            set;
        }

        public DateTime From
        {
            get;
            set;
        }

        public DateTime To
        {
            get;
            set;
        }

        public override string ToString()
        {
            return @"TripDataDTO" + Destination + " " + From + " " + To;
        }
    }
}