using System;

namespace Networking.RpcProtocol
{
    [Serializable]
    public class Response
    {
        public ResponseType Type
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