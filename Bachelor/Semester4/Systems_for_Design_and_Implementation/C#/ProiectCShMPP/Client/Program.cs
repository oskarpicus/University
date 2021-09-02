using System;
using System.Collections.Generic;
using System.Configuration;
using System.Linq;
using System.Threading.Tasks;
using System.Windows.Forms;
using Networking.RpcProtocol;
using Services;

namespace GUI
{
    static class Program
    {
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new LogIn(GetServiceProxy()));
        }

        static IService GetServiceProxy()
        {
            string host = ConfigurationManager.AppSettings["host"];
            int port = Int32.Parse(ConfigurationManager.AppSettings["port"]);
            return new ServicesRpcProxy(host, port);
        }
    }
}