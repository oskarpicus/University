package controllers;

import domain.JuryMember;
import domain.Mark;
import domain.Participant;
import domain.Status;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import services.Observer;
import services.Service;
import utils.MyAlert;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Objects;

public class HomeController extends UnicastRemoteObject implements Observer, Serializable, Controller {

    private transient Service service;
    private transient JuryMember juryMember;
    private transient final ObservableList<Participant> model = FXCollections.observableArrayList();

    @FXML
    transient TableView<Participant> tableViewParticipants;
    @FXML
    transient TableColumn<Participant, String> tableColumnName;
    @FXML
    transient TableColumn<Participant, Status> tableColumnStatus;
    @FXML
    transient TableColumn<Participant, Integer> tableColumnPoints;
    @FXML
    transient TextField textFieldNrPoints;

    public HomeController() throws RemoteException {
    }

    @Override
    public void markAdded(Mark mark) throws RemoteException {
        Platform.runLater(() -> {
            int index = model.indexOf(mark.getParticipant());
            model.set(index, mark.getParticipant());
        });
    }

    @Override
    public void close() {
        Stage stage = (Stage) textFieldNrPoints.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialise(Service service, JuryMember user) {
        this.service = service;
        this.juryMember = user;

        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        tableColumnPoints.setCellValueFactory(new PropertyValueFactory<>("points"));
        tableViewParticipants.setItems(model);

        model.setAll(service.getAllParticipants());

        this.service.addObserver(this);
    }

    @Override
    public Service getService() {
        return this.service;
    }

    @Override
    public JuryMember getLoggedUser() {
        return this.juryMember;
    }

    public void buttonSubmitClicked(ActionEvent actionEvent) {
        int points = 0;
        try {
            points = Integer.parseInt(textFieldNrPoints.getText());
        } catch (NumberFormatException e) {
            MyAlert.showErrorMessage(null, "Bad text");
            return;
        }
        Participant participant = this.getSelectedParticipant();
        if (participant == null) {
            MyAlert.showMessage(null, Alert.AlertType.WARNING, "Warning", "You need to select a participant");
            return;
        }
        Mark mark = new Mark(-1L, points, getLoggedUser(), participant);
        Mark result = this.service.addMark(mark);
        if (result == null) {  // successfully added mark
            MyAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Confirmation", "Mark added successfully");
        }
        else {
            MyAlert.showErrorMessage(null, "You already added this mark");
        }
    }

    public void buttonLogoutClicked(ActionEvent actionEvent) {
        this.service.removeObserver(this);
        openWindow(Window.login);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        HomeController that = (HomeController) o;
        return Objects.equals(juryMember, that.juryMember);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), juryMember);
    }

    private Participant getSelectedParticipant() {
        return tableViewParticipants.getSelectionModel().getSelectedItem();
    }
}
