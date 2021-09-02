package controllers;

import domain.User;
import domain.dtos.TripDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import services.Service;
import services.TourismAgencyException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SearchTripController {

    private Service service;
    private User loggedUser;
    private final ObservableList<TripDTO> model = FXCollections.observableArrayList();
    private String destination;
    private LocalDateTime from;
    private LocalDateTime to;

    @FXML
    Label labelGreeting;
    @FXML
    TableView<TripDTO> tableViewTrips;
    @FXML
    TableColumn<String, TripDTO> tableColumnTransportFirm;
    @FXML
    TableColumn<LocalDateTime, TripDTO> tableColumnDepartureTime;
    @FXML
    TableColumn<Float, TripDTO> tableColumnPrice;
    @FXML
    TableColumn<Integer, TripDTO> tableColumnSeats;

    public void initialise(Service service, User loggedUser, String destination, LocalDateTime from, LocalDateTime to){
        this.service = service;
        this.loggedUser = loggedUser;
        this.destination = destination;
        this.from = from;
        this.to = to;

        from = from.minusNanos(from.getNano());
        to = to.minusNanos(to.getNano());

        labelGreeting.setText("Trips in "+destination+", between "+from+" and "+to);
        List<TripDTO> list = new ArrayList<>();

        try {
            list = service.findTrips(destination, from, to);
        }catch (TourismAgencyException e){
            MyAlert.showErrorMessage(null, e.getMessage());
        }

        if(list.isEmpty()){
            MyAlert.showMessage(null, Alert.AlertType.INFORMATION, "Information", "There are no trips to show !");
            closeWindow();
            return;
        }

        tableColumnDepartureTime.setCellValueFactory(new PropertyValueFactory<>("departureTime"));
        tableColumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        tableColumnSeats.setCellValueFactory(new PropertyValueFactory<>("seats"));
        tableColumnTransportFirm.setCellValueFactory(new PropertyValueFactory<>("transportFirm"));
        tableViewTrips.setItems(model);
        model.setAll(list);

        tableViewTrips.setRowFactory(new Callback<>() {
            @Override
            public TableRow<TripDTO> call(TableView<TripDTO> param) {
                return new TableRow<>() {
                    @Override
                    protected void updateItem(TripDTO item, boolean empty) {
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

    private void closeWindow(){
        Stage stage = (Stage)labelGreeting.getScene().getWindow();
        stage.close();
    }
}
