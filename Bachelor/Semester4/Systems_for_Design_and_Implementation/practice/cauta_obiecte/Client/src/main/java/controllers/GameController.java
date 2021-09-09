package controllers;

import domain.Game;
import domain.Guess;
import domain.Item;
import domain.Player;
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
import utils.Constants;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.stream.Collectors;

public class GameController extends UnicastRemoteObject implements Controller, Serializable, Observer {

    private transient Service service;
    private transient Player player;
    private transient Game game;
    private transient int indexRound = 1;

    private transient final ObservableList<Player> model = FXCollections.observableArrayList();

    @FXML
    transient Button buttonStartGame;
    @FXML
    transient Button buttonLogout;
    @FXML
    transient Button buttonGuess;
    @FXML
    transient TextField textFieldItem1;
    @FXML
    transient TextField textFieldItem2;
    @FXML
    transient TextField textFieldItem3;
    @FXML
    transient TextField textFieldGuess;
    @FXML
    transient TableView<Player> tableViewPlayers;
    @FXML
    transient TableColumn<Player, String> tableColumnUsername;
    @FXML
    transient TableColumn<Player, String> tableColumnPositions;

    public GameController() throws RemoteException {
    }

    @Override
    public void close() {
        ((Stage) textFieldGuess.getScene().getWindow()).close();
    }

    @Override
    public void initialise(Service service, Player player) {
        this.service = service;
        this.player = player;
        ((Stage) textFieldGuess.getScene().getWindow()).setTitle(player.getUsername());
        service.addObserver(player, this);

        tableColumnUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        tableColumnPositions.setCellValueFactory(new PropertyValueFactory<>("positions"));
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameController that = (GameController) o;
        return Objects.equals(player, that.player);
    }

    @Override
    public int hashCode() {
        return Objects.hash(player);
    }

    @Override
    public void startGame(Game game) throws RemoteException {
        Platform.runLater(() -> {
            this.game = game;
            setInGameState();
        });
    }

    @Override
    public void displayPositions(Map<Player, String> positions) throws RemoteException {
        positions.forEach(Player::setPositions);
        model.setAll(positions.keySet());
        Platform.runLater(() -> buttonGuess.setDisable(false));
    }

    @Override
    public void displayRanking(Map<Player, Integer> points) throws RemoteException {
        String message = points.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .map(e -> e.getKey().getUsername() + ": " + e.getValue() + " points\n")
                .reduce("", (x, y) -> x + y);
        Platform.runLater(() -> {
            MyAlert.showMessage(null, Alert.AlertType.INFORMATION, "End game", message);
            setNotInGameState();
        });
    }

    public void buttonStartGameClicked(ActionEvent actionEvent) {
        List<Integer> integers = new ArrayList<>();
        try {
            integers.add(Integer.parseInt(textFieldItem1.getText()));
            integers.add(Integer.parseInt(textFieldItem2.getText()));
            integers.add(Integer.parseInt(textFieldItem3.getText()));
        } catch (NumberFormatException e) {
            MyAlert.showErrorMessage(null, "You need to insert numbers as positions!");
            return;
        }
        for (Integer i : integers) {
            if (i < Constants.MINIMUM_POSITION || i > Constants.MAXIMUM_POSITION) {
                MyAlert.showErrorMessage(null,"Incorrect positions [1, 9]");
                return;
            }
        }
        List<Item> items = integers.stream()
                .map(integer -> new Item(-1L, integer, player))
                .collect(Collectors.toList());
        setInGameState();
        if ((game = service.startGame(items)) == null) {
            MyAlert.showMessage(null, Alert.AlertType.INFORMATION, "Waiting", "Waiting for others to join");
            buttonGuess.setDisable(true);
            textFieldGuess.setDisable(true);
        }
    }

    public void buttonLogoutClicked(ActionEvent actionEvent) {
        service.removeObserver(player);
        openWindow(Window.login);
    }

    public void buttonGuessClicked(ActionEvent actionEvent) {
        Player targetPlayer = getSelectedPlayer();
        if (targetPlayer == null) {
            MyAlert.showErrorMessage(null, "You need to select a player first");
            return;
        }
        int position;
        try {
            position = Integer.parseInt(textFieldGuess.getText());
        } catch (NumberFormatException e) {
            MyAlert.showErrorMessage(null, "Position is not a number");
            return;
        }
        Guess guess = new Guess(-1L, indexRound, position, player, game, targetPlayer);
        service.addGuess(guess);
        buttonGuess.setDisable(true);
        indexRound++;
    }

    private void setInGameState() {
        textFieldItem1.setDisable(true);
        textFieldItem2.setDisable(true);
        textFieldItem3.setDisable(true);
        buttonStartGame.setDisable(true);
        buttonLogout.setDisable(true);
        textFieldGuess.setDisable(false);
        buttonGuess.setDisable(false);
    }

    private void setNotInGameState() {
        textFieldItem1.setDisable(false);
        textFieldItem2.setDisable(false);
        textFieldItem3.setDisable(false);
        buttonStartGame.setDisable(false);
        buttonLogout.setDisable(false);
        model.clear();
        textFieldGuess.setDisable(true);
        buttonGuess.setDisable(true);
        textFieldGuess.clear();
        textFieldItem1.clear();
        textFieldItem2.clear();
        textFieldItem3.clear();
        indexRound = 1;
    }

    private Player getSelectedPlayer() {
        return tableViewPlayers.getSelectionModel().getSelectedItem();
    }
}
