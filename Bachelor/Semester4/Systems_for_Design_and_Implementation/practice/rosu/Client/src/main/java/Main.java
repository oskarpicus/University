import controllers.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import services.Service;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:Spring.xml");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/login.fxml"));
        Pane pane = loader.load();
        primaryStage.setScene(new Scene(pane));
        Service service = context.getBean(Service.class);
        LoginController controller = loader.getController();
        controller.initialise(service, null);
        primaryStage.show();
    }
}
