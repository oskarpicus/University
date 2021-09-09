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
import java.util.*;
import java.util.stream.Collectors;


public class GameController extends UnicastRemoteObject implements Controller, Serializable, Observer {
    private transient Service service;
    private transient Player player;
    private transient Game game;
    private transient Position currentPosition;
    private transient int indexRound = 0;

    private transient final ObservableList<Player> modelPlayers = FXCollections.observableArrayList();
    private transient final ObservableList<Position> modelPositions = FXCollections.observableArrayList();

    @FXML
    transient TableView<Player> tableViewPlayers;
    @FXML
    transient TableColumn<Player, Long> tableColumnId;
    @FXML
    transient TableView<Position> tableViewPositions;
    @FXML
    transient TableColumn<Position, Integer> tableColumnIndex;
    @FXML
    transient TableColumn<Position, Integer> tableColumnValue;
    @FXML
    transient Button buttonStartGame;
    @FXML
    transient Button buttonLogout;
    @FXML
    transient Button buttonGenerate;
    @FXML
    transient Label labelN;
    @FXML
    transient Label labelPosition;

    public GameController() throws RemoteException {
    }

    @Override
    public void close() {
        ((Stage) buttonLogout.getScene().getWindow()).close();
    }

    @Override
    public void initialise(Service service, Player player) {
        this.service = service;
        this.player = player;
        ((Stage) buttonLogout.getScene().getWindow()).setTitle(player.getUsername());

        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableViewPlayers.setItems(modelPlayers);

        tableColumnIndex.setCellValueFactory(new PropertyValueFactory<>("index"));
        tableColumnValue.setCellValueFactory(new PropertyValueFactory<>("value"));
        tableViewPositions.setItems(modelPositions);

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
        this.game = game;
        Platform.runLater(this::setInGameState);
    }

    @Override
    public void displayOpponents(Collection<Player> players) throws RemoteException {
        List<Player> p = players.stream()
                .filter(player1 -> !player1.equals(player))
                .collect(Collectors.toList());
        modelPlayers.setAll(p);
    }

    @Override
    public void displayPositions(Collection<Position> positions) throws RemoteException {
        var p = positions.stream()
                .sorted(Comparator.comparingInt(Position::getIndex))
                .collect(Collectors.toList());
        modelPositions.setAll(p);
    }

    @Override
    public void displayRound(Round round) throws RemoteException {
        String message = "Player: " + round.getPlayer().getUsername() + "\n" +
                "N: " + round.getN() + "\n" +
                "New position: " + round.getNewPosition() + "\n" +
                "Paid: " + round.getPaid() + "\n" +
                "Received: " + round.getReceived();
        Platform.runLater(() -> {
            MyAlert.showMessage(null, Alert.AlertType.INFORMATION, "Round", message);
        });
    }

    @Override
    public void allowToGenerateNumber() throws RemoteException {
        Platform.runLater(() -> buttonGenerate.setDisable(false));
    }

    @Override
    public void displayRanking(List<Player> players) throws RemoteException {
        String message = players.stream()
                .map(Player::getUsername)
                .reduce("", (x, y) -> x + y + "\n");
        Platform.runLater(() -> {
            MyAlert.showMessage(null, Alert.AlertType.INFORMATION, "Ranking", message);
            setNotInGameState();
        });
    }

    public void buttonStartGameClicked(ActionEvent actionEvent) {
        if ((game = service.startGame(player)) == null) {
            MyAlert.showMessage(null, Alert.AlertType.INFORMATION, "Waiting...", "You need to wait");
        }
    }

    public void buttonLogoutClicked(ActionEvent actionEvent) {
        service.removeObserver(player);
        openWindow(Window.login);
    }

    public void buttonGenerateClicked(ActionEvent actionEvent) {
        buttonGenerate.setDisable(true);
        int n = generateRandomNumber();
        labelN.setText(Integer.valueOf(n).toString());
        Round round = new Round(-1L, n, player, game, currentPosition);
        if (currentPosition == null) {  // first turn
            round.setReceived(50);
        }
        round.setIndexRound(indexRound);
        currentPosition = service.addRound(round);
        labelPosition.setText(currentPosition.toString());
        indexRound++;
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

    private void setNotInGameState() {
        buttonStartGame.setDisable(false);
        buttonLogout.setDisable(false);
        modelPositions.clear();
        modelPlayers.clear();
        buttonGenerate.setDisable(true);
        labelN.setText("");
        labelPosition.setText("");
    }

    private void setInGameState() {
        buttonStartGame.setDisable(true);
        buttonLogout.setDisable(true);
    }

    private Integer generateRandomNumber() {
        List<Integer> possible = Arrays.asList(1, 2, 3);
        return possible.get(new Random().nextInt(possible.size()));
    }
}
