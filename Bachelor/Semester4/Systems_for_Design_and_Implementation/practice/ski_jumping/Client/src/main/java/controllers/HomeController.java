package controllers;

import domain.JuryMember;
import domain.Mark;
import domain.Participant;
import domain.Status;
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
import java.util.Objects;

public class HomeController extends UnicastRemoteObject implements Serializable, Observer, Controller {

    private transient Service service;
    private transient JuryMember juryMember;
    private transient final ObservableList<Participant> model = FXCollections.observableArrayList();

    @FXML
    transient Label labelGreeting;
    @FXML
    transient Label labelAspect;
    @FXML
    transient TableView<Participant> tableViewParticipants;
    @FXML
    transient TableColumn<Participant, String> tableColumnName;
    @FXML
    transient TableColumn<Participant, Status> tableColumnStatus;
    @FXML
    transient TableColumn<Participant, Integer> tableColumnPoints;
    @FXML
    transient TextField textFieldMark;

    public HomeController() throws RemoteException {
    }

    @Override
    public void close() {
        ((Stage) textFieldMark.getScene().getWindow()).close();
    }

    @Override
    public void initialise(Service service, JuryMember juryMember) {
        this.service = service;
        this.juryMember = juryMember;

        tableViewParticipants.setItems(model);
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        tableColumnPoints.setCellValueFactory(new PropertyValueFactory<>("points"));

        labelGreeting.setText("Hello " + juryMember.getUsername());
        labelAspect.setText("Aspect " + juryMember.getAspect());

        service.addObserver(juryMember, this);
    }

    @Override
    public Service getService() {
        return service;
    }

    @Override
    public JuryMember getLoggedJuryMember() {
        return juryMember;
    }

    @Override
    public void setParticipants(List<Participant> participants) throws RemoteException {
        model.setAll(participants);
    }

    @Override
    public void updateParticipant(Participant participant) throws RemoteException {
        int index = model.indexOf(participant);
        model.set(index, participant);
    }

    public void buttonLogoutClicked(ActionEvent actionEvent) {
        service.removeObserver(getLoggedJuryMember());
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

    public void buttonSubmitClicked(ActionEvent actionEvent) {
        Participant participant = getSelectedParticipant();
        if (participant == null) {
            MyAlert.showMessage(null, Alert.AlertType.WARNING, "Warning", "You need to select a participant first !");
            return;
        }
        if (participant.getStatus().equals(Status.FINISHED)) {
            MyAlert.showErrorMessage(null, "Mark already submitted for this participant");
            return;
        }
        int nrPoints = 0;
        try {
            nrPoints = Integer.parseInt(textFieldMark.getText());
            textFieldMark.clear();
        } catch (NumberFormatException e) {
            MyAlert.showErrorMessage(null, "Number of points is not a number");
            return;
        }
        Mark mark = new Mark(-1L, participant, juryMember, nrPoints, participant.getStatus().ordinal());
        if (service.addMark(mark) == null) {
            MyAlert.showErrorMessage(null, "Could not assign mark");
        }
        else {
            MyAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Success", "Mark successfully added");
        }
    }

    private Participant getSelectedParticipant() {
        return tableViewParticipants.getSelectionModel().getSelectedItem();
    }
}
