package rpcprotocol;

import domain.Vanzare;
import services.Observer;
import services.Service;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class ServicesRpcProxy implements Service {
    private Observer client;
    private final String host;
    private final int port;
    private Socket connection;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private volatile boolean finished;
    private final BlockingQueue<Response> queue = new LinkedBlockingDeque<>();

    public ServicesRpcProxy(String host, int port) {
        this.host = host;
        this.port = port;
    }

    private void closeConnection() {
        finished = true;
        try {
            input.close();
            output.close();
            connection.close();
            client = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Response readResponse() {
        Response result = null;
        try {
            result = queue.take();
        } catch (Exception e) {
            System.out.println("Error read response " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    private void sendRequest(Request request) {
        try {
            output.writeObject(request);
            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error send request " + request);
        }
    }

    private void initialiseConnection() {
        try {
            connection = new Socket(host, port);
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            new Thread(new Reader()).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleUpdate(Response response) {
        if (response.getType() == ResponseType.SERVER_CLOSES) {
            client.serverCloses();
            this.finished = true;
        }
    }

    @Override
    public void checkShows() {
    }

    @Override
    public boolean buyTicket(Vanzare vanzare) {
        Request request = new Request(RequestType.BUY_TICKET, vanzare);
        sendRequest(request);
        Response response = readResponse();
        return response.getType() == ResponseType.OK;
    }

    @Override
    public void addObserver(Observer observer) {
        initialiseConnection();
        finished = false;
        Request request = new Request(RequestType.ADD_OBSERVER, null);
        sendRequest(request);
        Response response = readResponse();
        if (response.getType() == ResponseType.ERROR) {
            throw new RuntimeException(response.getData().toString());
        }
        this.client = observer;
    }

    @Override
    public void notifyObserversServerCloses() {
        closeConnection();
        client.serverCloses();
    }

    private class Reader implements Runnable {

        @Override
        public void run() {
            while (!finished) {
                try {
                    Response response = (Response) input.readObject();
                    if (isUpdateResponse(response))
                        handleUpdate(response);
                    else {
                        try {
                            queue.add(response);
                        } catch (Exception e) {
                            System.out.println("Error Queue Add " + e.getMessage());
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Error Reader run " + e.getMessage());
                }
            }
        }

        private boolean isUpdateResponse(Response response) {
            return response.getType() == ResponseType.SERVER_CLOSES;
        }
    }
}
