import controllers.LoginController;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import proto.ServiceGrpc;
import services.Service;
import stub.ProtobufStub;

import java.util.Properties;

public class MainFX  extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:Spring.xml");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/login.fxml"));
        Pane pane = loader.load();
        primaryStage.setScene(new Scene(pane));

        Properties properties = (Properties) context.getBean("connectionProperties");
        String ip = properties.getProperty("ip");
        int port = Integer.parseInt(properties.getProperty("port"));
        ManagedChannel channel = ManagedChannelBuilder.forAddress(ip,port).usePlaintext().build();

        ServiceGrpc.ServiceBlockingStub stub = ServiceGrpc.newBlockingStub(channel);
        ProtobufStub proxy = new ProtobufStub(stub);

//        Service service = context.getBean(Service.class);
//        System.out.println(service);
        LoginController controller = loader.getController();
//        controller.setService(service);
        controller.setService(proxy);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
