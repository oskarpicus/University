using System;
using System.Collections.Generic;
using System.Data;
using ProiectCShMPP.domain;
using ProiectCShMPP.domain.validator;
using ProiectCShMPP.utils;

namespace ProiectCShMPP.repository
{
    public class TripDbRepository : AbstractDBRepository<long,Trip>, ITripRepository
    {
        public TripDbRepository(IValidator<long, Trip> validator) : base(validator)
        {
        }

        protected override IDbCommand GetSaveCommand(IDbConnection connection, Trip entity)
        {
            var command = connection.CreateCommand();
            command.CommandText =
                "INSERT INTO trips(destination, transportfirm, departuretime, price, seats) VALUES (@destination, @transportfirm, @departuretime, @price, @seats);";

            var parameter = command.CreateParameter();
            parameter.ParameterName = "@destination";
            parameter.Value = entity.Destination;
            command.Parameters.Add(parameter);
            
            parameter = command.CreateParameter();
            parameter.ParameterName = "@transportfirm";
            parameter.Value = entity.TransportFirm;
            command.Parameters.Add(parameter);
            
            parameter = command.CreateParameter();
            parameter.ParameterName = "@departuretime";
            parameter.Value = entity.DepartureTime;
            command.Parameters.Add(parameter);
            
            parameter = command.CreateParameter();
            parameter.ParameterName = "@price";
            parameter.Value = entity.Price;
            command.Parameters.Add(parameter);
            
            parameter = command.CreateParameter();
            parameter.ParameterName = "@seats";
            parameter.Value = entity.Seats;
            command.Parameters.Add(parameter);
            
            return command;
        }

        protected override IDbCommand GetDeleteCommand(IDbConnection connection, long id)
        {
            var command = connection.CreateCommand();
            command.CommandText = "DELETE FROM trips WHERE id=@id;";

            var parameter = command.CreateParameter();
            parameter.ParameterName = "@id";
            parameter.Value = id;
            command.Parameters.Add(parameter);
            
            return command;
        }

        protected override IDbCommand GetUpdateCommand(IDbConnection connection, Trip entity)
        {
            var command = connection.CreateCommand();
            command.CommandText =
                "UPDATE trips SET destination = @destination, transportfirm = @transportfirm, departuretime = @departuretime, price = @price, seats = @seats WHERE id=@id;";

            var parameter = command.CreateParameter();
            parameter.ParameterName = "@destination";
            parameter.Value = entity.Destination;
            command.Parameters.Add(parameter);
            
            parameter = command.CreateParameter();
            parameter.ParameterName = "@transportfirm";
            parameter.Value = entity.TransportFirm;
            command.Parameters.Add(parameter);
            
            parameter = command.CreateParameter();
            parameter.ParameterName = "@departuretime";
            parameter.Value = entity.DepartureTime;
            command.Parameters.Add(parameter);
            
            parameter = command.CreateParameter();
            parameter.ParameterName = "@price";
            parameter.Value = entity.Price;
            command.Parameters.Add(parameter);
            
            parameter = command.CreateParameter();
            parameter.ParameterName = "@seats";
            parameter.Value = entity.Seats;
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
            command.CommandText = "SELECT * FROM trips WHERE id=@id;";

            var parameter = command.CreateParameter();
            parameter.ParameterName = "@id";
            parameter.Value = id;
            command.Parameters.Add(parameter);

            return command;
        }

        protected override IDbCommand GetFindAllCommand(IDbConnection connection)
        {
            var command = connection.CreateCommand();
            command.CommandText = "SELECT * FROM trips;";
            return command;
        }

        protected override Trip ExtractEntity(IDataReader reader)
        {
            _logger.InfoFormat("Entry Extract Entity");
            long id = (long) reader["id"];
            string destination = (string) reader["destination"];
            string transportFirm = (string) reader["transportfirm"];
            DateTime departureTime = (DateTime) reader["departuretime"];
            float price = (float)(double) reader["price"];
            int seats = (int) reader["seats"];
            Trip trip = new Trip(id)
            {
                Destination = destination,
                TransportFirm = transportFirm,
                DepartureTime = departureTime,
                Price = price,
                Seats = seats
            };
            _logger.InfoFormat("Extracted {0}",trip);
            return trip;
        }

        public IEnumerable<Trip> FindTripsByDestinationTime(string destination, DateTime from, DateTime to)
        {
            _logger.InfoFormat("Entry find trips in {0} between {1} and {2}", destination, from, to);
            var list = new List<Trip>();
            using (var connection = DbUtils.GetConnection())
            {
                using (var command = connection.CreateCommand())
                {
                    command.CommandText =
                        "SELECT * FROM trips WHERE destination=@destination AND departuretime BETWEEN @from AND @to;";
                    var parameter = command.CreateParameter();
                    parameter.ParameterName = "@destination";
                    parameter.Value = destination;
                    command.Parameters.Add(parameter);
                    
                    parameter = command.CreateParameter();
                    parameter.ParameterName = "@from";
                    parameter.Value = from;
                    command.Parameters.Add(parameter);
                    
                    parameter = command.CreateParameter();
                    parameter.ParameterName = "@to";
                    parameter.Value = to;
                    command.Parameters.Add(parameter);

                    using (var reader = command.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            list.Add(ExtractEntity(reader));
                        }
                    }
                }
            }
            _logger.InfoFormat("Exiting with {0}", list);
            return list;
        }
    }
}