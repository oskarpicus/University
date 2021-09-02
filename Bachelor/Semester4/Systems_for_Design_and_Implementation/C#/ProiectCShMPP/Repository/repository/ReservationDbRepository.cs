using System;
using System.Data;
using ProiectCShMPP.domain;
using ProiectCShMPP.domain.validator;

namespace ProiectCShMPP.repository
{
    public class ReservationDbRepository : AbstractDBRepository<long, Reservation>, IReservationRepository
    {
        public ReservationDbRepository(IValidator<long, Reservation> validator) : base(validator)
        {
        }

        protected override IDbCommand GetSaveCommand(IDbConnection connection, Reservation entity)
        {
            var command = connection.CreateCommand();
            command.CommandText =
                "INSERT INTO reservations(client, phonenumber, tickets, tripid, userid) VALUES (@client, @phonenumber, @tickets, @tripdid, @userid);";

            var parameter = command.CreateParameter();
            parameter.ParameterName = "@client";
            parameter.Value = entity.Client;
            command.Parameters.Add(parameter);
            
            parameter = command.CreateParameter();
            parameter.ParameterName = "@phonenumber";
            parameter.Value = entity.PhoneNumber;
            command.Parameters.Add(parameter);
            
            parameter = command.CreateParameter();
            parameter.ParameterName = "@tickets";
            parameter.Value = entity.Tickets;
            command.Parameters.Add(parameter);
            
            parameter = command.CreateParameter();
            parameter.ParameterName = "@tripdid";
            parameter.Value = entity.Trip.Id;
            command.Parameters.Add(parameter);
            
            parameter = command.CreateParameter();
            parameter.ParameterName = "@userid";
            parameter.Value = entity.User.Id;
            command.Parameters.Add(parameter);
            
            return command;
        }

        protected override IDbCommand GetDeleteCommand(IDbConnection connection, long id)
        {
            var command = connection.CreateCommand();
            command.CommandText = "DELETE FROM reservations WHERE id=@id;";
            var parameter = command.CreateParameter();
            parameter.ParameterName = "@id";
            parameter.Value = id;
            command.Parameters.Add(parameter);
            return command;
        }

        protected override IDbCommand GetUpdateCommand(IDbConnection connection, Reservation entity)
        {
            var command = connection.CreateCommand();
            command.CommandText =
                "UPDATE reservations SET client = @client, phonenumber = @phonenumber, tickets = @tickets, tripid = @tripid, userid = @userid WHERE id = @id;";

            var parameter = command.CreateParameter();
            parameter.ParameterName = "@client";
            parameter.Value = entity.Client;
            command.Parameters.Add(parameter);
            
            parameter = command.CreateParameter();
            parameter.ParameterName = "@phonenumber";
            parameter.Value = entity.PhoneNumber;
            command.Parameters.Add(parameter);
            
            parameter = command.CreateParameter();
            parameter.ParameterName = "@tickets";
            parameter.Value = entity.Tickets;
            command.Parameters.Add(parameter);
            
            parameter = command.CreateParameter();
            parameter.ParameterName = "@tripid";
            parameter.Value = entity.Trip.Id;
            command.Parameters.Add(parameter);
            
            parameter = command.CreateParameter();
            parameter.ParameterName = "@userid";
            parameter.Value = entity.User.Id;
            command.Parameters.Add(parameter);
            
            parameter = command.CreateParameter();
            parameter.ParameterName = "@id";
            parameter.Value = entity.Id;
            command.Parameters.Add(parameter);

            return command;
        }

        protected override IDbCommand GetFindCommand(IDbConnection connection, long id)
        {
            var command = connection.CreateCommand();
            command.CommandText =
                "SELECT r.id, client, phonenumber, tickets, tripid, userid, username, password, destination, transportfirm," +
                " departuretime, price, seats FROM reservations r INNER JOIN users u on r.userid=u.id INNER JOIN trips t on r.tripid=t.id" +
                " WHERE r.id=@id;";
            var parameter = command.CreateParameter();
            parameter.ParameterName = "@id";
            parameter.Value = id;
            command.Parameters.Add(parameter);
            return command;
        }

        protected override IDbCommand GetFindAllCommand(IDbConnection connection)
        {
            var command = connection.CreateCommand();
            command.CommandText =
                "SELECT r.id, client, phonenumber, tickets, tripid, userid, username, password, destination, transportfirm," +
                " departuretime, price, seats FROM reservations r INNER JOIN users u on r.userid=u.id INNER JOIN trips t on r.tripid=t.id;";
            return command;
        }

        protected override Reservation ExtractEntity(IDataReader reader)
        {
            _logger.InfoFormat("Entry Extract Entity");
            long id = (long) reader["id"];
            string client = (string) reader["client"];
            string phoneNumber = (string) reader["phonenumber"];
            int tickets = (int) reader["tickets"];

            long tripId = (long) reader["tripid"];
            Trip trip = new Trip(tripId)
            {
                Destination = (string) reader["destination"],
                TransportFirm = (string) reader["transportfirm"],
                DepartureTime = (DateTime) reader["departuretime"],
                Price = (float) (double) reader["price"],
                Seats = (int) reader["seats"]
            };

            long userId = (long) reader["userid"];
            User user = new User(userId)
            {
                Username = (string) reader["username"],
                Password = (string) reader["password"]
            };

            var reservation = new Reservation(id)
            {
                Client = client,
                PhoneNumber = phoneNumber,
                Tickets = tickets,
                Trip = trip,
                User = user
            };
            _logger.InfoFormat("Extracted {0}", reservation);
            return reservation;
        }
    }
}