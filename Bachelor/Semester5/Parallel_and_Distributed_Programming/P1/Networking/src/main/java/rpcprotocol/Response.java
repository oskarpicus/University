package rpcprotocol;

import java.io.Serializable;

public class Response implements Serializable {
    private ResponseType type;
    private Object data;

    public Response(ResponseType type) {
        this.type = type;
    }

    public Response(ResponseType type, Object data) {
        this.type = type;
        this.data = data;
    }

    public ResponseType getType() {
        return type;
    }

    public void setType(ResponseType type) {
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Response{" + "type=" + type +
                ", data=" + data +
                '}';
    }
}