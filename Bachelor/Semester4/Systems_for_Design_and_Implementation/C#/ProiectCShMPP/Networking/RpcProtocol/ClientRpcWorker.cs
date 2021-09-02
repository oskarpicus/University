using System;
using System.Collections.Generic;
using System.Net.Sockets;
using System.Reflection;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters.Binary;
using log4net;
using ProiectCShMPP.domain;
using ProiectCShMPP.domain.dtos;
using Services;

namespace Networking.RpcProtocol
{
    public class ClientRpcWorker : IObserver
    {
        private volatile bool _connected;
        private readonly NetworkStream _stream;
        private readonly TcpClient _client;
        private readonly IService _service;
        private readonly IFormatter _formatter;
        private static readonly ILog Logger = LogManager.GetLogger(typeof(ClientRpcWorker));

        public ClientRpcWorker(IService service, TcpClient client)
        {
            _service = service;
            _client = client;
            try
            {
                _connected = true;
                _stream = client.GetStream();
                _formatter = new BinaryFormatter();
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
                Console.WriteLine(e.Message);
            }
        }

        public void TripChanged(Trip trip)
        {
            Logger.InfoFormat("Entry Trip Changed {0}", trip);
            Response response = new Response()
            {
                Type = ResponseType.UpdateSeats,
                Data = trip
            };
            SendResponse(response);
            Logger.InfoFormat("Exit Trip Changed");
        }

        public void Run()
        {
            while (_connected)
            {
                try
                {
                    Request request = (Request) _formatter.Deserialize(_stream);
                    Logger.InfoFormat("Got request {0} from {1}", request, _client);
                    Console.WriteLine("Got request {0} from {1}", request, _client);
                    Response response = HandleRequest(request);
                    Logger.InfoFormat("Responded with {0}", response);
                    if (response!=null)
                    {
                        SendResponse(response);
                    }
                }
                catch (Exception e)
                {
                    Console.WriteLine(e);
                }
            }

            try
            {
                _stream.Close();
                _client.Close();
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
            }
        }

        private void SendResponse(Response response)
        {
            Logger.InfoFormat("Entry Sending Response {0}", response);
            _formatter.Serialize(_stream, response);
            _stream.Flush();
            Logger.InfoFormat("Exit Sending Response");
        }

        private Response HandleRequest(Request request)
        {
            Logger.InfoFormat("Entry Handle request {0}", request);
            object[] parameters = {request};
            Response result = null;
            try
            {
                string methodName = @"Handle" + request.Type;
                var method = typeof(ClientRpcWorker).GetMethod(methodName, BindingFlags.NonPublic | BindingFlags.Instance);
                if (method!=null)
                    result = (Response)method.Invoke(this, parameters);
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
            }
            Logger.InfoFormat("Exit Handle request {0}", result);
            return result;
        }

        private Response HandleLogin(Request request)
        {
            Logger.InfoFormat("Entry login {0}", request);
            Response result = new Response() {Type = ResponseType.Error};
            try
            {
                User user = (User) request.Data;
                user = _service.FindUser(user.Username, user.Password, this);
                if (user!=null)
                {
                    result = new Response()
                    {
                        Type = ResponseType.Ok,
                        Data = user
                    };
                }
                else
                {
                    _connected = false;
                    result.Data = "Wrong credentials";
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
                _connected = false;
                result.Data = e.Message;
            }
            Logger.InfoFormat("Exit Handle login {0}", result);
            return result;
        }

        private Response HandleLogout(Request request)
        {
            Logger.InfoFormat("Entry Handle Logout {0}", request);
            Response response;
            try
            {
                User user = (User) request.Data;
                _service.LogOut(user, this);
                response = new Response() {Type = ResponseType.Ok};
                _connected = false;
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
                response = new Response() {Type = ResponseType.Error, Data = e.Message};
            }
            Logger.InfoFormat("Exit Handle Logout {0}", response);
            return response;
        }

        private Response HandleGetTrips(Request request)
        {
            Logger.InfoFormat("Entry Handle Get Trips {0}", request);
            Response response;
            try
            {
                IList<Trip> trips = _service.GetAllTrips();
                response = new Response() {Type = ResponseType.Ok, Data = trips};
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
                response = new Response() {Type = ResponseType.Error, Data = e.Message};
            }
            Logger.InfoFormat("Exit Handle Get All Trips {0}", response);
            return response;
        }

        private Response HandleSearchTrips(Request request)
        {
            Logger.InfoFormat("Entry Handle Search Trips {0}", request);
            Response response;
            try
            {
                TripDataDTO tripDataDto = (TripDataDTO) request.Data;
                IList<TripDTO> trips = _service.FindTrips(tripDataDto.Destination, tripDataDto.From, tripDataDto.To);
                response = new Response() {Type = ResponseType.Ok, Data = trips};
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
                response = new Response() {Type = ResponseType.Error, Data = e.Message};
            }
            Logger.InfoFormat("Exit Handle Search Trips {0}", response);
            return response;
        }

        private Response HandleBookTrip(Request request)
        {
            Logger.InfoFormat("Entry Book Trip {0}", request);
            Response response;
            try
            {
                Reservation reservation = (Reservation) request.Data;
                Reservation result = _service.BookTrip(reservation.Client, reservation.PhoneNumber,
                    reservation.Tickets, reservation.Trip, reservation.User);
                response = result == null
                    ? new Response() {Type = ResponseType.Ok}
                    : new Response() {Type = ResponseType.Error};
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
                response = new Response() {Type = ResponseType.Error, Data = e.Message};
            }
            Logger.InfoFormat("Exit Book Trip {0}", response);
            return response;
        }
    }
}