using ProiectCShMPP.domain;

namespace Services
{
    public interface IObserver
    {
        void TripChanged(Trip trip);
    }
}