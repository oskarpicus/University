package controllers;

import domain.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.Service;

public class LoginController implements Controller{

    private Service service;
    private Player player;

    @FXML
    TextField textFieldUsername;
    @FXML
    PasswordField passwordField;

    public void buttonLoginClicked(ActionEvent actionEvent) {
        String username = textFieldUsername.getText();
        String password = passwordField.getText();
        if (username.equals("") || password.equals("")) {
            MyAlert.showMessage(null, Alert.AlertType.WARNING, "Warning", "Form not completed");
            return;
        }
        player = service.login(username, password);
        if (player == null) {
            MyAlert.showErrorMessage(null, "Invalid credentials");
        }
        else {
            openWindow(Window.game);
        }
    }

    @Override
    public void close() {
        Stage stage = (Stage) textFieldUsername.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialise(Service service, Player player) {
        this.service = service;
        this.player = player;

        Stage stage = (Stage) textFieldUsername.getScene().getWindow();
        stage.setOnCloseRequest(event -> System.exit(0));
    }

    @Override
    public Service getService() {
        return service;
    }

    @Override
    public Player getLoggedPlayer() {
        return player;
    }
}
