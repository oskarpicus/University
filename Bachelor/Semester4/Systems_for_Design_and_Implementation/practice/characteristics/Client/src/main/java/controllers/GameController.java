package controllers;

import domain.Answer;
import domain.Game;
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
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class GameController extends UnicastRemoteObject implements Serializable, Controller, Observer {
    private transient Service service;
    private transient Player player;
    private transient Game game;
    private transient Word currentWord;
    private transient int indexRound;

    private transient final ObservableList<Player> model = FXCollections.observableArrayList();

    @FXML
    transient Button buttonStartGame;
    @FXML
    transient Button buttonLogout;
    @FXML
    transient Button buttonSubmit;
    @FXML
    transient TextField textFieldCharacteristic1;
    @FXML
    transient TextField textFieldCharacteristic2;
    @FXML
    transient Label labelWord;
    @FXML
    transient TableView<Player> tableViewPlayers;
    @FXML
    transient TableColumn<Player, String> tableColumnUsername;

    public GameController() throws RemoteException {
    }

    @Override
    public void close() {
        ((Stage) labelWord.getScene().getWindow()).close();
    }

    @Override
    public void initialise(Service service, Player player) {
        this.service = service;
        this.player = player;
        ((Stage) labelWord.getScene().getWindow()).setTitle(player.getUsername());

        tableColumnUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        tableViewPlayers.setItems(model);

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
    public void startGame(Game game) {
        this.game = game;
        Platform.runLater(this::setInGameState);
    }

    @Override
    public void setPlayers(Collection<Player> players) {
        List<Player> opponents = players.stream()
                .filter(p -> !p.equals(player))
                .collect(Collectors.toList());
        model.setAll(opponents);
    }

    @Override
    public void setWord(Word word) {
        this.currentWord = word;
        Platform.runLater(() -> labelWord.setText(word.getName()));
    }

    @Override
    public void displayResultsRound(Collection<Answer> answers) {
        String message = answers.stream()
                .map(answer -> answer.getPlayer().getUsername() + ": " + answer.getPoints() + "\n" +
                        answer.getCharacteristic1() + " " + answer.getCharacteristic2() + "\n")
                .reduce("", (x, y) -> x + y);
        Platform.runLater(() -> {
            MyAlert.showMessage(null, Alert.AlertType.INFORMATION, "Round finished", message);
            buttonSubmit.setDisable(false);
        });
    }

    @Override
    public void displayRanking(Map<Player, Integer> points) {
        String message = points.entrySet().stream()
                .sorted((e1, e2) -> -e1.getValue().compareTo(e2.getValue()))
                .map(e -> e.getKey().getUsername() + ": " + e.getValue() + "\n")
                .reduce("", (x, y) -> x + y);
        Platform.runLater(() -> {
            MyAlert.showMessage(null, Alert.AlertType.INFORMATION, "Ranking", message);
            setNotInGameState();
        });
    }

    public void buttonStartGameClicked(ActionEvent actionEvent) {
        if ((game = service.startGame(player)) == null) {
            MyAlert.showMessage(null, Alert.AlertType.INFORMATION, "Waiting..", "Waiting for others to join");
        }
        buttonStartGame.setDisable(true);
        buttonLogout.setDisable(true);
    }

    public void buttonLogoutClicked(ActionEvent actionEvent) {
        this.service.removeObserver(player);
        openWindow(Window.login);
    }

    public void buttonSubmitClicked(ActionEvent actionEvent) {
        String ch1 = textFieldCharacteristic1.getText();
        String ch2 = textFieldCharacteristic2.getText();
        if (ch1.equals("") || ch2.equals("")) {
            MyAlert.showMessage(null, Alert.AlertType.WARNING, "Warning", "You need to complete both fields");
            return;
        }
        if (ch1.equals(ch2)) {
            MyAlert.showMessage(null, Alert.AlertType.WARNING, "Warning", "Characteristics can't match");
            return;
        }
        Answer answer = new Answer(-1L, player, game, indexRound, ch1, ch2, currentWord);
        buttonSubmit.setDisable(true);
        service.addAnswer(answer);
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
        textFieldCharacteristic1.setDisable(true);
        textFieldCharacteristic2.setDisable(true);
        buttonSubmit.setDisable(true);
        labelWord.setText("___");
        indexRound = 0;
        model.clear();
        textFieldCharacteristic1.clear();
        textFieldCharacteristic2.clear();
    }

    private void setInGameState() {
        buttonStartGame.setDisable(true);
        buttonLogout.setDisable(true);
        textFieldCharacteristic1.setDisable(false);
        textFieldCharacteristic2.setDisable(false);
        buttonSubmit.setDisable(false);
    }
}
