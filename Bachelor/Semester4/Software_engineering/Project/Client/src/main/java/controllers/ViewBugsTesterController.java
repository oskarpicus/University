package controllers;

import domain.Bug;
import domain.Tester;
import domain.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.Service;

import java.util.List;

public class ViewBugsTesterController implements Controller {

    @FXML
    TableView<Bug> tableViewBugs;
    @FXML
    TableColumn<Bug, String> tableColumnName;
    @FXML
    TableColumn<Bug, String> tableColumnSeverity;

    private Service service;
    private Tester loggedTester;
    private final ObservableList<Bug> model = FXCollections.observableArrayList();

    @Override
    public void close() {
        Stage stage = (Stage) tableViewBugs.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialise(Service service, User user) {
        this.service = service;
        this.loggedTester = (Tester) user;

        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnSeverity.setCellValueFactory(new PropertyValueFactory<>("severity"));
        tableViewBugs.setItems(model);
        displayBugs(service.findBugsByTester((Tester)getLoggedUser()));
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

    public void buttonAddBugClicked(MouseEvent mouseEvent) {
        openWindow(Window.addBug);
    }

    private void displayBugs(List<Bug> bugs) {
        model.setAll(bugs);
    }
}
