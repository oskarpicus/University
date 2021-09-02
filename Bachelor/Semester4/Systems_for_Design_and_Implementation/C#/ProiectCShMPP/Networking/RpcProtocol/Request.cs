using System;

namespace Networking.RpcProtocol
{
    [Serializable]
    public class Request
    {
        public RequestType Type
        {
            get;
            set;
        }

        public Object Data
        {
            get;
            set;
        }

        public override string ToString()
        {
            return Type+" "+Data;
        }
    }
}