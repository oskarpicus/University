package service;

import domain.Answer;
import domain.Game;
import domain.Player;
import domain.Word;
import repository.AnswerRepository;
import repository.GameRepository;
import repository.PlayerRepository;
import repository.WordRepository;
import services.Observer;
import services.Service;
import utils.Constants;

import java.rmi.RemoteException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MasterService implements Service {
    private final WordRepository wordRepository;
    private final AnswerRepository answerRepository;
    private final PlayerRepository playerRepository;
    private final GameRepository gameRepository;

    private final Map<Player, Observer> observers = new HashMap<>();
    private final Set<Player> readyPlayers = new HashSet<>();

    public MasterService(WordRepository wordRepository, AnswerRepository answerRepository, PlayerRepository playerRepository, GameRepository gameRepository) {
        this.wordRepository = wordRepository;
        this.answerRepository = answerRepository;
        this.playerRepository = playerRepository;
        this.gameRepository = gameRepository;
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
    public Game startGame(Player player) {
        readyPlayers.add(player);
        if (readyPlayers.size() == Constants.NR_OF_PLAYERS) {  // can start game
            sendOpponentsToPlayers();
            sendWordToPlayers(readyPlayers, chooseWord());
            Game game = new Game();
            gameRepository.save(game);
            startGameToPlayers(readyPlayers, game);
            readyPlayers.clear();
            return game;
        }
        return null;
    }

    @Override
    public void addAnswer(Answer answer) {
        answerRepository.save(answer);
        List<Answer> answers = answerRepository.getAnswersByGameAndRound(answer.getGame(), answer.getIndexRound());
        if (answers.size() == Constants.NR_OF_PLAYERS) {  // everybody sent their answer
            computePoints(answers);
            answers.forEach(answerRepository::update);
            sendAnswersToPlayers(answers);
            if (answer.getIndexRound() == Constants.NR_ROUNDS - 1) {  // end game
                var points = endGame(answers);
                sendRankingToPlayers(answers, points);
            }
            else {
                List<Player> players = answers.stream().map(Answer::getPlayer).collect(Collectors.toList());
                sendWordToPlayers(players, chooseWord());
            }
        }
    }

    private void sendOpponentsToPlayers() {
        readyPlayers.stream()
                .map(observers::get)
                .forEach(observer -> {
                    try {
                        observer.setPlayers(readyPlayers);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                });
    }

    private Word chooseWord() {
        Random random = new Random();
        List<Word> words = StreamSupport
                .stream(wordRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        int index = random.nextInt(words.size());
        return words.get(index);
    }

    private void sendWordToPlayers(Collection<Player> players, Word word) {
        players.stream()
                .map(observers::get)
                .forEach(observer -> {
                    try {
                        observer.setWord(word);
                    } catch (RemoteException e ) {
                        e.printStackTrace();
                    }
                });
    }

    private void startGameToPlayers(Collection<Player> players, Game game) {
        players.stream()
                .map(observers::get)
                .forEach(observer -> {
                    try {
                        observer.startGame(game);
                    } catch (RemoteException e ) {
                        e.printStackTrace();
                    }
                });
    }

    private void sendAnswersToPlayers(Collection<Answer> answers) {
        answers.forEach(answer -> {
            Observer observer = observers.get(answer.getPlayer());
            try {
                observer.displayResultsRound(answers);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

    private void sendRankingToPlayers(Collection<Answer> answers, Map<Player, Integer> points) {
        answers.forEach(answer -> {
            Observer observer = observers.get(answer.getPlayer());
            try {
                observer.displayRanking(points);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

    private void computePoints(List<Answer> answers) {
        answers.forEach(answer -> {
            // the first one
            if (isCharacteristicCorrect(answer.getWord(), answer.getCharacteristic1())) {
                int points = 0;
                switch (getCounts(answers, answer.getCharacteristic1())) {
                    case 1 -> points = 5;
                    case 2 -> points = 3;
                    case 3 -> points = 1;
                }
                answer.setPoints(answer.getPoints() + points);
            }
            if (isCharacteristicCorrect(answer.getWord(), answer.getCharacteristic2())) {
                int points = 0;
                switch (getCounts(answers, answer.getCharacteristic2())) {
                    case 1 -> points = 5;
                    case 2 -> points = 3;
                    case 3 -> points = 1;
                }
                answer.setPoints(answer.getPoints() + points);
            }
        });
    }

    private int getCounts(List<Answer> answers, String characteristic) {
        return (int)answers.stream()
                .filter(answer -> answer.getCharacteristic1().equals(characteristic) ||
                        answer.getCharacteristic2().equals(characteristic))
                .count();
    }

    private boolean isCharacteristicCorrect(Word word, String characteristic) {
        return word.getCharacteristics().stream()
                .anyMatch(c -> c.getName().equals(characteristic));
    }

    private Map<Player, Integer> endGame(List<Answer> lastAnswers) {
        Map<Player, Integer> points = new HashMap<>();
        Game game = lastAnswers.get(0).getGame();
        lastAnswers.stream()
                .map(Answer::getPlayer)
                .forEach(player -> points.putIfAbsent(player, gameRepository.getPointsByPlayer(game, player)));
        return points;
    }
}
