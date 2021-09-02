package controllers;

import domain.Trip;
import domain.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import services.Observer;
import services.Service;
import services.TourismAgencyException;
import tornadofx.control.DateTimePicker;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class HomeController extends UnicastRemoteObject implements Observer, Serializable {

    private Service service;
    private User loggedUser;
    private transient final ObservableList<Trip> model = FXCollections.observableArrayList();

    public HomeController() throws RemoteException{
    }

    @FXML
    transient Label labelGreeting;
    @FXML
    transient TableView<Trip> tableViewTrips;
    @FXML
    transient TableColumn<String, Trip> tableColumnDestination;
    @FXML
    transient TableColumn<String, Trip> tableColumnTransportFirm;
    @FXML
    transient TableColumn<LocalDateTime, Trip> tableColumnDepartureTime;
    @FXML
    transient TableColumn<Float, Trip> tableColumnPrice;
    @FXML
    transient TableColumn<Integer, Trip> tableColumnSeats;
    @FXML
    transient TextField textfieldDestination;
    @FXML
    transient DateTimePicker datePickerFrom;
    @FXML
    transient DateTimePicker datePickerTo;

    public void initialise(Service service, User user){
        this.service = service;
        this.loggedUser = user;
        this.labelGreeting.setText("Hello "+user.getUsername()+", here are all the trips");

        tableColumnDestination.setCellValueFactory(new PropertyValueFactory<>("destination"));
        tableColumnDepartureTime.setCellValueFactory(new PropertyValueFactory<>("departureTime"));
        tableColumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        tableColumnTransportFirm.setCellValueFactory(new PropertyValueFactory<>("transportFirm"));
        tableColumnSeats.setCellValueFactory(new PropertyValueFactory<>("seats"));

        try {
            model.setAll(service.getAllTrips());
        }catch (TourismAgencyException e){
            MyAlert.showErrorMessage(null, e.getMessage());
        }

        tableViewTrips.setItems(model);

        tableViewTrips.setRowFactory(new Callback<>() {
            @Override
            public TableRow<Trip> call(TableView<Trip> param) {
                return new TableRow<>() {
                    @Override
                    protected void updateItem(Trip item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setStyle("");
                        } else if (item.getSeats() <= 0) {
                            setStyle("-fx-background-color: red");
                        } else {
                            setStyle("");
                        }
                    }
                };
            }
        });
    }

    public void buttonSearchClicked(MouseEvent mouseEvent) {
        try{
            String destination = textfieldDestination.getText();
            LocalDateTime from = datePickerFrom.getDateTimeValue();
            LocalDateTime to = datePickerTo.getDateTimeValue();

            if(destination.equals("") || from==null || to==null){
                MyAlert.showMessage(null, Alert.AlertType.WARNING, "Warning", "You need to complete all fields");
                return;
            }

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/searchTrip.fxml"));
            Pane pane = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(pane));
            stage.show();

            SearchTripController controller = loader.getController();
            controller.initialise(service, loggedUser, destination, from, to);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void buttonBookTripClicked(ActionEvent actionEvent) {
        try{
            Trip selectedTrip = getSelectedTrip();
            if(selectedTrip==null){
                MyAlert.showMessage(null, Alert.AlertType.WARNING, "Warning", "You have not selected any trips !");
                return;
            }
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/bookTrip.fxml"));
            Pane pane = loader.load();
            BookTripController controller = loader.getController();
            controller.initialise(service, loggedUser, selectedTrip);

            Stage stage = new Stage();
            stage.setScene(new Scene(pane));
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private Trip getSelectedTrip(){
        return this.tableViewTrips.getSelectionModel().getSelectedItem();
    }

    private void closeWindow(){
        Stage stage = (Stage) labelGreeting.getScene().getWindow();
        stage.close();
    }

    public void buttonLogOutClicked(ActionEvent actionEvent) {
        try {
            service.logOut(loggedUser, this);
        }catch (TourismAgencyException e){
            MyAlert.showErrorMessage(null, e.getMessage());
        }
        closeWindow();
        System.exit(0);
    }

    @Override
    public void tripChanged(Trip trip) {
        List<Trip> list = model.stream()
                .filter(trip1 -> !trip1.getId().equals(trip.getId()))
                .collect(Collectors.toList());
        list.add(trip);
        model.setAll(list);
    }
}
