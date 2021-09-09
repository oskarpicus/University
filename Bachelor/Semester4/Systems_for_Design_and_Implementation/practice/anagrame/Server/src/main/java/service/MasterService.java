package service;

import domain.Game;
import domain.Player;
import domain.Round;
import domain.Word;
import repository.GameRepository;
import repository.PlayerRepository;
import repository.RoundRepository;
import repository.WordRepository;
import services.Observer;
import services.Service;
import utils.Constants;

import java.rmi.RemoteException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MasterService implements Service {
    private final GameRepository gameRepository;
    private final WordRepository wordRepository;
    private final PlayerRepository playerRepository;
    private final RoundRepository roundRepository;

    private final Map<Player, Observer> loggedPlayers = new HashMap<>();
    private final Map<Player, Observer> activePlayers = new HashMap<>();
    private final List<Round> currentResponses = new ArrayList<>();
    private Word currentWord;
    private int indexRound = 0;

    public MasterService(GameRepository gameRepository, WordRepository wordRepository, PlayerRepository playerRepository, RoundRepository repository) {
        this.gameRepository = gameRepository;
        this.wordRepository = wordRepository;
        this.playerRepository = playerRepository;
        this.roundRepository = repository;
    }

    @Override
    public Player login(String username, String password) {
        return playerRepository.findByUsernamePassword(username, password).orElse(null);
    }

    @Override
    public void addObserver(Player player, Observer observer) {
        loggedPlayers.putIfAbsent(player, observer);
    }

    @Override
    public void removeObserver(Player player) {
        loggedPlayers.remove(player);
    }

    @Override
    public Game startGame() {
        if (!activePlayers.isEmpty()) {  // there is an already active game
            return null;
        }
        Game game = new Game();
        gameRepository.save(game);
        List<Player> players = new ArrayList<>(loggedPlayers.keySet());
        loggedPlayers.forEach((player, observer) -> {
            activePlayers.putIfAbsent(player, observer);
            try {
                observer.prepareStartGame(game);
                observer.setOnlinePlayers(players);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
        sendAnagram();
        return game;
    }

    @Override
    public void sendAnswer(Round round) {
        round.setCorrectWord(currentWord);
        round.setIndex(indexRound);
        currentResponses.add(round);
        if (currentResponses.size() == activePlayers.size()) {  // everybody sent their answer
            var points = computePoints();
            saveRounds();
            sendRoundResults(points);
            currentResponses.clear();
            indexRound++;
            if (indexRound == Constants.NUMBER_OF_ROUNDS) { // end game
                finishGame();
            }
            else {
                sendAnagram();
            }
        }
    }

    private void saveRounds() {
        currentResponses.forEach(roundRepository::save);
    }

    private void finishGame() {
        // clear everything and let observers know game is over
        activePlayers.values().forEach(observer -> {
            try {
                observer.endGame();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
        activePlayers.clear();
        indexRound = 0;
        currentWord = null;
        currentResponses.clear();
    }

    private void sendRoundResults(Map<Player, Integer> points) {
        activePlayers.values().forEach(observer -> {
            try {
                observer.displayResultsRound(points, currentWord);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

    private void sendAnagram() {
        List<Word> words = StreamSupport.stream(wordRepository.findAll().spliterator(), false).collect(Collectors.toList());
        currentWord = words.get(new Random().nextInt(words.size()));
        String anagram = getAnagram(currentWord);
        activePlayers.values().forEach(observer -> {
            try {
                observer.setAnagram(anagram);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

    private String getAnagram(Word word) {
        List<String> characters = Arrays.asList(word.getWord().split(""));
        Collections.shuffle(characters);
        StringBuilder result = new StringBuilder();
        for (String string : characters) {
            result.append(string);
        }
        return result.toString();
    }

    private Map<Player, Integer> computePoints() {
        Map<Player, Integer> points = new HashMap<>();
        activePlayers.keySet().forEach(player -> {
            Optional<Round> round = currentResponses.stream()
                    .filter(round1 -> round1.getPlayer().equals(player))
                    .findFirst();
            if (round.isPresent()) {
                round.get().setPoints(getPoints(currentWord, round.get().getAnswer()));
                points.putIfAbsent(player, round.get().getPoints());
            }
        });
        return points;
    }

    private int getPoints(Word correct, String answer) {
        var c1 = correct.getWord().toCharArray();
        var c2 = answer.toCharArray();
        int total = 0;
        for (int i = 0; i < c1.length && i < c2.length; i++) {
            if (c1[i] == c2[i]) {
                total++;
            }
        }
        return total;
    }
}