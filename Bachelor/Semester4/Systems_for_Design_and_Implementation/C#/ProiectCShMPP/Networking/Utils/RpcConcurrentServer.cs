using System.Net.Sockets;
using System.Threading;
using Networking.RpcProtocol;
using Services;

namespace Networking.Utils
{
    public class RpcConcurrentServer : AbstractConcurrentServer
    {
        private readonly IService _service;
        
        public RpcConcurrentServer(string ip, int port, IService service) : base(ip, port)
        {
            _service = service;
        }

        protected override Thread CreateWorker(TcpClient client)
        {
            return new Thread(new ClientRpcWorker(_service, client).Run);
        }
    }
}