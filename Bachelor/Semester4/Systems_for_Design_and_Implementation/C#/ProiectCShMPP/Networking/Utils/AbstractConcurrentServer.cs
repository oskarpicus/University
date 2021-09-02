using System.Net.Sockets;
using System.Threading;

namespace Networking.Utils
{
    public abstract class AbstractConcurrentServer : AbstractServer
    {
        protected AbstractConcurrentServer(string ip, int port) : base(ip, port)
        {
        }

        protected override void ProcessRequest(TcpClient client)
        {
            CreateWorker(client).Start();
        }

        protected abstract Thread CreateWorker(TcpClient client);
    }
}