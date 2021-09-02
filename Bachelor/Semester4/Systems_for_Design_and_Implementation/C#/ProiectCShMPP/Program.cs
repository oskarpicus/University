using System;
using ProiectCShMPP.domain;
using ProiectCShMPP.domain.validator;
using ProiectCShMPP.repository;
using ProiectCShMPP.service;

namespace ProiectCShMPP
{
    internal class Program
    {
        public static void Main(string[] args)
        {
            Console.WriteLine("Hello");

            /*TestUser();
            TestTrip();
            TestReservation();*/
            TestService();
        }

        private static void TestUser()
        {
            UserDbRepository repository = new UserDbRepository(new UserValidator());
            Console.WriteLine(repository.Save(new User(1L)
            {
                Username = "Joe",
                Password = "Black"
            }));
            Console.WriteLine(repository.Find(1L));
            Console.WriteLine(repository.FindByUsernamePassword("Joe","Black"));
            Console.WriteLine(repository.Update(new User(1L)
            {
                Username = "Anne",
                Password = "AnneFlowers"
            }));
            Console.WriteLine(repository.Find(1L));
            Console.WriteLine(repository.Save(new User(2L)
            {
                Username = "Ion",
                Password = "AnBnCnDn"
            }));
            foreach (var user in repository.FindAll())
            {
                Console.WriteLine(user);
            }
        }

        private static void TestTrip()
        {
            TripDbRepository repository = new TripDbRepository(new TripValidator());
            Console.WriteLine(repository.Find(1L));
            Console.WriteLine(repository.Save(new Trip(1L)
            {
                Destination = "Cairo",
                TransportFirm = "A .SRL",
                DepartureTime = new DateTime(2020,12,4,10,10,10),
                Price = 129,
                Seats = 27
            }));
            Console.WriteLine(repository.Find(1L));
            foreach (var trip in repository.FindTripsByDestinationTime("Cairo",new DateTime(1999,1,1), new DateTime(2029,1,1)))
            {
                Console.WriteLine(trip);
            }

            Console.WriteLine(repository.Delete(2L));
            Console.WriteLine(repository.Delete(232322L));
            Console.WriteLine(repository.Update(new Trip(1L)
            {
                Destination = "Paris",
                TransportFirm = "BBB .SRL",
                Price = 190,
                Seats = 1,
                DepartureTime = DateTime.Now
            }));
        }

        private static void TestReservation()
        {
            User user = new User(1L)
            {
                Username = "Anne",
                Password = "AnneFlowers"
            };
            Trip trip = new Trip(1L)
            {
                Destination = "Cairo",
                TransportFirm = "A .SRL",
                DepartureTime = new DateTime(2020, 12, 4, 10, 10, 10),
                Price = 129,
                Seats = 27
            };
            ReservationDbRepository repository = new ReservationDbRepository(new ReservationValidator());
            Console.WriteLine(repository.Find(1L));
            Console.WriteLine(repository.Save(new Reservation(1L)
            {
                Client = "Bob",
                PhoneNumber = "088921",
                Tickets = 4,
                Trip = trip,
                User = user
            }));
            Console.WriteLine(repository.Find(1L));
            Console.WriteLine(repository.Update(new Reservation(1L)
            {
                Client = "Ellen",
                PhoneNumber = "121",
                Tickets = 2,
                Trip = trip,
                User = user
            }));
            Console.WriteLine(repository.Find(1L));
            Console.WriteLine(repository.Delete(33232L));
            Console.WriteLine(repository.Delete(1L));
        }

        private static void TestService()
        {
            var userRepository = new UserDbRepository(new UserValidator());
            var tripRepository = new TripDbRepository(new TripValidator());
            var reservationRepository = new ReservationDbRepository(new ReservationValidator());

            var service = new MasterService(new UserService(userRepository),
                new TripService(tripRepository), new ReservationService(reservationRepository));
            Console.WriteLine(service.FindUser("a","a", null));
            foreach (var allTrip in service.GetAllTrips())
            {
                Console.WriteLine(allTrip);
            }
        }
    }
}