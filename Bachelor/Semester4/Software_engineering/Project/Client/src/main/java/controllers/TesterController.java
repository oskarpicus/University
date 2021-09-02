package controllers;

import domain.Tester;
import domain.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.Service;

public class TesterController implements Controller {

    private Service service;
    private Tester loggedTester;

    @FXML
    Label labelGreeting;

    @Override
    public void close() {
        Stage stage = (Stage) labelGreeting.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialise(Service service, User user) {
        this.service = service;
        this.loggedTester = (Tester)user;
        labelGreeting.setText("Hello " + loggedTester.getUsername());
    }

    @Override
    public Service getService() {
        return service;
    }

    @Override
    public User getLoggedUser() {
        return loggedTester;
    }

    public void buttonAddBugClicked(MouseEvent mouseEvent) {
        openWindow(Window.addBug);
    }

    public void buttonViewBugsClicked(MouseEvent mouseEvent) {
        openWindow(Window.viewBugsTester);
    }
}
