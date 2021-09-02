using System;
using System.Data;
using ProiectCShMPP.domain;
using ProiectCShMPP.domain.validator;
using ProiectCShMPP.utils;

namespace ProiectCShMPP.repository
{
    public class UserDbRepository : AbstractDBRepository<long, User>, IUserRepository
    {
        public UserDbRepository(IValidator<long, User> validator) : base(validator)
        {
        }

        protected override IDbCommand GetSaveCommand(IDbConnection connection, User entity)
        {
            IDbCommand command = connection.CreateCommand();
            command.CommandText = "INSERT INTO users(username, password) VALUES (@username, @password);";

            IDbDataParameter parameterUsername = command.CreateParameter();
            parameterUsername.ParameterName = "@username";
            parameterUsername.Value = entity.Username;

            IDbDataParameter parameterPassword = command.CreateParameter();
            parameterPassword.ParameterName = "@password";
            parameterPassword.Value = entity.Password;

            command.Parameters.Add(parameterUsername);
            command.Parameters.Add(parameterPassword);
            return command;
        }

        protected override IDbCommand GetDeleteCommand(IDbConnection connection, long id)
        {
            IDbCommand command = connection.CreateCommand();
            command.CommandText = "DELETE FROM users WHERE id=@id;";

            IDataParameter parameter = command.CreateParameter();
            parameter.ParameterName = "@id";
            parameter.Value = id;

            command.Parameters.Add(parameter);
            return command;
        }

        protected override IDbCommand GetUpdateCommand(IDbConnection connection, User entity)
        {
            IDbCommand command = connection.CreateCommand();
            command.CommandText = "UPDATE users SET username = @username, password = @password WHERE id = @id;";

            IDataParameter parameter = command.CreateParameter();
            parameter.ParameterName = "@username";
            parameter.Value = entity.Username;
            command.Parameters.Add(parameter);

            parameter = command.CreateParameter();
            parameter.ParameterName = "@password";
            parameter.Value = entity.Password;
            command.Parameters.Add(parameter);
            return command;
        }

        protected override IDbCommand GetFindCommand(IDbConnection connection, long id)
        {
            IDbCommand command = connection.CreateCommand();
            command.CommandText = "SELECT * FROM users WHERE id=@id;";
            IDbDataParameter parameter = command.CreateParameter();
            parameter.ParameterName = "@id";
            parameter.Value = id;
            command.Parameters.Add(parameter);
            return command;
        }

        protected override IDbCommand GetFindAllCommand(IDbConnection connection)
        {
            IDbCommand command = connection.CreateCommand();
            command.CommandText = "SELECT * FROM users;";
            return command;
        }

        protected override User ExtractEntity(IDataReader reader)
        {
            _logger.InfoFormat("Entry Extract Entity");
            long id = (long)reader["id"];
            string name = (string) reader["username"];
            string password = (String) reader["password"];
            User result = new User(id)
            {
                Username = name,
                Password = password
            };
            _logger.InfoFormat("Extracted {0}", result);
            return result;
        }

        public User FindByUsernamePassword(string username, string password)
        {
            _logger.InfoFormat("Entry find user {0},{1}", username,password);
            User result = null;
            using (IDbConnection connection = DbUtils.GetConnection())
            {
                using (IDbCommand command = connection.CreateCommand())
                {
                    command.CommandText = "SELECT * FROM users WHERE username=@username AND password=@password;";
                    IDbDataParameter parameter = command.CreateParameter();
                    parameter.ParameterName = "@username";
                    parameter.Value = username;
                    command.Parameters.Add(parameter);

                    parameter = command.CreateParameter();
                    parameter.ParameterName = "@password";
                    parameter.Value = password;
                    command.Parameters.Add(parameter);

                    using (IDataReader reader = command.ExecuteReader())
                    {
                        if (reader.Read())
                            result = ExtractEntity(reader);
                    }
                }
            }
            _logger.InfoFormat("Found {0}", result);
            return result;
        }
    }
}