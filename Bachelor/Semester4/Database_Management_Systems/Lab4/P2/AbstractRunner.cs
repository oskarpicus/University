using System.Configuration;
using System.Data.SqlClient;

namespace Lab4
{
    public abstract class AbstractRunner
    {
        protected volatile int NrTries = 0;
        protected static readonly int NrMaximumTries = 3;

        protected SqlConnection GetConnection()
        {       
            var settings = ConfigurationManager.ConnectionStrings["connectionString"];
            if (settings != null)
            {
                var connection = new SqlConnection(settings.ConnectionString);
                connection.Open();
                return connection;
            }
            return null;
        }

        public abstract void Run();
    }
}