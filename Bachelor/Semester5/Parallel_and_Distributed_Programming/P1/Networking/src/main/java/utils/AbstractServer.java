package utils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public abstract class AbstractServer {
    private ServerSocket server;
    private final int port;
    private Timer timer;
    private static final int MINUTES_TO_RUN = 2;

    protected AbstractServer(int port) {
        this.port = port;
    }

    public void start() {
        scheduleTimeToCloseServer();
        scheduleChecks();
        try {
            server = new ServerSocket(port);
            while (true) {
                System.out.println("Waiting for clients");
                Socket client = server.accept();
                System.out.println("Somebody connected !");
                processRequest(client);
            }
        } catch (Exception e) {
            System.out.println("CLOSED");
        }
    }

    protected abstract void processRequest(Socket client);

    protected abstract void scheduleChecks();

    public void scheduleTimeToCloseServer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                stop();
                timer.cancel();
                timer.purge();
            }
        }, TimeUnit.MINUTES.toMillis(MINUTES_TO_RUN));
    }

    public void stop() {
        try {
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
