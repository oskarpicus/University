package controllers;

import domain.JuryMember;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.Service;
import utils.MyAlert;

public class LoginController implements Controller{

    private Service service;
    private JuryMember loggedJuryMember;

    @FXML
    TextField textFieldUsername;
    @FXML
    TextField textFieldPassword;

    @Override
    public void close() {
        Stage stage = (Stage) textFieldUsername.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialise(Service service, JuryMember user) {
        this.service = service;
        this.loggedJuryMember = user;

        Stage stage = (Stage) textFieldUsername.getScene().getWindow();
        stage.setOnCloseRequest(event -> System.exit(0));
    }

    @Override
    public Service getService() {
        return this.service;
    }

    @Override
    public JuryMember getLoggedUser() {
        return this.loggedJuryMember;
    }

    public void buttonLoginClicked(ActionEvent actionEvent) {
        String username = textFieldUsername.getText();
        String password = textFieldPassword.getText();
        if (username.equals("") || password.equals("")) {
            MyAlert.showMessage(null, Alert.AlertType.WARNING, "Warning", "You need to fill out the entire form");
            return;
        }
        JuryMember juryMember = service.login(username, password);
        if (juryMember == null) {
            MyAlert.showErrorMessage(null, "Invalid credentials");
            return;
        }
        this.loggedJuryMember = juryMember;
        System.out.println(juryMember + " logged in");
        openWindow(Window.home);
    }
}
