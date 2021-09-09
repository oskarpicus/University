package controllers;

import domain.Answer;
import domain.Game;
import domain.Player;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.Observer;
import services.Service;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.Objects;

public class GameController extends UnicastRemoteObject implements Serializable, Controller, Observer {

    private transient Service service;
    private transient Player player;
    private transient Game game;

    @FXML
    transient TextField textFieldCountry;
    @FXML
    transient TextField textFieldCity;
    @FXML
    transient TextField textFieldSea;
    @FXML
    transient Button buttonStartGame;
    @FXML
    transient Button buttonSubmit;
    @FXML
    transient Label labelLetter;
    @FXML
    transient Button buttonLogout;

    public GameController() throws RemoteException {
    }

    @Override
    public void close() {
        Stage stage = (Stage) textFieldCity.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialise(Service service, Player player) {
        this.service = service;
        this.player = player;

        Stage stage = (Stage) textFieldCity.getScene().getWindow();
        stage.setTitle(player.getUsername());

        service.addObserver(player, this);
    }

    @Override
    public Service getService() {
        return service;
    }

    @Override
    public Player getLoggedPlayer() {
        return player;
    }

    public void buttonLogoutClicked(ActionEvent actionEvent) {
        service.removeObserver(getLoggedPlayer());
        openWindow(Window.login);
    }

    public void buttonStartGameClicked(ActionEvent actionEvent) {
        service.startGame(getLoggedPlayer());
    }

    public void buttonSubmitClicked(ActionEvent actionEvent) {
        String city = textFieldCity.getText();
        String country = textFieldCountry.getText();
        String sea = textFieldSea.getText();
        if (sea.equals("")) sea = " ";
        if (country.equals("")) country = " ";
        if (city.equals("")) city = " ";
        Answer answer = new Answer(-1L, labelLetter.getText(), 0, city, sea, country, getLoggedPlayer(), game);
        this.service.sendAnswer(answer);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        GameController that = (GameController) o;
        return Objects.equals(player, that.player);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), player);
    }

    @Override
    public void canStartGame() throws RemoteException {
        Platform.runLater(() -> {
            buttonStartGame.setDisable(false);
            buttonLogout.setDisable(true);
        });
    }

    @Override
    public void setLetter(String letter) throws RemoteException {
        Platform.runLater(() -> {
            textFieldCity.setText("");
            textFieldCountry.setText("");
            textFieldSea.setText("");
            labelLetter.setText(letter);
        });
    }

    @Override
    public void startGame(Game game) throws RemoteException {
        Platform.runLater(() -> {
            buttonStartGame.setDisable(true);
            buttonSubmit.setDisable(false);
        });
        this.game = game;
    }

    @Override
    public void displayResultsRound(Map<String, Integer> points) throws RemoteException{
        Platform.runLater(() -> {
            String message = "Results for this round:\n" +
                    points.entrySet().stream()
                    .map(x -> x.getKey() + " " + x.getValue() + "\n")
                    .reduce("", (x, y) -> x + y);
            MyAlert.showMessage(null, Alert.AlertType.INFORMATION, "Result", message);
        });
    }

    @Override
    public void displayRanking(Map<String, Integer> points) throws RemoteException{
        Platform.runLater(() -> {
            this.buttonLogout.setDisable(false);
            String message = "Total results:\n" +
                    points.entrySet().stream()
                            .sorted(Map.Entry.comparingByValue())
                            .map(x -> x.getKey() + " " + x.getValue() + "\n")
                            .reduce("", (x, y) -> x + y);
            MyAlert.showMessage(null, Alert.AlertType.INFORMATION, "Result", message);
        });
    }
}
