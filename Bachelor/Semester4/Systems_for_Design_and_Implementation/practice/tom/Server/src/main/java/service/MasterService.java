package service;

import domain.*;
import repository.*;
import services.Observer;
import services.Service;
import utils.Constants;

import java.rmi.RemoteException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MasterService implements Service {
    private final AnswerRepository answerRepository;
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;
    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;
    private final SeaRepository seaRepository;

    private final Map<Player, Observer> observers = new HashMap<>();
    private final Map<String, Integer> totalPoints = new HashMap<>();
    private final List<Answer> answers = new ArrayList<>();
    private Game currentGame;
    private int indexRound = 0;

    public MasterService(AnswerRepository answerRepository, CityRepository cityRepository, CountryRepository countryRepository, GameRepository gameRepository, PlayerRepository playerRepository, SeaRepository seaRepository) {
        this.answerRepository = answerRepository;
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
        this.seaRepository = seaRepository;
    }

    @Override
    public void startGame(Player player) {
        currentGame = new Game();
        if (gameRepository.save(currentGame).isEmpty()) {  // successfully saved
            observers.values().forEach(observer -> {
                try {
                    observer.startGame(currentGame);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            });
            sendLetter(); // the first letter to be sent
            observers.keySet().forEach(player1 -> totalPoints.putIfAbsent(player1.getUsername(), 0));
        }
    }

    @Override
    public void addObserver(Player player, Observer observer) {
        observers.putIfAbsent(player, observer);
        if (observers.size() == Constants.NUMBER_OF_PLAYERS) {
            observers.values().forEach(o -> {
                try {
                    o.canStartGame();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @Override
    public Player login(String username, String password) {
        return playerRepository.findByUsernamePassword(username, password).orElse(null);
    }

    @Override
    public void sendAnswer(Answer answer) {
        answers.add(answer);
        if (answers.size() == Constants.NUMBER_OF_PLAYERS) {
            computePoints();
            indexRound++;
            if (indexRound == Constants.NUMBER_OF_ROUNDS) {  // end game
                sendRanking();
                clear();
            }
            else {
                sendLetter();
            }
            answers.clear();
        }
    }

    @Override
    public void removeObserver(Player player) {
        observers.remove(player);
    }

    private void sendLetter() {
        Random random = new Random();
        String letter = Constants.LETTERS.get(random.nextInt(Constants.LETTERS.size()));
        observers.values().forEach(observer -> {
            try {
                observer.setLetter(letter);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

    private void computePoints() {
        String letter = answers.get(0).getLetterChosen();
        List<City> cities = StreamSupport.stream(cityRepository.findAll().spliterator(), false).collect(Collectors.toList());
        List<Sea> seas = StreamSupport.stream(seaRepository.findAll().spliterator(), false).collect(Collectors.toList());
        List<Country> countries = StreamSupport.stream(countryRepository.findAll().spliterator(), false).collect(Collectors.toList());
        // set the amount of points
        List<String> responsesCities = answers.stream().map(Answer::getCity).collect(Collectors.toList());
        List<String> responsesSeas = answers.stream().map(Answer::getSea).collect(Collectors.toList());
        List<String> responsesCountries = answers.stream().map(Answer::getCountry).collect(Collectors.toList());
        answers.forEach(answer -> {
            if (startsWith(answer.getCity(), letter) && isValidCity(cities, answer.getCity())) {
                answer.addPoints(3);
                if (isUnique(responsesCities, answer.getCity())) {
                    answer.addPoints(7);
                }
            }
            if (startsWith(answer.getSea(), letter) && isValidSea(seas, answer.getSea())) {
                answer.addPoints(3);
                if (isUnique(responsesSeas, answer.getSea())) {
                    answer.addPoints(7);
                }
            }
            if (startsWith(answer.getCountry(), letter) && isValidCountry(countries, answer.getCountry())) {
                answer.addPoints(3);
                if (isUnique(responsesCountries, answer.getCountry())) {
                    answer.addPoints(7);
                }
            }
        });
        // save answers in DB
        for (Answer answer : answers) {
            answerRepository.save(answer);
            int before = totalPoints.get(answer.getPlayer().getUsername());
            totalPoints.replace(answer.getPlayer().getUsername(), before + answer.getPoints());
        }
        // send results in this round
        Map<String, Integer> points = new HashMap<>();
        answers.forEach(answer -> points.putIfAbsent(answer.getPlayer().getUsername(), answer.getPoints()));
        observers.values().forEach(observer -> {
            try {
                observer.displayResultsRound(points);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

    private void sendRanking() {
        observers.values().forEach(observer -> {
            try {
                observer.displayRanking(totalPoints);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

    private boolean startsWith(String word, String letter) {
        return word.toCharArray()[0] == letter.toCharArray()[0];
    }

    private boolean isValidCity(List<City> cities, String city) {
        return cities.stream().anyMatch(city1 -> city1.getName().equals(city));
    }

    private boolean isValidSea(List<Sea> cities, String city) {
        return cities.stream().anyMatch(city1 -> city1.getName().equals(city));
    }

    private boolean isValidCountry(List<Country> cities, String city) {
        return cities.stream().anyMatch(city1 -> city1.getName().equals(city));
    }

    private boolean isUnique(List<String> strings, String s) {
        return strings.stream().filter(s1 -> s1.equals(s)).count() == 1;
    }

    private void clear() {
        currentGame = null;
        totalPoints.clear();
        observers.clear();
        answers.clear();
        indexRound = 0;
    }
}