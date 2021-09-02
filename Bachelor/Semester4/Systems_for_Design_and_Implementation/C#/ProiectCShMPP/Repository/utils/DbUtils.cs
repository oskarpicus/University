using System.Data;
using ConnectionUtils;

namespace ProiectCShMPP.utils
{
    public class DbUtils
    {
        private static IDbConnection _connection;
        
        public static IDbConnection GetConnection()
        {
            if (_connection == null || _connection.State == ConnectionState.Closed)
            {
                _connection = GetNewConnection();
                _connection.Open();
            }
            return _connection;
        }

        private static IDbConnection GetNewConnection()
        {
            return ConnectionFactory.GetInstance().GetConnection();
        }
    }
}