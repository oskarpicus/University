using Grpc.Core;
using proto;
using Server.proto;
using Services;
using Trip = ProiectCShMPP.domain.Trip;

namespace Protobuf.observer
{
    public class ObserverClient : IObserver
    {
        private readonly Observer.ObserverClient _client;
        
        public ObserverClient(string connection)
        {
            // connecting to server
            Channel channel = new Channel(connection, ChannelCredentials.Insecure);
            _client = new Observer.ObserverClient(channel);
        }
        
        public void TripChanged(Trip trip)
        {
            _client.tripChanged(ProtoUtils.GetTrip(trip));
        }
    }
}