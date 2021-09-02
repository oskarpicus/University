package controllers;

import domain.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import services.Service;
import services.TourismAgencyException;


public class LoginController {
    private Service service;
    private HomeController homeController;
    private Stage homeStage;

    @FXML
    TextField textfieldUsername;
    @FXML
    PasswordField textfieldPassword;

    public void buttonLoginClicked(MouseEvent mouseEvent) {
        String username = textfieldUsername.getText();
        String password = textfieldPassword.getText();
        if(username.equals("") || password.equals("")){
            MyAlert.showErrorMessage(null, "You have forms unfilled !");
            return;
        }
        User user = null;
        try {
            getMainWindow();
            user = service.findUser(username, password, homeController);
            if(user!=null) {
                homeController.initialise(service, user);
                closeWindow();
                homeStage.show();
            }
        }catch (TourismAgencyException e){
            MyAlert.showErrorMessage(null, e.getMessage());
        }
        if(user==null){
            MyAlert.showMessage(null, Alert.AlertType.WARNING, "Warning","Wrong credentials !");
        }
    }

    public void setService(Service service){
        this.service = service;
    }

    private void getMainWindow(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/home.fxml"));
            Pane pane = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(pane));
            homeStage = stage;

            homeController = loader.getController();

            stage.setOnCloseRequest((x)->{
                homeController.buttonLogOutClicked(null);
                System.exit(0);
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void closeWindow(){
        Stage stage = (Stage) textfieldPassword.getScene().getWindow();
        stage.close();
    }
}