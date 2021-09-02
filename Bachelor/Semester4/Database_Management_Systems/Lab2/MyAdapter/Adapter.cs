using System.Configuration;
using System.Data;
using System.Data.SqlClient;

namespace Lab1.MyAdapter
{
    public class Adapter
    {
        private readonly SqlDataAdapter _dataAdapter = new SqlDataAdapter();
        private readonly DataSet _dataSetParent = new DataSet();
        private readonly DataSet _dataSetChild = new DataSet();

        public DataTable GetAllParents()
        {
            using (var connection = GetConnection())
            {
                _dataAdapter.SelectCommand = new SqlCommand(Utils.GetResource(Resources.SelectParent), connection);
                _dataSetParent.Clear();
                _dataAdapter.Fill(_dataSetParent);
                return _dataSetParent.Tables[0];
            }
        }

        public DataTable GetChildrenById(int id)
        {
            using (var connection = GetConnection())
            {
                _dataAdapter.SelectCommand = new SqlCommand(Utils.GetResource(Resources.SelectChild), connection);
                _dataAdapter.SelectCommand.Parameters.Add("@"+Utils.GetParentIdColumn(), SqlDbType.Int).Value = id;
                _dataSetChild.Clear();
                _dataAdapter.Fill(_dataSetChild);
                return _dataSetChild.Tables[0];
            }
        }

        public int UpdateChild(params string[] values)
        {
            using (var connection = GetConnection())
            {
                _dataAdapter.UpdateCommand = new SqlCommand(Utils.GetResource(Resources.UpdateChild), connection);
                int counter = 0;
                foreach (var column in Utils.GetResource(Resources.ChildColumns).Split(','))
                {
                    _dataAdapter.UpdateCommand.Parameters.AddWithValue("@" + column, values[counter]);
                    counter++;
                }
                var result = _dataAdapter.UpdateCommand.ExecuteNonQuery();
                return result;
            }
        }

        public int DeleteChild(int id)
        {
            using (var connection = GetConnection())
            {
                _dataAdapter.DeleteCommand = new SqlCommand(Utils.GetResource(Resources.DeleteChild), connection);
                _dataAdapter.DeleteCommand.Parameters.Add("@"+Utils.GetChildIdColumn(), SqlDbType.Int).Value = id;
                return _dataAdapter.DeleteCommand.ExecuteNonQuery();
            }
        }

        public int AddChild(params string[] values)
        {
            using (var connection = GetConnection())
            {
                var idColumn = Utils.GetChildIdColumn();
                int counter = 0;
                _dataAdapter.InsertCommand = new SqlCommand(Utils.GetResource(Resources.InsertChild), connection);
                foreach (var column in Utils.GetResource(Resources.ChildColumns).Split(','))
                {
                    if (idColumn==column)
                        continue;
                    _dataAdapter.InsertCommand.Parameters.AddWithValue("@" + column, values[counter]);
                    counter++;
                }
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