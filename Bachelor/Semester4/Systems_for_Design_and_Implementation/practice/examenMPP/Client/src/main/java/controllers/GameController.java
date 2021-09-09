package controllers;

import domain.Game;
import domain.Guess;
import domain.Player;
import domain.Word;
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
import java.util.Map;
import java.util.Objects;

public class GameController extends UnicastRemoteObject implements Serializable, Controller, Observer {

    private transient Service service;
    private transient Player player;
    private transient Game game;

    private transient final ObservableList<Player> model = FXCollections.observableArrayList();

    @FXML
    transient TextField textFieldWord;
    @FXML
    transient TextField textFieldLetter;
    @FXML
    transient Button buttonGuess;
    @FXML
    transient Button buttonLogout;
    @FXML
    transient Button buttonStartGame;
    @FXML
    transient TableView<Player> tableViewPlayers;
    @FXML
    transient TableColumn<Player, String> tableColumnUsername;
    @FXML
    transient TableColumn<Player, String> tableColumnWord;

    public GameController() throws RemoteException {
    }

    @Override
    public void close() {
        ((Stage) textFieldLetter.getScene().getWindow()).close();
    }

    @Override
    public void initialise(Service service, Player player) {
        this.service = service;
        this.player = player;
        ((Stage) textFieldLetter.getScene().getWindow()).setTitle(player.getUsername());
        service.addObserver(player, this);

        tableColumnUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        tableColumnWord.setCellValueFactory(new PropertyValueFactory<>("word"));
        tableViewPlayers.setItems(model);
    }

    @Override
    public Service getService() {
        return service;
    }

    @Override
    public Player getLoggedPlayer() {
        return player;
    }

    public void buttonStartGameClicked(ActionEvent actionEvent) {
        String word = textFieldWord.getText();
        Word word1 = new Word(-1L, word, player);
        String errors = service.startGame(word1);
        if (!errors.equals("")) {
            MyAlert.showErrorMessage(null, errors);
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
        String letter = textFieldLetter.getText();
        if (letter.length() != 1) {
            MyAlert.showErrorMessage(null, "You must insert a letter (only)");
            return;
        }
        Player targetPlayer = getSelectedPlayer();
        if (targetPlayer == null) {
            MyAlert.showErrorMessage(null, "You need to select a player");
            return;
        }
        if (targetPlayer.equals(this.player)) {
            MyAlert.showErrorMessage(null, "You can't select yourself");
            return;
        }
        Guess guess = new Guess(-1L, letter, player, game, targetPlayer);
        service.addGuess(guess);
        buttonGuess.setDisable(true);
    }

    @Override
    public void canStartGame(Game game) throws RemoteException {
        this.game = game;
        Platform.runLater(this::setInGameState);
    }

    @Override
    public void displayWordLetters(Map<Player, String> playerWord) throws RemoteException {
        playerWord.forEach(Player::setWord);
        Platform.runLater(() -> model.setAll(playerWord.keySet()));
    }

    @Override
    public void displayPoints(Map<Player, Integer> points) throws RemoteException {
        String message = points.entrySet().stream()
                .map(e -> e.getKey().getUsername() + ": " + e.getValue() + " points\n")
                .reduce("", (x, y) -> x + y);
        Platform.runLater(() -> {
            MyAlert.showMessage(null, Alert.AlertType.INFORMATION, "End round", message);
            buttonGuess.setDisable(false);
        });
    }

    @Override
    public void displayRanking(Map<Player, Integer> points) throws RemoteException {
        String message = points.entrySet().stream()
                .sorted((e1, e2) -> -e1.getValue().compareTo(e2.getValue()))
                .map(e -> e.getKey().getUsername() + ": " + e.getValue() + " points\n")
                .reduce("", (x, y) -> x + y);
        Platform.runLater(() -> {
            MyAlert.showMessage(null, Alert.AlertType.INFORMATION,"End game", message);
            setNotInGameState();
        });
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

    private void setInGameState() {
        buttonLogout.setDisable(true);
        buttonStartGame.setDisable(true);
        textFieldWord.setDisable(true);
        buttonGuess.setDisable(false);
        textFieldLetter.setDisable(false);
    }

    private void setNotInGameState() {
        buttonLogout.setDisable(false);
        buttonStartGame.setDisable(false);
        textFieldWord.setDisable(false);
        buttonGuess.setDisable(true);
        textFieldLetter.setDisable(true);
        model.clear();
        textFieldLetter.clear();
        textFieldWord.clear();
    }

    private Player getSelectedPlayer() {
        return tableViewPlayers.getSelectionModel().getSelectedItem();
    }
}
