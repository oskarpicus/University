package controllers;

import domain.Card;
import domain.Game;
import domain.Player;
import domain.Round;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
import java.util.Set;

public class GameController extends UnicastRemoteObject implements Controller, Serializable, Observer {

    private transient Service service;
    private transient Player player;
    private transient Game game;
    private transient final ObservableList<Player> modelPlayers = FXCollections.observableArrayList();
    private transient final ObservableList<Card> modelCards = FXCollections.observableArrayList();

    @FXML
    transient Button buttonStartGame;
    @FXML
    transient Button buttonLogout;
    @FXML
    transient Button buttonSendCard;
    @FXML
    transient TableView<Player> tableViewPlayers;
    @FXML
    transient TableColumn<Player, String> tableColumnUsername;
    @FXML
    transient TableView<Card> tableViewCards;
    @FXML
    transient TableColumn<Card, String> tableColumnCard;

    public GameController() throws RemoteException {
    }

    @Override
    public void close() {
        ((Stage) buttonStartGame.getScene().getWindow()).close();
    }

    @Override
    public void initialise(Service service, Player player) {
        this.player = player;
        this.service = service;

        tableColumnUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        tableColumnCard.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableViewPlayers.setItems(modelPlayers);
        tableViewCards.setItems(modelCards);

        this.service.addObserver(player,this);
        ((Stage) buttonSendCard.getScene().getWindow()).setTitle(player.getUsername());
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
    public void setOnlinePlayers(List<Player> players) throws RemoteException {
        Platform.runLater(() -> modelPlayers.setAll(players));
    }

    @Override
    public void setCards(Set<Card> cards) throws RemoteException {
        Platform.runLater(() -> modelCards.setAll(cards));
    }

    @Override
    public void startGame(Game game) throws RemoteException {
        this.game = game;
        Platform.runLater(this::setInGameState);
    }

    @Override
    public void displayCardsChosen(Map<Player, Card> cards, String roundWinnerUsername) throws RemoteException {
        String message = "Cards chosen:\n" +
                cards.entrySet().stream()
                .map(entry -> entry.getKey().getUsername() + " : " + entry.getValue().getName() + "\n")
                .reduce("", (x, y) -> x + y) + "\n" +
                "Round winner: " + roundWinnerUsername;
        Platform.runLater(() -> {
            MyAlert.showMessage(null, Alert.AlertType.INFORMATION, "End round", message);
            buttonSendCard.setDisable(false);
        });
    }

    @Override
    public void displayWinner(Player winner) throws RemoteException {
        Platform.runLater(() -> MyAlert.showMessage(null, Alert.AlertType.INFORMATION, "Winner", "The winner is " + winner.getUsername()));
        Platform.runLater(this::setNotInGameState);
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
        buttonSendCard.setDisable(true);
        buttonStartGame.setDisable(false);
        buttonLogout.setDisable(false);
    }

    private void setInGameState() {
        buttonStartGame.setDisable(true);
        buttonLogout.setDisable(true);
        buttonSendCard.setDisable(false);
    }

    public void buttonStartGameClicked(ActionEvent actionEvent) {
        if ((game = service.startGame()) == null) {
            MyAlert.showMessage(null, Alert.AlertType.WARNING, "Can't start game",
                    "Try again later");
        }
    }

    public void buttonLogoutClicked(ActionEvent actionEvent) {
        this.service.removeObserver(player);
        openWindow(Window.login);
    }

    public void buttonSendCardClicked(ActionEvent actionEvent) {
        Card sentCard = getSelectedCard();
        if (sentCard == null) {
            MyAlert.showMessage(null, Alert.AlertType.WARNING, "Warning", "You need to select a card");
            return;
        }
        buttonSendCard.setDisable(true);
        Round round = new Round(-1L, player, game, sentCard);
        modelCards.remove(sentCard);
        service.sendCard(round);
    }

    private Card getSelectedCard() {
        return tableViewCards.getSelectionModel().getSelectedItem();
    }
}