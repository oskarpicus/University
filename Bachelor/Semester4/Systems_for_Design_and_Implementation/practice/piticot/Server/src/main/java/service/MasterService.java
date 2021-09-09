package service;

import domain.Game;
import domain.Player;
import domain.Position;
import domain.Round;
import repository.GameRepository;
import repository.PlayerRepository;
import repository.PositionRepository;
import repository.RoundRepository;
import services.Observer;
import services.Service;
import utils.Constants;

import java.rmi.RemoteException;
import java.util.*;

public class MasterService implements Service {
    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;
    private final PositionRepository positionRepository;
    private final RoundRepository roundRepository;

    private final Map<Player, Observer> observers = new HashMap<>();
    private final List<Player> readyPlayers = new ArrayList<>();

    public MasterService(GameRepository gameRepository, PlayerRepository playerRepository, PositionRepository positionRepository, RoundRepository repository) {
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
        this.positionRepository = positionRepository;
        this.roundRepository = repository;
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
        if (readyPlayers.size() == Constants.NR_PLAYERS) {  // can start game
            Game game = new Game();
            gameRepository.save(game);
            Set<Position> positions = generateAndSavePositions(game);
            game.setPositions(positions);
            sendGameToPlayers(game);
            sendOpponentToPlayers();
            readyPlayers.clear();
            return game;
        }
        return null;
    }

    @Override
    public void addRound(Round round) {
        roundRepository.save(round);
        int newPositionIndex = round.getOldPosition().getIndexPosition() + round.getN();
        round.getOldPosition().setPlayer(null);
        Position position = positionRepository.getPositionByIndex(round.getGame(), newPositionIndex);
        round.setNewPosition(position);
        if (position.getPlayer() != null) {  // position occupied
            
        }
    }

    private void sendOpponentToPlayers() {
        Player p1 = readyPlayers.get(0), p2 = readyPlayers.get(1);
        try {
            observers.get(p1).setOpponent(p2);
            observers.get(p2).setOpponent(p1);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private Set<Position> generateAndSavePositions(Game game) {
        Random random = new Random();
        Set<Position> positions = new HashSet<>();
        int indexOfX = random.nextInt(Constants.NR_POSITIONS + 1);
        for (int i = 1; i <= Constants.NR_POSITIONS; i++) {
            Position position = new Position(-1L, i, i == indexOfX, game);
            positionRepository.save(position);
            positions.add(position);
        }
        return positions;
    }

    private void sendGameToPlayers(Game game) {
        readyPlayers.stream()
                .map(observers::get)
                .forEach(observer -> {
                    try {
                        observer.startGame(game);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                });
    }
}
