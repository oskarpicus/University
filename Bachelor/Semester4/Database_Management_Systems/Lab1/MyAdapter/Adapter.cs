using System.Configuration;
using System.Data;
using System.Data.SqlClient;

namespace Lab1.MyAdapter
{
    public class Adapter
    {
        private readonly SqlDataAdapter _dataAdapter = new SqlDataAdapter();
        private readonly DataSet _dataSet = new DataSet();

        public DataTable GetAllPublishingHouses()
        {
            using (var connection = GetConnection())
            {
                _dataAdapter.SelectCommand = new SqlCommand("SELECT * FROM Editura;", connection);
                _dataSet.Clear();
                _dataAdapter.Fill(_dataSet);
                return _dataSet.Tables[0];
            }
        }

        public DataTable GetBooksByEid(int eid)
        {
            using (var connection = GetConnection())
            {
                _dataAdapter.SelectCommand = new SqlCommand("SELECT cid, titlu, an, eid FROM Carte WHERE eid=@eid;", connection);
                _dataAdapter.SelectCommand.Parameters.Add("@eid", SqlDbType.Int).Value = eid;
                _dataSet.Clear();
                _dataAdapter.Fill(_dataSet);
                return _dataSet.Tables[0];
            }
        }

        public int UpdateBook(int cid, string title, int year, int eid)
        {
            using (var connection = GetConnection())
            {
                _dataAdapter.UpdateCommand = new SqlCommand("UPDATE Carte SET titlu=@t, an=@a, eid=@e WHERE cid=@c;", connection);
                _dataAdapter.UpdateCommand.Parameters.Add("@t", SqlDbType.VarChar).Value = title;
                _dataAdapter.UpdateCommand.Parameters.Add("@a", SqlDbType.Int).Value = year;
                _dataAdapter.UpdateCommand.Parameters.Add("@e", SqlDbType.Int).Value = eid;
                _dataAdapter.UpdateCommand.Parameters.Add("@c", SqlDbType.Int).Value = cid;
                var result = _dataAdapter.UpdateCommand.ExecuteNonQuery();
                return result;
            }
        }

        public int DeleteBook(int cid)
        {
            using (var connection = GetConnection())
            {
                _dataAdapter.DeleteCommand = new SqlCommand("DELETE FROM Carte WHERE cid=@c;", connection);
                _dataAdapter.DeleteCommand.Parameters.Add("@c", SqlDbType.Int).Value = cid;
                return _dataAdapter.DeleteCommand.ExecuteNonQuery();
            }
        }

        public int AddBook(string title, int year, int eid)
        {
            int cnp1 = 0, cnp2 = 0;
            using (var connection = GetConnection())
            {
                using (var commandSelect = connection.CreateCommand())
                {
                    commandSelect.CommandText = "SELECT TOP 1 i.CNP, a.CNP FROM Imprumutator i, Angajat a;";
                    using (var reader = commandSelect.ExecuteReader())
                    {
                        if (reader.Read())
                        {
                            cnp1 = reader.GetInt32(0);
                            cnp2 = reader.GetInt32(1);
                        }
                    }
                }
                _dataAdapter.InsertCommand = new SqlCommand(
                                "INSERT INTO Carte(titlu, an, eid, data_imprumut, CNP_imprumutator, CNP_Angajat) VALUES " +
                                "(@t,@a,@e,'2020-08-08',@cnpi,@cnpa);", connection);
                _dataAdapter.InsertCommand.Parameters.Add("@t", SqlDbType.VarChar).Value = title;
                _dataAdapter.InsertCommand.Parameters.Add("@a", SqlDbType.Int).Value = year;
                _dataAdapter.InsertCommand.Parameters.Add("@e", SqlDbType.Int).Value = eid;
                _dataAdapter.InsertCommand.Parameters.Add("@cnpi", SqlDbType.Int).Value = cnp1;
                _dataAdapter.InsertCommand.Parameters.Add("@cnpa", SqlDbType.Int).Value = cnp2;
                return _dataAdapter.InsertCommand.ExecuteNonQuery();
            }
        }

        private SqlConnection GetConnection()
        {
            string connectionString = "";
            var settings = ConfigurationManager.ConnectionStrings["connectionString"];
            if (settings != null)
                connectionString = settings.ConnectionString;
            var connection = new SqlConnection(connectionString);
            connection.Open();
            return connection;
        }
    }
}