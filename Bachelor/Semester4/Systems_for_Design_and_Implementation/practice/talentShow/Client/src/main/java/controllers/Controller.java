package controllers;

import domain.JuryMember;
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
            controller.initialise(this.getService(), this.getLoggedUser());
            this.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    void close();

    void initialise(Service service, JuryMember user);

    Service getService();

    JuryMember getLoggedUser();
}
