using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using Grpc.Core;
using proto;
using Protobuf.observer;
using Services;

namespace Server.proto
{
    public class ProtoService : Service.ServiceBase
    {
        private readonly IService _service;
        private readonly IDictionary<string, IObserver> _dictionary = new Dictionary<string, IObserver>();

        public ProtoService(IService service)
        {
            _service = service;
        }
        
        public override Task<FindUserResponse> findUser(FindUserRequest request, ServerCallContext context)
        {
            Console.WriteLine(context.Peer+" ");
            Console.WriteLine("Find User");
            IObserver observer = new ObserverClient(GetConnection(context.Peer, request.ObserverPort));
            ProiectCShMPP.domain.User user = _service.FindUser(request.Username, request.Password, observer);
            FindUserResponse response = new FindUserResponse();
            if (user!=null) // successfully logged in
            {
                response.User = ProtoUtils.GetUser(user);
                _dictionary.Add(context.Peer, observer);
            }
            else
            {
                response.ErrorMessage = @"Wrong credentials";
            }
            return Task.FromResult(response);
        }

        public override async Task getAllTrips(Empty request, IServerStreamWriter<Trip> responseStream, ServerCallContext context)
        {
            Console.WriteLine("Get All Trips");
            var trips = _service.GetAllTrips();
            foreach (var trip in trips)
            {
                await responseStream.WriteAsync(ProtoUtils.GetTrip(trip));
            }
        }

        public override async Task findTrips(TripDataDTO request, IServerStreamWriter<TripDTO> responseStream, ServerCallContext context)
        {
            Console.WriteLine("Find Trips");
            var trips = _service.FindTrips(
                request.Destination,
                ProtoUtils.GetDateTime(request.From),
                ProtoUtils.GetDateTime(request.To)
            );
            foreach (var tripDto in trips)
            {
                await responseStream.WriteAsync(ProtoUtils.GetTripDto(tripDto));
            }
        }

        public override Task<BookTripResponse> bookTrip(BookTripRequest request, ServerCallContext context)
        {
            Console.WriteLine("Book Trip");
            BookTripResponse response = new BookTripResponse();
            try
            {
                var result = _service.BookTrip(
                    request.Client, request.PhoneNumber, request.Tickets,
                    ProtoUtils.GetTrip(request.Trip),
                    ProtoUtils.GetUser(request.User)
                );
                var resultReservation = new Reservation()
                {
                    Client = request.Client,
                    PhoneNumber = request.PhoneNumber,
                    Tickets = request.Tickets,
                    Trip = request.Trip,
                    User = request.User
                };
                response.Reservation = resultReservation;
            }
            catch (Exception e)
            {
                response.ErrorMessage = e.Message;
            }
            return Task.FromResult(response);
        }

        public override Task<Empty> logout(User request, ServerCallContext context)
        {
            Console.WriteLine("Log out");
            _service.LogOut(ProtoUtils.GetUser(request), _dictionary[context.Peer]);
            return Task.FromResult(new Empty());
        }

        private string GetConnection(string peer, int port)
        {
            var ip = peer.Split(':')[1];
            return ip + ":" + port;
        }
    }
}