package service;

import domain.Game;
import domain.Guess;
import domain.Plane;
import domain.Player;
import repository.GameRepository;
import repository.GuessRepository;
import repository.PlaneRepository;
import repository.PlayerRepository;
import services.Observer;
import services.Service;

import java.rmi.RemoteException;
import java.util.*;

public class MasterService implements Service {
    private final GameRepository gameRepository;
    private final GuessRepository guessRepository;
    private final PlaneRepository planeRepository;
    private final PlayerRepository playerRepository;

    private final Map<Player, Observer> observers = new HashMap<>();
    private final Map<Player, Plane> standBy = new HashMap<>();

    public MasterService(GameRepository gameRepository, GuessRepository guessRepository, PlaneRepository planeRepository, PlayerRepository playerRepository) {
        this.gameRepository = gameRepository;
        this.guessRepository = guessRepository;
        this.planeRepository = planeRepository;
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
    public Game startGame(Plane plane) {
        planeRepository.save(plane);
        if (standBy.isEmpty()) {  // there is nobody to play
            standBy.putIfAbsent(plane.getPlayer(), plane);
            return null;
        }
        Player opponent = getRandomOpponent(plane.getPlayer());
        Game game = new Game(-1L, new HashSet<>(Arrays.asList(plane, standBy.get(opponent))));
        gameRepository.save(game);
        Arrays.asList(observers.get(plane.getPlayer()), observers.get(opponent))
                .forEach(observer -> {
                    try {
                        observer.startGame(game);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                });
        standBy.remove(opponent);
        standBy.remove(plane.getPlayer());
        return game;
    }

    @Override
    public void addGuess(Guess guess) {
        guessRepository.save(guess);
        Optional<Game> game = gameRepository.find(guess.getGame().getId());
        if (game.isEmpty())
            return;
        Optional<Plane> opponentPlane = game.get().getPlanes().stream()
                .filter(plane -> !plane.getPlayer().equals(guess.getPlayer()))
                .findFirst();
        if (opponentPlane.isEmpty())
            return;
        try { // send the guess to opponent to display it
            observers.get(opponentPlane.get().getPlayer()).displayGuessOpponent(guess);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        if (guess.getLine().equals(opponentPlane.get().getLine()) &&
                guess.getColumn().equals(opponentPlane.get().getColumn())) {  // guessed correctly
            Arrays.asList(
                    observers.get(guess.getPlayer()),
                    observers.get(opponentPlane.get().getPlayer())
            ).forEach(observer -> {
                try {
                    observer.displayWinner(guess.getPlayer());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            });
            game.get().setWinner(guess.getPlayer());
            gameRepository.update(game.get());
        }
    }

    private Player getRandomOpponent(Player other) {
        Random random = new Random();
        Player opponent;
        do {
            List<Player> players = new ArrayList<>(standBy.keySet());
            opponent = players.get(random.nextInt(players.size()));
        } while (other.equals(opponent));
        return opponent;
    }
}
