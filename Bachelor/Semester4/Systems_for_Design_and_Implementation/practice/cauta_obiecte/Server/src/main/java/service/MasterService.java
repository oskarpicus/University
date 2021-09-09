package service;

import domain.Game;
import domain.Guess;
import domain.Item;
import domain.Player;
import repository.GameRepository;
import repository.GuessRepository;
import repository.ItemRepository;
import repository.PlayerRepository;
import services.Observer;
import services.Service;
import utils.Constants;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MasterService implements Service {
    private final GameRepository gameRepository;
    private final GuessRepository guessRepository;
    private final ItemRepository itemRepository;
    private final PlayerRepository playerRepository;

    private final Map<Player, Observer> observers = new HashMap<>();
    private final List<Player> currentPlayers = new ArrayList<>();
    private final Map<Player, String> positions = new HashMap<>();
    private final List<Guess> currentGuesses = new ArrayList<>();
    private Game currentGame = null;

    public MasterService(GameRepository gameRepository, GuessRepository guessRepository, ItemRepository itemRepository, PlayerRepository playerRepository) {
        this.gameRepository = gameRepository;
        this.guessRepository = guessRepository;
        this.itemRepository = itemRepository;
        this.playerRepository = playerRepository;
    }

    @Override
    public Player login(String username, String password) {
        return playerRepository.findByUsernamePassword(username, password).orElse(null);
    }

    @Override
    public void addObserver(Player player, Observer observer) {
        observers.putIfAbsent(player, observer);
    }

    @Override
    public void removeObserver(Player player) {
        observers.remove(player);
    }

    @Override
    public Game startGame(List<Item> items) {
        currentPlayers.add(items.get(0).getPlayer());
        if (currentPlayers.size() == 1) {  // the first one to push the button
            currentGame = new Game();
            gameRepository.save(currentGame);
        }
        // saving the items in DB
        items.forEach(item -> item.setGame(currentGame));
        items.forEach(itemRepository::save);
        if (currentPlayers.size() == Constants.NR_PLAYERS) { // can start game
            prepareToStartGame();
            sendGameToPlayers();
            sendPositionsToPlayers();
            return currentGame;
        }
        return null;
    }

    @Override
    public void addGuess(Guess guess) {
        guessRepository.save(guess);
        currentGuesses.add(guess);
        if (currentGuesses.size() == Constants.NR_PLAYERS) {  // everybody sent their answer
            computePoints();
            updateGuesses();
            sendPositionsToPlayers();
            if (guess.getIndexRound().equals(Constants.NR_ROUNDS)) {
                System.out.println("end game");
                Map<Player, Integer> ranking = getRanking();
                sendRankingToPlayers(ranking);
                clearGame();
            }
            currentGuesses.clear();
        }
    }

    private void sendGameToPlayers() {
        currentPlayers.stream()
                .map(observers::get)
                .forEach(observer -> {
                    try {
                        observer.startGame(currentGame);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                });
    }

    private void prepareToStartGame() {
        currentPlayers.forEach(player -> this.positions.putIfAbsent(player, "_".repeat(Constants.MAXIMUM_POSITION)));
    }

    private void sendPositionsToPlayers() {
        currentPlayers.stream()
                .map(observers::get)
                .forEach(observer -> {
                    try {
                        observer.displayPositions(positions);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                });
    }

    private void sendRankingToPlayers(Map<Player, Integer> ranking) {
        currentPlayers.stream()
                .map(observers::get)
                .forEach(observer -> {
                    try {
                        observer.displayRanking(ranking);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                });
    }

    private void computePoints() {
        for(var guess : currentGuesses) {
            char character;
            List<Item> itemsOpponent = itemRepository.getItemsByGameAndPlayer(guess.getGame(), guess.getTargetPlayer());
            if (correctlyGuessed(itemsOpponent, guess)) {
                guess.setPoints(Constants.POINTS_CORRECT);
                character = 'O';
            }
            else if (almostGuessed(itemsOpponent, guess)) {
                guess.setPoints(Constants.POINTS_ALMOST_CORRECT);
                character = 'N';
            }
            else {
                character = 'C';
            }
            setCharacter(guess.getTargetPlayer(), guess.getPosition(), character);
        }
    }

    private void updateGuesses() {
        currentGuesses.forEach(guessRepository::update);
    }

    private void setCharacter(Player player, int index, char c) {
        String before = positions.get(player);
        char[] array = before.toCharArray();
        array[index - 1] = c;
        positions.replace(player, new String(array));
    }

    private boolean correctlyGuessed(List<Item> items, Guess guess) {
        return items.stream()
                .anyMatch(item -> item.getPosition().equals(guess.getPosition()));
    }

    private boolean almostGuessed(List<Item> items, Guess guess) {
        return items.stream()
                .anyMatch(item -> guess.getPosition().equals(item.getPosition() - 1) ||
                        guess.getPosition().equals(item.getPosition() + 1));
    }

    private void clearGame() {
        currentPlayers.clear();
        positions.clear();
        currentGuesses.clear();
        currentGame = null;
    }

    private Map<Player, Integer> getRanking() {
        Map<Player, Integer> points = new HashMap<>();
        currentPlayers.forEach(player -> {
            int p = gameRepository.getPointsByPlayer(currentGame, player);
            points.putIfAbsent(player, p);
        });
        return points;
    }
}
