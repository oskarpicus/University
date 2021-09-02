package controllers;

import domain.Tester;
import domain.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.Service;

public class LoginController implements Controller{
    private Service service;
    private User loggedUser;

    @FXML
    TextField textFieldUsername;
    @FXML
    PasswordField passwordFieldPassword;

    public void buttonLoginClicked(ActionEvent actionEvent) {
        String username = textFieldUsername.getText();
        String password = passwordFieldPassword.getText();
        if (username.equals("") || password.equals("")){
            MyAlert.showMessage(null, Alert.AlertType.WARNING, "Warning", "You did not submit all data");
            return;
        }
        User user = service.findUser(username, password);
        if (user == null){
            MyAlert.showMessage(null, Alert.AlertType.WARNING, "Warning", "Wrong credentials");
            return;
        }
        Window window = user instanceof Tester ? Window.tester : Window.programmer;
        this.loggedUser = user;
        openWindow(window);
    }

    @Override
    public void close() {
        Stage stage = (Stage) textFieldUsername.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialise(Service service, User user) {
        this.service = service;
    }

    @Override
    public Service getService() {
        return service;
    }

    @Override
    public User getLoggedUser() {
        return loggedUser;
    }
}
