package controllers;

import domain.JuryMember;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.Service;

public class LoginController implements Controller{
    private Service service;
    private JuryMember juryMember;
    
    @FXML
    TextField textFieldUsername;
    @FXML
    PasswordField passwordField;

    @Override
    public void close() {
        getStage().close();
    }

    @Override
    public void initialise(Service service, JuryMember juryMember) {
        this.service = service;
        this.juryMember = juryMember;
        
        getStage().setOnCloseRequest(event -> System.exit(0));
    }

    @Override
    public Service getService() {
        return service;
    }

    @Override
    public JuryMember getLoggedJuryMember() {
        return juryMember;
    }
    
    private Stage getStage() {
        return (Stage) textFieldUsername.getScene().getWindow();
    }

    public void buttonLoginClicked(ActionEvent actionEvent) {
        String username = textFieldUsername.getText();
        String password = passwordField.getText();
        if (username.equals("") || password.equals("")) {
            MyAlert.showMessage(null, Alert.AlertType.WARNING, "Warning", "Form not complete");
            return;
        }
        JuryMember juryMember = service.login(username, password);
        if (juryMember == null) {
            MyAlert.showErrorMessage(null, "Wrong credentials");
        }
        else {
            this.juryMember = juryMember;
            openWindow(Window.home);
        }
    }
}
