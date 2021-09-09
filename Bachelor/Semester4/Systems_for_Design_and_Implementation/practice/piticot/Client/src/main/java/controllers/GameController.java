package controllers;

import domain.Game;
import domain.Player;
import domain.Position;
import domain.Round;
import javafx.application.Platform;
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
import java.util.Objects;
import java.util.Random;

public class GameController extends UnicastRemoteObject implements Controller, Serializable, Observer {

    private transient Service service;
    private transient Player player;
    private transient int indexRound = 0;
    private transient Game game;
    private transient Position currentPosition;

    private transient final ObservableList<Position> model = FXCollections.observableArrayList();

    @FXML
    transient Label labelOpponent;
    @FXML
    transient Label labelMyPosition;
    @FXML
    transient Label labelOpponentPosition;
    @FXML
    transient Button buttonStartGame;
    @FXML
    transient Button buttonLogout;
    @FXML
    transient Button buttonGenerate;
    @FXML
    transient TableView<Position> tableViewPositions;
    @FXML
    transient TableColumn<Position, Integer> tableColumnIndex;
    @FXML
    transient TableColumn<Position, String> tableColumnText;

    public GameController() throws RemoteException {
    }

    @Override
    public void close() {
        ((Stage) tableViewPositions.getScene().getWindow()).close();
    }

    @Override
    public void initialise(Service service, Player player) {
        this.service = service;
        this.player = player;
        ((Stage) tableViewPositions.getScene().getWindow()).setTitle(player.getUsername());

        tableColumnIndex.setCellValueFactory(new PropertyValueFactory<>("index"));
        tableColumnText.setCellValueFactory(new PropertyValueFactory<>("text"));
        tableViewPositions.setItems(model);

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
    public void startGame(Game game) throws RemoteException {
        this.game = game;
        Platform.runLater(() -> {
            this.setInGameState();
            model.setAll(game.getPositions());
        });
    }

    @Override
    public void setOpponent(Player player) throws RemoteException {
        Platform.runLater(() -> labelOpponent.setText(player.getUsername()));
    }

    @Override
    public void displayRound(Integer n, Position myPosition, Position opponentPosition) throws RemoteException {
        String generator = "";
        boolean iGenerated = false;
        if (myPosition.equals(currentPosition)) {  // the opponent moved
            generator = "The opponent generated: " + n + "\n";
        }
        else {
            generator = "I generated: " + n + "\n";
            iGenerated = true;
        }
        String message = generator + "My position: " + myPosition + "\nOpponent position:" + opponentPosition;
        this.currentPosition = myPosition;
        Platform.runLater(() -> {
            labelMyPosition.setText("My position: " + myPosition.toString());
            labelOpponentPosition.setText("Opponent's position: " + opponentPosition.toString());
            MyAlert.showMessage(null, Alert.AlertType.INFORMATION, "Round", message);
        });
        if (iGenerated)
            Platform.runLater(() -> buttonGenerate.setDisable(true));
        else
            Platform.runLater(() -> buttonGenerate.setDisable(false));
    }

    @Override
    public void displayWinner(Player player) throws RemoteException {
        String message = "The winner is " + player.getUsername();
        Platform.runLater(() -> {
            MyAlert.showMessage(null, Alert.AlertType.INFORMATION,"Winner", message);
            setNotInGameState();
        });
    }

    public void buttonStartGameClicked(ActionEvent actionEvent) {
        setInGameState();
        if ((game = service.startGame(player)) == null) {
            MyAlert.showMessage(null, Alert.AlertType.INFORMATION, "Waiting", "Please wait while others join");
            buttonGenerate.setDisable(true);
        }
    }

    public void buttonLogoutClicked(ActionEvent actionEvent) {
        service.removeObserver(player);
        openWindow(Window.login);
    }

    public void buttonGenerateClicked(ActionEvent actionEvent) {
        int n = generateNumber();
        Round round = new Round(-1L, n, indexRound, currentPosition, game, player);
        service.addRound(round);
        indexRound++;
    }

    private void setNotInGameState() {
        indexRound = 0;
        game = null;
        currentPosition = null;
        model.clear();
        labelOpponent.setText("_");
        labelMyPosition.setText("");
        labelOpponentPosition.setText("");
        buttonGenerate.setDisable(true);
        buttonLogout.setDisable(false);
        buttonStartGame.setDisable(false);
    }

    private void setInGameState() {
        buttonGenerate.setDisable(false);
        buttonLogout.setDisable(true);
        buttonStartGame.setDisable(true);
    }

    private int generateNumber() {
        return new Random().nextInt(4);
    }
}
