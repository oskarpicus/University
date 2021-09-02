package observer;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class ObserverServer {
    private final int port;
    private final ObserverService service;
    private Server server;

    public ObserverServer(int port, ObserverService service) {
        this.port = port;
        this.service = service;
    }

    public void start() throws IOException{
        server = ServerBuilder.forPort(port).addService(service).build().start();
        System.out.println("PORT "+server.getPort());
    }

    public void blockUntilShutdown() throws InterruptedException{
        if(server==null)
            return;
        server.awaitTermination();
    }

    public int getPort(){
        return server.getPort();
    }

    public void stop() {
        server.shutdownNow();
    }
}
