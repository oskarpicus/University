package controllers;

import domain.Card;
import domain.Player;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
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
import utils.MyAlert;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class GameController extends UnicastRemoteObject implements Serializable, Observer, Controller {

    private transient Service service;
    private transient Player player;
    private transient final ObservableList<String> modelPlayers = FXCollections.observableArrayList();
    private transient final ObservableList<Card> modelCards = FXCollections.observableArrayList();

    @FXML
    transient TableView<String> tableViewPlayers;
    @FXML
    transient TableColumn<String, String> tableColumnUsername;
    @FXML
    transient TableView<Card> tableViewCards;
    @FXML
    transient TableColumn<Card, String> tableColumnColor;
    @FXML
    transient TableColumn<Card, Integer> tableColumnNumber;
    @FXML
    transient Button buttonLogout;
    @FXML
    transient Button buttonStartGame;

    public GameController() throws RemoteException {
    }

    @Override
    public void close() {
        Stage stage = (Stage)tableViewPlayers.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialise(Service service, Player player) {
        this.service = service;
        this.player = player;

        tableColumnUsername.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue()));
        tableColumnColor.setCellValueFactory(new PropertyValueFactory<>("color"));
        tableColumnNumber.setCellValueFactory(new PropertyValueFactory<>("number"));

        tableViewPlayers.setItems(modelPlayers);
        tableViewCards.setItems(modelCards);

        Stage stage = (Stage)tableViewPlayers.getScene().getWindow();
        stage.setTitle(player.getUsername());
    }

    @Override
    public Service getService() {
        return this.service;
    }

    @Override
    public Player getLoggedUser() {
        return this.player;
    }

    @Override
    public void setPlayers(List<String> usernames) throws RemoteException {
        modelPlayers.setAll(usernames);
    }

    @Override
    public void setStartCards(Set<Card> cards) throws RemoteException {
        modelCards.setAll(cards);
    }

    @Override
    public void displayResultRound(Set<Card> cards, String usernameWinner) throws RemoteException {
        String message = usernameWinner + " has won:\n" +
                cards.stream()
                .map(card -> card.getColor() + " " + card.getNumber())
                .reduce("", (x, y) -> x + y + "\n");
        Platform.runLater(() -> MyAlert.showMessage(null, Alert.AlertType.INFORMATION, "End round", message));
    }

    @Override
    public void displayFinalResult(Map<String, Integer> result) throws RemoteException {
        String message = "FINAL RESULTS: " + result.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .map(entry -> entry.getKey() + ": " + entry.getValue() + " cards\n")
                .reduce("", (e1, e2) -> e1 + e2);
        System.out.println(result);
        Platform.runLater(() -> {
            MyAlert.showMessage(null, Alert.AlertType.INFORMATION, "Results", message);
        });
        this.buttonLogout.setDisable(false);
    }

    public void buttonLogoutClicked(ActionEvent actionEvent) {
        openWindow(Window.login);
    }

    public void buttonStartGameClicked(ActionEvent actionEvent) {
        this.buttonStartGame.setDisable(true);
        this.buttonLogout.setDisable(true);
        this.service.startGame(player.getId(), this);
    }

    public void buttonSendCardClicked(ActionEvent actionEvent) {
        Card selectedCard = getSelectedCard();
        if (selectedCard == null) {
            MyAlert.showMessage(null, Alert.AlertType.WARNING, "Warning", "You need to select a card");
            return;
        }
        modelCards.remove(selectedCard);
        service.sendCard(getLoggedUser().getId(), selectedCard);
    }

    private Card getSelectedCard() {
        return tableViewCards.getSelectionModel().getSelectedItem();
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
