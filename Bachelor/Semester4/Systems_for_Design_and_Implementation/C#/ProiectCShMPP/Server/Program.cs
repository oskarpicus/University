using System;
using System.Configuration;
using Grpc.Core;
using log4net;
using Networking.Utils;
using ProiectCShMPP.domain.validator;
using ProiectCShMPP.repository;
using ProiectCShMPP.service;
using proto;
using Server.proto;
using Services;

namespace Server
{
    internal class Program
    {
        private static readonly ILog Logger = LogManager.GetLogger(typeof(Program));
        public static void Main(string[] args)
        {
            Logger.InfoFormat("Begin");
            try
            {
                var settings = ConfigurationManager.AppSettings;
                string serverIp = settings["serverIp"];
                int port = Int32.Parse(settings["port"]);
                Console.WriteLine("{0} {1}", serverIp, port);
                // AbstractServer server = new RpcConcurrentServer(serverIp, port, GetService());
                // server.Start();
                ProtoService service = new ProtoService(GetService());

                Grpc.Core.Server server = new Grpc.Core.Server
                {
                    Services = {Service.BindService(service)},
                    Ports = { new ServerPort(serverIp, port, ServerCredentials.Insecure)}
                };
                Console.WriteLine("Starting server...");
                server.Start();
                Console.ReadKey();
                server.ShutdownAsync().Wait();
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
                throw;
            }
        }

        private static IService GetService()
        {
            var userRepository = new UserDbRepository(new UserValidator());
            var tripRepository = new TripDbRepository(new TripValidator());
            var reservationRepository = new ReservationDbRepository(new ReservationValidator());

            return new MasterService(new UserService(userRepository),
                new TripService(tripRepository), new ReservationService(reservationRepository));
        }
    }
}