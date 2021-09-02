using System;
using System.Data;
using System.Data.SqlClient;

namespace Lab4
{
    public class HighPriorityRunner : AbstractRunner
    {
        public override void Run()
        {
            using (var connection = GetConnection())
            {
                try
                {
                    var command = new SqlCommand("uspDeadlock2", connection)
                    {
                        CommandType = CommandType.StoredProcedure
                    };
                    command.ExecuteNonQuery();
                    Console.WriteLine("SUCCESSFULLY RAN HIGH PRIORITY");
                }
                catch (Exception e)
                {
                    Console.WriteLine("High priority runner");
                    Console.WriteLine(e + "\n");
                }
            }
        }
    }
}