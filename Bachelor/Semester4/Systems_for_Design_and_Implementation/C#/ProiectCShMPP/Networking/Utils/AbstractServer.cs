using System;
using System.Net;
using System.Net.Sockets;
using log4net;

namespace Networking.Utils
{
    public abstract class AbstractServer
    {
        private TcpListener _server;
        private readonly int _port;
        private readonly string _ip;
        private static readonly ILog _logger = LogManager.GetLogger(typeof(AbstractServer));

        public AbstractServer(string ip, int port)
        {
            this._port = port;
            this._ip = ip;
        }

        public void Start()
        {
            _logger.InfoFormat("Entry Start Server");
            _server = new TcpListener(IPAddress.Parse(_ip), _port);
            _server.Start();
            while (true)
            {
                Console.WriteLine("Waiting clients...");
                TcpClient client = _server.AcceptTcpClient();
                Console.WriteLine("{0} connected", client);
                _logger.InfoFormat("{0} connected", client);
                ProcessRequest(client);
            }
        }

        protected abstract void ProcessRequest(TcpClient client);
    }
}