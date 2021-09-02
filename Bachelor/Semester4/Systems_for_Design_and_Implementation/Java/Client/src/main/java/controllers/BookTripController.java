package controllers;

import domain.Reservation;
import domain.Trip;
import domain.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.Service;

import java.util.Optional;

public class BookTripController {

    private Service service;
    private User loggedUser;
    private Trip trip;

    @FXML
    Spinner<Integer> spinnerTickets;
    @FXML
    TextField textFieldClient;
    @FXML
    TextField textFieldPhoneNumber;

    void initialise(Service service, User user, Trip trip){
        this.service = service;
        this.loggedUser = user;
        this.trip = trip;
    }

    public void buttonMakeReservationClicked(ActionEvent actionEvent) {
        String client = textFieldClient.getText();
        String phoneNumber = textFieldPhoneNumber.getText();
        int tickets = spinnerTickets.getValue();

        if(client.equals("") || phoneNumber.equals("")){
            MyAlert.showMessage(null, Alert.AlertType.WARNING, "Warning", "You haven't filled all forms !");
            return;
        }

        try {
            Reservation result = service.bookTrip(client, phoneNumber, tickets, trip, loggedUser);
            if (result==null) {
                MyAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Confirmation", "Reservation successfully saved");
                closeWindow();
            } else {
                MyAlert.showErrorMessage(null, "Failed to make reservation");
            }
        }
        catch (Exception e){
            MyAlert.showErrorMessage(null, e.getMessage());
        }
    }

    private void closeWindow(){
        Stage stage = (Stage)textFieldClient.getScene().getWindow();
        stage.close();
    }
}
