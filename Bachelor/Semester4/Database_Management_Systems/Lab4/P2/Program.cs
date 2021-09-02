using System;
using System.Threading;

namespace Lab4
{
    internal class Program
    {
        public static void Main(string[] args)
        {
            new Thread(new LowPriorityRunner().Run).Start();
            new Thread(new HighPriorityRunner().Run).Start();
            Console.WriteLine("Enter key to exit");
            Console.ReadKey();
        }
    }
}