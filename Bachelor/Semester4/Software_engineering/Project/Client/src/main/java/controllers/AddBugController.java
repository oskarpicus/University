package controllers;

import domain.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.Service;

public class AddBugController implements Controller{

    private Service service;
    private Tester loggedTester;

    @FXML
    TextField textFieldName;
    @FXML
    TextArea textAreaDescription;
    @FXML
    ComboBox<Severity> comboBoxSeverity;


    @Override
    public void close() {
        Stage stage = (Stage) textFieldName.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialise(Service service, User user) {
        this.service = service;
        this.loggedTester = (Tester)user;
        this.comboBoxSeverity.setItems(FXCollections.observableArrayList(Severity.values()));
    }

    @Override
    public Service getService() {
        return this.service;
    }

    @Override
    public User getLoggedUser() {
        return this.loggedTester;
    }

    public void buttonHomeClicked(MouseEvent mouseEvent) {
        openWindow(Window.tester);
    }

    public void buttonConfirmClicked(ActionEvent actionEvent) {
        String name = textFieldName.getText();
        String description = textAreaDescription.getText();
        Severity severity = comboBoxSeverity.getValue();
        if (name.equals("") || description.equals("") || severity == null){
            MyAlert.showMessage(null, Alert.AlertType.WARNING, "Warning", "Form not completed");
            return;
        }
        Tester tester = (Tester)this.getLoggedUser();
        Bug bug = new Bug(1L, name, description, severity, Status.UNRESOLVED, tester);
        try{
            service.addBug(bug);
            MyAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Confirmation", "Bug successfully added");
        }catch (Exception e){
            MyAlert.showErrorMessage(null, e.getMessage());
        }
    }

    public void buttonViewBugsClicked(MouseEvent mouseEvent) {
        openWindow(Window.viewBugsTester);
    }
}
