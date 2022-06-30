import mocks.MockSpectacolRepository;
import mocks.MockVanzareRepository;
import repository.jdbc.SpectacolJdbcRepository;
import repository.jdbc.VanzareJdbcRepository;
import repository.jdbc.utils.JdbcUtils;
import service.MasterService;
import services.Service;
import utils.AbstractServer;
import utils.RpcConcurrentServer;

import java.io.IOException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        Properties serverProps = new Properties();
        try {
            serverProps.load(Main.class.getResourceAsStream("/database.properties"));
            System.out.println("Server properties set. ");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find server.properties " + e);
            return;
        }

        int port = Integer.parseInt(serverProps.getProperty("port"));
        int nrThreads = Integer.parseInt(serverProps.getProperty("nrThreads"));
        JdbcUtils utils = new JdbcUtils(serverProps);
        Service service = new MasterService(new SpectacolJdbcRepository(utils), new VanzareJdbcRepository(utils));
        //Service service = new MasterService(new MockSpectacolRepository(), new MockVanzareRepository());

        AbstractServer server = new RpcConcurrentServer(port, service, nrThreads);
        try {
            server.start();
        } catch (Exception e) {
            System.out.println("Error starting the server " + e.getMessage());
        }
    }
}
