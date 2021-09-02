package controllers;

import domain.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import services.Observer;
import services.Service;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.stream.Collectors;

public class ProgrammerController extends UnicastRemoteObject implements Controller, Observer, Serializable {

    private Service service;
    private Programmer loggedProgrammer;
    private transient final ObservableList<Bug> model = FXCollections.observableArrayList();

    @FXML
    transient Label labelGreeting;
    @FXML
    transient TableView<Bug> tableViewBugs;
    @FXML
    transient TableColumn<Bug, String> tableColumnName;
    @FXML
    transient TableColumn<Bug, String> tableColumnSeverity;
    @FXML
    transient TableColumn<Bug, String> tableColumnStatus;
    @FXML
    transient TextArea textAreaDescription;
    @FXML
    transient ComboBox<Severity> comboBoxSeverity;

    public ProgrammerController() throws RemoteException {
    }

    @Override
    public void close() {
        Stage stage = (Stage) labelGreeting.getScene().getWindow();
        stage.close();
        this.service.removeObserver(this);
    }

    @Override
    public void initialise(Service service, User user) {
        this.service = service;
        this.loggedProgrammer = (Programmer)user;
        labelGreeting.setText("Hello " + loggedProgrammer.getUsername());

        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnSeverity.setCellValueFactory(new PropertyValueFactory<>("severity"));
        tableColumnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        tableViewBugs.setItems(model);

        tableViewBugs.getSelectionModel().selectedItemProperty().addListener((obs, old, newS) -> {
            if (newS != null) {
                textAreaDescription.setText(newS.getDescription());
            }
        });

        comboBoxSeverity.setItems(FXCollections.observableArrayList(Severity.values()));

        this.service.addObserver(this);
        displayBugs(this.service.getAllBugs());

        Stage stage = (Stage) labelGreeting.getScene().getWindow();
        stage.setOnCloseRequest(event -> this.service.removeObserver(this));
    }

    @Override
    public Service getService() {
        return this.service;
    }

    @Override
    public User getLoggedUser() {
        return this.loggedProgrammer;
    }

    public void displayBugs(List<Bug> bugs) {
        model.setAll(bugs);
    }

    @Override
    public void addedBug(Bug bug) throws RemoteException {
        this.model.add(bug);
    }

    @Override
    public void updatedBug(Bug bug) throws RemoteException {
        List<Bug> bugs = model.stream()  // remove the updated bug
                .filter(bug1 -> !bug1.getId().equals(bug.getId()))
                .collect(Collectors.toList());
        if (bug.getStatus() != Status.RESOLVED)
            bugs.add(bug);
        model.setAll(bugs);
    }

    public void buttonFilterBugsClicked(ActionEvent actionEvent) {
        Severity severity = comboBoxSeverity.getValue();
        if (severity == null) {
            MyAlert.showMessage(null, Alert.AlertType.WARNING, "Warning", "You have not selected any severity");
            return;
        }
        List<Bug> bugs = service.findBugsBySeverity(severity);
        displayBugs(bugs);
    }

    public void buttonClearClicked(ActionEvent actionEvent) {
        List<Bug> bugs = service.getAllBugs();
        displayBugs(bugs);
    }

    public void buttonResolvedBugClicked(ActionEvent actionEvent) {
        Bug bug = getSelectedBug();
        if (bug == null) {  // nothing was selected
            MyAlert.showMessage(null, Alert.AlertType.WARNING, "Warning", "You have not selected any bug");
            return;
        }
        try {
            Programmer p = (Programmer)this.getLoggedUser();
            bug.setStatus(Status.RESOLVED);
            bug.setProgrammer(p);
            if (this.service.updateBug(bug) == null) {
                MyAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Confirmation", "Bug successfully resolved");
                this.textAreaDescription.clear();
            }
            else
                MyAlert.showMessage(null, Alert.AlertType.WARNING, "Warning", "Failed to resolve bug");
        } catch (Exception e) {
            MyAlert.showErrorMessage(null, e.getMessage());
        }
    }

    public Bug getSelectedBug() {
        return tableViewBugs.getSelectionModel().getSelectedItem();
    }

    public void buttonInWorksBugClicked(ActionEvent actionEvent) {
        Bug bug = getSelectedBug();
        if (bug == null) {  // nothing was selected
            MyAlert.showMessage(null, Alert.AlertType.WARNING, "Warning", "You have not selected any bug");
            return;
        }
        try {
            bug.setStatus(Status.IN_WORKS);
            if (this.service.updateBug(bug) == null) {
                MyAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Confirmation", "Updated bug successfully");
            }
            else {
                MyAlert.showMessage(null, Alert.AlertType.WARNING, "Warning", "Failed to update bug");
            }
        } catch (Exception e) {
            MyAlert.showErrorMessage(null, e.getMessage());
        }
    }
}
