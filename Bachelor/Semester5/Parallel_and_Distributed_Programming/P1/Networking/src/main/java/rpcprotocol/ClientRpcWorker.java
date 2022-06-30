package rpcprotocol;

import domain.Vanzare;
import services.Observer;
import services.Service;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;

public class ClientRpcWorker implements Runnable, Observer {
    private final Service service;
    private final Socket connection;
    private volatile boolean connected;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    public ClientRpcWorker(Service service, Socket connection) {
        this.service = service;
        this.connection = connection;
        try {
            connected = true;
            inputStream = new ObjectInputStream(connection.getInputStream());
            outputStream = new ObjectOutputStream(connection.getOutputStream());
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (connected) {
            try {
                Request request = (Request) inputStream.readObject();
                System.out.println("Got request: " + request.getType());
                Response response = handleRequest(request);
                if (response != null)
                    sendResponse(response);
            } catch (Exception e) {
                break;
            }
        }
        try {
            inputStream.close();
            outputStream.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        }
    }

    private Response handleRequest(Request request) {
        Response result = null;
        String methodName = "handle" + request.getType();
        try {
            Method method = getClass().getDeclaredMethod(methodName, Request.class);
            result = (Response) method.invoke(this, request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private void sendResponse(Response response) throws IOException {
        outputStream.writeObject(response);
        outputStream.flush();
    }

    private Response handleBUY_TICKET(Request request) {
        try {
            System.out.println(request.getData());
            boolean savedSuccessfully = service.buyTicket((Vanzare) request.getData());
            System.out.println(savedSuccessfully);
            return new Response(savedSuccessfully ? ResponseType.OK : ResponseType.ERROR, null);
        } catch (Exception e) {
            return new Response(ResponseType.ERROR, e.getMessage());
        }
    }

    private Response handleADD_OBSERVER(Request request) {
        try {
            service.addObserver(this);
            return new Response(ResponseType.OK, null);
        } catch (Exception e) {
            connected = false;
            return new Response(ResponseType.ERROR, e.getMessage());
        }
    }

    @Override
    public void serverCloses() {
        Response response = new Response(ResponseType.SERVER_CLOSES, null);
        try {
            sendResponse(response);
            connected = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}