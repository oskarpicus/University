package service;

import domain.Game;
import domain.Guess;
import domain.Player;
import domain.Word;
import repository.GameRepository;
import repository.GuessRepository;
import repository.PlayerRepository;
import repository.WordRepository;
import services.Observer;
import services.Service;
import utils.Constants;
import validator.ValidationException;

import java.rmi.RemoteException;
import java.util.*;

public class MasterService implements Service {
    private final GameRepository gameRepository;
    private final GuessRepository guessRepository;
    private final PlayerRepository playerRepository;
    private final WordRepository wordRepository;

    private final Map<Player, Observer> observers = new HashMap<>();
    private final List<Word> wordsActivePlayers = new ArrayList<>();
    private final Map<Player, String> playerWord = new HashMap<>();
    private final List<Guess> currentGuesses = new ArrayList<>();
    private final Map<Player, Integer> currentPoints = new HashMap<>();
    private int indexRound = 1;

    public MasterService(GameRepository gameRepository, GuessRepository guessRepository, PlayerRepository playerRepository, WordRepository wordRepository) {
        this.gameRepository = gameRepository;
        this.guessRepository = guessRepository;
        this.playerRepository = playerRepository;
        this.wordRepository = wordRepository;
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
    public String startGame(Word word) {
        try {
            wordRepository.save(word);
        } catch (ValidationException e) {
            return e.getMessage();
        }
        wordsActivePlayers.add(word);
        if (wordsActivePlayers.size() == Constants.NR_PLAYERS) {  // can start game
            Game game = new Game();
            gameRepository.save(game);
            updateWordsWithGame(game);
            initialiseMaps();
            sendGameToPlayers(game);
            sendPlayerWordToPlayers();
        }
        return "";
    }

    @Override
    public void addGuess(Guess guess) {
        guess.setIndexRound(indexRound);
        currentGuesses.add(guess);
        if (currentGuesses.size() == Constants.NR_PLAYERS) {  // everybody sent their answer
            Map<Player, Integer> points = computePoints();
            saveCurrentGuesses();
            sendPointsToPlayers(points);
            sendPlayerWordToPlayers();
            if (guess.getIndexRound().equals(Constants.NR_ROUND)) {  // end game
                sendRankingToPlayers();
                clearGame();
            }
            else {
                indexRound++;
                currentGuesses.clear();
            }
        }
    }

    private void updateWordsWithGame(Game game) {
        wordsActivePlayers.forEach(word -> word.setGame(game));
        wordsActivePlayers.forEach(wordRepository::update);
    }

    private void initialiseMaps() {
        wordsActivePlayers.forEach(word -> playerWord.putIfAbsent(word.getPlayer(), "_".repeat(word.getWord().length())));
        wordsActivePlayers.forEach(word -> currentPoints.putIfAbsent(word.getPlayer(), 0));
    }

    private void sendPlayerWordToPlayers() {
        wordsActivePlayers.stream()
                .map(word -> observers.get(word.getPlayer()))
                .forEach(observer -> {
                    try {
                        observer.displayWordLetters(playerWord);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                });
    }

    private void sendGameToPlayers(Game game) {
        wordsActivePlayers.stream()
                .map(word -> observers.get(word.getPlayer()))
                .forEach(observer -> {
                    try {
                        observer.canStartGame(game);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                });
    }

    private void sendPointsToPlayers(Map<Player, Integer> points) {
        wordsActivePlayers.stream()
                .map(word -> observers.get(word.getPlayer()))
                .forEach(observer -> {
                    try {
                        observer.displayPoints(points);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                });
    }

    private void sendRankingToPlayers() {
        wordsActivePlayers.stream()
                .map(word -> observers.get(word.getPlayer()))
                .forEach(observer -> {
                    try {
                        observer.displayRanking(currentPoints);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                });
    }

    private Map<Player, Integer> computePoints() {
        Map<Player, Integer> points = new HashMap<>();
        currentGuesses.forEach(guess -> {
            Optional<Word> wordTarget = wordsActivePlayers.stream()
                    .filter(word -> word.getPlayer().equals(guess.getTargetPlayer()))
                    .findFirst();
            if (wordTarget.isPresent()) {
                int nrOccurrences = getNumberOfOccurrences(wordTarget.get(), guess.getLetter());
                points.putIfAbsent(guess.getPlayer(), nrOccurrences);
                guess.setPoints(nrOccurrences);
                updatePlayerWord(guess.getTargetPlayer(), wordTarget.get(), guess.getLetter());

                int before = currentPoints.get(guess.getPlayer());
                currentPoints.replace(guess.getPlayer(), before + nrOccurrences);
            }
        });
        return points;
    }

    private int getNumberOfOccurrences(Word word, String letter) {
        int result = 0;
        char targetChar = letter.charAt(0);
        for (int i = 0; i < word.getWord().length(); i++) {
            if (word.getWord().charAt(i) == targetChar) {
                result++;
            }
        }
        return result;
    }

    private void updatePlayerWord(Player ownerOfWord, Word targetWord, String letter) {
        String before = playerWord.get(ownerOfWord);
        char targetLetter = letter.charAt(0);
        char[] beforeArray = before.toCharArray();
        char[] targetWordArray = targetWord.getWord().toCharArray();
        for (int i = 0; i < beforeArray.length; i++) {
            if (targetWordArray[i] == targetLetter) {
                beforeArray[i] = targetLetter;
            }
        }
        playerWord.replace(ownerOfWord, new String(beforeArray));
    }

    private void saveCurrentGuesses() {
        currentGuesses.forEach(guessRepository::save);
    }

    private void clearGame() {
        wordsActivePlayers.clear();
        indexRound = 1;
        playerWord.clear();
        currentGuesses.clear();
        currentPoints.clear();
    }
}
