package controllers;

import domain.Game;
import domain.Guess;
import domain.Plane;
import domain.Player;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import services.Observer;
import services.Service;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Optional;

public class GameController extends UnicastRemoteObject implements Controller, Serializable, Observer {
    private transient Service service;
    private transient Player player;
    private transient Game game;
    private transient Plane plane;

    @FXML
    transient Button buttonStartGame;
    @FXML
    transient Button buttonLogout;
    @FXML
    transient Button buttonGuess;
    @FXML
    transient TextField textFieldLinePlane;
    @FXML
    transient TextField textFieldColumnPlane;
    @FXML
    transient TextField textFieldLineGuess;
    @FXML
    transient TextField textFieldColumnGuess;
    @FXML
    transient Label labelIdOpponent;
    @FXML
    transient GridPane gridPane;
    @FXML
    transient Label labelPlane;

    public GameController() throws RemoteException {
    }

    @Override
    public void close() {
        ((Stage)textFieldColumnGuess.getScene().getWindow()).close();
    }

    @Override
    public void initialise(Service service, Player player) {
        this.service = service;
        this.player = player;
        ((Stage)textFieldColumnGuess.getScene().getWindow()).setTitle(player.getUsername());
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

    @Override
    public void startGame(Game game) throws RemoteException {
        Optional<Long> otherId = game.getPlanes().stream()
                .filter(plane -> !plane.getPlayer().equals(player))
                .map(plane -> plane.getPlayer().getId())
                .findFirst();
        otherId.ifPresent(aLong -> Platform.runLater(() -> labelIdOpponent.setText(String.valueOf(aLong))));
        Platform.runLater(() -> {
            this.game = game;
            setInGameState();
            labelPlane.setText("X");
            gridPane.add(labelPlane, plane.getLine(), plane.getColumn());
        });
    }

    @Override
    public void displayGuessOpponent(Guess guess) throws RemoteException {
        String message = "Opponent guessed:\n(" + guess.getLine() + "," + guess.getColumn() + ")";
        Platform.runLater(() -> {
            MyAlert.showMessage(null, Alert.AlertType.INFORMATION, "Guess opponent", message);
            buttonGuess.setDisable(false);
        });
    }

    @Override
    public void displayWinner(Player player) throws RemoteException {
        Platform.runLater(() -> {
            MyAlert.showMessage(null, Alert.AlertType.INFORMATION, "Winner", "The winner is: " + player.getUsername());
            setNotInGameState();
        });
    }

    private void setNotInGameState() {
        labelIdOpponent.setText("_");
        textFieldColumnGuess.setDisable(true); textFieldLineGuess.clear();
        textFieldLineGuess.setDisable(true); textFieldLineGuess.clear();
        textFieldColumnPlane.setDisable(false); textFieldColumnPlane.clear();
        textFieldLinePlane.setDisable(false); textFieldLinePlane.clear();
        buttonStartGame.setDisable(false);
        buttonLogout.setDisable(false);
        buttonGuess.setDisable(true);
        labelIdOpponent.setText("");
        labelPlane.setText("");
    }

    private void setInGameState() {
        textFieldColumnGuess.setDisable(false);
        textFieldLineGuess.setDisable(false);
        textFieldColumnPlane.setDisable(true);
        textFieldLinePlane.setDisable(true);
        buttonStartGame.setDisable(true);
        buttonLogout.setDisable(true);
        buttonGuess.setDisable(false);
    }

    public void buttonStartGameClicked(ActionEvent actionEvent) {
        String lineString = textFieldLinePlane.getText();
        String columnString = textFieldColumnPlane.getText();
        if (lineString.equals("") || columnString.equals("")) {
            MyAlert.showErrorMessage(null, "You need to give coordinates to your plane");
            return;
        }
        int line, column;
        try {
            line = Integer.parseInt(lineString);
            column = Integer.parseInt(columnString);
        } catch (NumberFormatException e) {
            MyAlert.showErrorMessage(null, "Coordinates must be numbers");
            return;
        }
        plane = new Plane(-1L, line, column, player);
        if ((game = service.startGame(plane)) == null) {
            MyAlert.showMessage(null, Alert.AlertType.INFORMATION, "Information", "Your request has been registered. Please wait for the game to start");
        }
        else {
            setInGameState();
        }
    }

    public void buttonLogoutClicked(ActionEvent actionEvent) {
        service.removeObserver(player);
        openWindow(Window.login);
    }

    public void buttonGuessClicked(ActionEvent actionEvent) {
        String lineString = textFieldLineGuess.getText();
        String columnString = textFieldColumnGuess.getText();
        if (lineString.equals("") || columnString.equals("")) {
            MyAlert.showErrorMessage(null, "You need to give coordinates to your plane");
            return;
        }
        int line, column;
        try {
            line = Integer.parseInt(lineString);
            column = Integer.parseInt(columnString);
        } catch (NumberFormatException e) {
            MyAlert.showErrorMessage(null, "Coordinates must be numbers");
            return;
        }
        Guess guess = new Guess(-1L, line, column, player, game);
        buttonGuess.setDisable(true);
        service.addGuess(guess);
    }
}
