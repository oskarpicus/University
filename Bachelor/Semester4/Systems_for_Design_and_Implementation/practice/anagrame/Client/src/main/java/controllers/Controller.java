package controllers;

import domain.Player;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import services.Service;

public interface Controller {
    default void openWindow(Window window){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/" + window + ".fxml"));
            Pane pane = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(pane));
            stage.show();

            Controller controller = loader.getController();
            controller.initialise(this.getService(), this.getLoggedPlayer());
            this.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    void close();

    void initialise(Service service, Player user);

    Service getService();

    Player getLoggedPlayer();
}
