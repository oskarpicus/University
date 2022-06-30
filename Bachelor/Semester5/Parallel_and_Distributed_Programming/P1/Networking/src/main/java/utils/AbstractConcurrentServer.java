package utils;

import java.net.Socket;

public abstract class AbstractConcurrentServer extends AbstractServer {
    protected AbstractConcurrentServer(int port) {
        super(port);
    }

    @Override
    protected void processRequest(Socket client) {
        createWorker(client);
    }

    protected abstract void createWorker(Socket client);
}
