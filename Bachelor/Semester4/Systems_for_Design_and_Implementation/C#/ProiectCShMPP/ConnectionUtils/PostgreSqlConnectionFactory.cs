using System;
using System.Configuration;
using System.Data;
using Npgsql;

namespace ConnectionUtils
{
    public class PostgreSqlConnectionFactory : ConnectionFactory
    {
        public override IDbConnection GetConnection()
        {
            String connectionString = null;
            var settings = ConfigurationManager.ConnectionStrings["connectionString"];
            if (settings != null)
                connectionString = settings.ConnectionString;
            return new NpgsqlConnection(connectionString);
        }
    }
}