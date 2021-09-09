package controllers;

import domain.Game;
import domain.Player;
import domain.Round;
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
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GameController extends UnicastRemoteObject implements Serializable, Controller, Observer {

    private transient Service service;
    private transient Player player;
    private transient Game game;
    private transient final ObservableList<Player> model = FXCollections.observableArrayList();

    @FXML
    transient TableView<Player> tableViewPlayers;
    @FXML
    transient TableColumn<Player, String> tableColumnUsername;
    @FXML
    transient Button buttonLogout;
    @FXML
    transient Button buttonStartGame;
    @FXML
    transient Button buttonSubmit;
    @FXML
    transient TextField textFieldAnswer;
    @FXML
    transient Label labelAnagram;

    public GameController() throws RemoteException {
    }

    @Override
    public void close() {
        Stage stage = (Stage) buttonStartGame.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialise(Service service, Player user) {
        this.service = service;
        this.player = user;

        tableColumnUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        tableViewPlayers.setItems(model);

        Stage stage = (Stage) textFieldAnswer.getScene().getWindow();
        stage.setTitle(user.getUsername());

        this.service.addObserver(getLoggedPlayer(), this);
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
        if (this.service.startGame() == null) {
            MyAlert.showMessage(null, Alert.AlertType.WARNING, "Active game", "There is already an active game going on, come back later");
        }
    }

    public void buttonLogoutClicked(ActionEvent actionEvent) {
        this.service.removeObserver(getLoggedPlayer());
        openWindow(Window.login);
    }

    public void buttonSubmitClicked(ActionEvent actionEvent) {
        String answer = textFieldAnswer.getText();
        Round round = new Round(-1L, player, game, answer);
        service.sendAnswer(round);
        buttonSubmit.setDisable(true);
    }

    @Override
    public void setOnlinePlayers(List<Player> players) throws RemoteException {
        Platform.runLater(() -> model.setAll(players));
    }

    @Override
    public void prepareStartGame(Game game) throws RemoteException {
        this.game = game;
        Platform.runLater(this::setPostGameState);
    }

    @Override
    public void setAnagram(String anagram) throws RemoteException {
        Platform.runLater(() -> {
            buttonSubmit.setDisable(false);
            textFieldAnswer.setText("");
            labelAnagram.setText(anagram);
        });
    }

    @Override
    public void displayResultsRound(Map<Player, Integer> points, Word correctWord) throws RemoteException {
        String message = "This round:\n" +
                points.entrySet().stream()
                .map(e -> e.getKey().getUsername() + ": " + e.getValue() + "\n")
                .reduce("", (x, y) -> x + y) +
                "The correct word was " + correctWord.getWord();
        Platform.runLater(() -> MyAlert.showMessage(null, Alert.AlertType.INFORMATION, "Information", message));
    }

    @Override
    public void endGame() throws RemoteException {
        Platform.runLater(() -> {
            MyAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "End", "The game ended!");
            setPreGameState();
        });
    }

    private void setPreGameState() {
        buttonStartGame.setDisable(false);
        buttonLogout.setDisable(false);
        textFieldAnswer.setDisable(true);
        buttonSubmit.setDisable(true);
        model.clear();
    }

    private void setPostGameState() {
        buttonStartGame.setDisable(true);
        buttonLogout.setDisable(true);
        textFieldAnswer.setDisable(false);
        buttonSubmit.setDisable(false);
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
}
