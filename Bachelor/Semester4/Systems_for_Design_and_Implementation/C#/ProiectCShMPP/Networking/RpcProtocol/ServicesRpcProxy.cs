using System;
using System.Collections.Generic;
using System.Net.Sockets;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters.Binary;
using System.Threading;
using log4net;
using ProiectCShMPP.domain;
using ProiectCShMPP.domain.dtos;
using Services;

namespace Networking.RpcProtocol
{
    public class ServicesRpcProxy : IService
    {
        private readonly string _host;
        private readonly int _port;
        private readonly Queue<Response> _responses = new Queue<Response>();
        private TcpClient _connection;
        private NetworkStream _stream;
        private IFormatter _formatter;
        private volatile bool _finished;
        private EventWaitHandle _eventWaitHandle;
        private IObserver _client;
        private static readonly ILog Logger = LogManager.GetLogger(typeof(ServicesRpcProxy));

        public ServicesRpcProxy(string host, int port)
        {
            _host = host;
            _port = port;
        }

        public User FindUser(string username, string password, IObserver client)
        {
            Logger.InfoFormat("Entry logging {0} {1}", username, password);
            InitialiseConnection();
            _finished = false;
            User user = new User(1L) {Username = username, Password = password};
            Request request = new Request() {Type = RequestType.Login, Data = user};
            SendRequest(request);
            Response response = ReadResponse();
            User result = null;
            if (response.Type == ResponseType.Ok)
            {
                user = (User) response.Data;
                _client = client;
                Logger.InfoFormat("{0} logged in", user);
                result = user;
            }
            else if (response.Type == ResponseType.Error)
            {
                CloseConnection();
                var error = new TourismAgencyException((string) response.Data);
                Logger.Error(error);
                throw error;
            }
            return result;
        }

        public IList<Trip> GetAllTrips()
        {
            Logger.InfoFormat("Entry get all trips");
            Request request = new Request() {Type = RequestType.GetTrips};
            SendRequest(request);
            Response response = ReadResponse();
            IList<Trip> result = null;
            if (response.Type == ResponseType.Ok)
            {
                result = (IList<Trip>) response.Data;
            }
            else if (response.Type == ResponseType.Error)
            {
                var error = new TourismAgencyException((string) response.Data);
                Logger.Error(error);
                throw error;
            }
            Logger.InfoFormat("Exit get all trips {0}", result);
            return result;
        }

        public IList<TripDTO> FindTrips(string destination, DateTime from, DateTime to)
        {
            Logger.InfoFormat("Entry find trips {0} {1} {2}", destination, from, to);
            TripDataDTO tripDataDto = new TripDataDTO() {Destination = destination, From = from, To = to};
            Request request = new Request() {Type = RequestType.SearchTrips, Data = tripDataDto};
            SendRequest(request);
            Response response = ReadResponse();
            IList<TripDTO> result = null;
            if (response.Type == ResponseType.Ok)
            {
                result = (IList<TripDTO>) response.Data;
            }
            else if (response.Type == ResponseType.Error)
            {
                var error = new TourismAgencyException((string) response.Data);
                Logger.Error(error);
                throw error;
            }
            Logger.InfoFormat("Exit find trips {0}", result);
            return result;
        }

        public Reservation BookTrip(string client, string phoneNumber, int tickets, Trip trip, User user)
        {
            Reservation reservation = new Reservation(1L)
            {
                Client = client,
                PhoneNumber = phoneNumber,
                Tickets = tickets,
                Trip = trip,
                User = user
            };
            Logger.InfoFormat("Entry Book trip {0}", reservation);
            Request request = new Request() {Type = RequestType.BookTrip, Data = reservation};
            SendRequest(request);
            Response response = ReadResponse();
            Reservation result = response.Type == ResponseType.Ok ? null : reservation;
            if (response.Type == ResponseType.Error)
            {
                var error = new TourismAgencyException((string) response.Data);
                Logger.Error(error);
                throw error;
            }
            return result;
        }

        public void LogOut(User user, IObserver observer)
        {
            Logger.InfoFormat("Entry logout {0} {1}", user, observer);
            Request request = new Request() {Type = RequestType.Logout, Data = user};
            SendRequest(request);
            Response response = ReadResponse();
            CloseConnection();
            if (response.Type == ResponseType.Error)
            {
                var error = new TourismAgencyException((string) response.Data);
                Logger.Error(error);
                throw error;
            }
            Logger.InfoFormat("Exit logout");
        }

        private void InitialiseConnection()
        {
            try
            {
                _connection = new TcpClient(_host, _port);
                _stream = _connection.GetStream();
                _formatter = new BinaryFormatter();
                _finished = false;
                _eventWaitHandle = new AutoResetEvent(false);
                new Thread(Run).Start();
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
                throw;
            }
        }

        private void CloseConnection()
        {
            _finished = true;
            try
            {
                _stream.Close();
                _connection.Close();
                _eventWaitHandle.Close();
                _client = null;
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
            }
        }

        private void SendRequest(Request request)
        {
            try
            {
                _formatter.Serialize(_stream, request);
                _stream.Flush();
            }
            catch (Exception e)
            {
                Logger.Error("Error sending request {0}", e);
                Console.WriteLine(e);
                throw;
            }
        }

        private Response ReadResponse()
        {
            Response response = null;
            try
            {
                _eventWaitHandle.WaitOne();
                response = _responses.Dequeue();
                Logger.InfoFormat("Read response {0}", response);
            }
            catch (Exception e)
            {
                Logger.Error("Failed to read response {0}", e);
                Console.WriteLine(e);
            }
            return response;
        }

        public virtual void Run()
        {
            Console.WriteLine("Run activated");
            while (!_finished)
            {
                try
                {
                    Response response = (Response) _formatter.Deserialize(_stream);
                    Logger.InfoFormat("Got Response {0}", response);
                    Console.WriteLine("Got Response {0}", response);
                    if (IsUpdateResponse(response))
                    {
                        HandleUpdate(response);
                    }
                    else
                    {
                        lock (_responses)
                        {
                            _responses.Enqueue(response);
                        }

                        _eventWaitHandle.Set();
                    }
                }
                catch (Exception e)
                {
                    Console.WriteLine("Error Reading "+e.Message);
                }
            }
        }

        private bool IsUpdateResponse(Response response)
        {
            return response.Type == ResponseType.UpdateSeats;
        }

        private void HandleUpdate(Response response)
        {
            if (response.Type==ResponseType.UpdateSeats)
            {
                _client.TripChanged((Trip)response.Data);
            }
        }
    }
}