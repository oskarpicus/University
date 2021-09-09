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
import java.util.stream.Collectors;

public class MasterService implements Service {
    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;
    private final PositionRepository positionRepository;
    private final RoundRepository roundRepository;

    private final Map<Player, Observer> observers = new HashMap<>();
    private final Set<Player> readyPlayers = new HashSet<>();
    private List<Player> activePlayers;

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
        if (readyPlayers.size() == Constants.NR_OF_PLAYERS) {
            activePlayers = new ArrayList<>(readyPlayers);
            Set<Position> positions = generatePositions();
            Game game = new Game(-1L, positions);
            gameRepository.save(game);
            sendIdsToPlayers();
            sendPositionsToPlayers(positions);
            sendGameToPlayers(game);
            sendCanGenerateNumber(activePlayers.get(0));
            readyPlayers.clear();
            return game;
        }
        return null;
    }

    @Override
    public Position addRound(Round round) {
        int nextPosition = round.getOldPosition() == null ? -1 : round.getOldPosition().getIndex();
        int n = round.getN();
        while (n != 0) {
            nextPosition++;
            if (nextPosition == Constants.NR_OF_POSITIONS) {
                round.setReceived(round.getReceived() + Constants.RON_CYCLE);
                nextPosition = 0;
            }
            n--;
        }
        Position newPosition = positionRepository.getPositionByIndex(round.getGame(), nextPosition);
        if (newPosition.getOwner() == null) {  // there is no owner
            round.setPaid(newPosition.getValue());
            newPosition.setOwner(round.getPlayer());
        }
        else {
            round.setPaid(newPosition.getValue() / 2);
            Round opponentRound = roundRepository.getLatestRoundByPlayer(round.getGame(), newPosition.getOwner());
            opponentRound.setReceived(opponentRound.getReceived() + newPosition.getValue() / 2);
            roundRepository.update(opponentRound);
        }
        round.setNewPosition(newPosition);
        positionRepository.update(newPosition);
        roundRepository.save(round);
        sendNextAvailableTurn(round);
        sendRoundToPlayers(round);
        return newPosition;
    }


    private Set<Position> generatePositions() {
        Random random = new Random();
        Set<Position> positions = new HashSet<>();
        for (int i = 0; i < Constants.NR_OF_POSITIONS; i++) {
            int value = random.nextInt(Constants.MAXIMUM_VALUE);
            Position p = new Position(-1L, i, value);
            positionRepository.save(p);
            positions.add(p);
        }
        return positions;
    }

    private void sendIdsToPlayers() {
        activePlayers.stream()
                .map(observers::get)
                .forEach(observer -> {
                    try {
                        observer.displayOpponents(readyPlayers);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                });
    }

    private void sendPositionsToPlayers(Set<Position> positions) {
        activePlayers.stream()
                .map(observers::get)
                .forEach(observer -> {
                    try {
                        observer.displayPositions(positions);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                });
    }

    private void sendGameToPlayers(Game game) {
        activePlayers.stream()
                .map(observers::get)
                .forEach(observer -> {
                    try {
                        observer.startGame(game);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                });
    }

    private void sendRoundToPlayers(Round round) {
        activePlayers.stream()
                .map(observers::get)
                .forEach(observer -> {
                    try {
                        observer.displayRound(round);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                });
    }

    private void sendCanGenerateNumber(Player player) {
        try {
            observers.get(player).allowToGenerateNumber();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void sendNextAvailableTurn(Round round) {
        int index = activePlayers.indexOf(round.getPlayer());
        if (index == Constants.NR_OF_PLAYERS - 1) {  // we move to the next turn
            index = 0;
        }
        else {
            index++;
        }
        try {
            observers.get(activePlayers.get(index)).allowToGenerateNumber();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        if (round.getIndexRound() == Constants.NR_OF_ROUNDS - 1) {
            endGame(round.getGame());
        }
    }

    private void endGame(Game game) {
        List<Long> rankingIds = gameRepository.getRanking(game);
        List<Player> players = rankingIds
                .stream()
                .map(id -> playerRepository.find(id).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        sendRankingToPlayers(players);
        activePlayers.clear();
    }

    private void sendRankingToPlayers(List<Player> players) {
        activePlayers.stream()
                .map(observers::get)
                .forEach(observer -> {
                    try {
                        observer.displayRanking(players);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                });
    }
}
