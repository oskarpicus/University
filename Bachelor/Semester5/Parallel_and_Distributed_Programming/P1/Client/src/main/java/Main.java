import client.Client;
import rpcprotocol.ServicesRpcProxy;
import services.Service;

import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        Properties connection = new Properties();
        try {
            connection.load(Main.class.getResourceAsStream("/connection.properties"));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        Service service = new ServicesRpcProxy(connection.getProperty("ip"), Integer.parseInt(connection.getProperty("port")));
        Client client = new Client(service);
        client.run();
    }
}
