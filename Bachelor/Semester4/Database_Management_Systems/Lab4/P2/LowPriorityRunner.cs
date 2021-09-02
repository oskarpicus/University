using System;
using System.Data;
using System.Data.SqlClient;

namespace Lab4
{
    public class LowPriorityRunner : AbstractRunner
    {
        public override void Run()
        {
            using (var connection = GetConnection())
            {
                try
                {
                    var command = new SqlCommand("uspDeadlock1", connection)
                    {
                        CommandType = CommandType.StoredProcedure
                    };
                    command.ExecuteNonQuery();
                    Console.WriteLine("SUCCESSFULLY RAN LOW PRIORITY");
                }
                catch (Exception e)
                {
                    Console.WriteLine("Low priority runner");
                    Console.WriteLine(e + "\n");
                    if (NrTries < NrMaximumTries)
                    {
                        NrTries++;
                        Run();
                    }
                }
            }
        }
    }
}