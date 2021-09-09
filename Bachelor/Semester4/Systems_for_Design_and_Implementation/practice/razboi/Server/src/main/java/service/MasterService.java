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
    private final CardRepository cardRepository;
    private final GameRepository gameRepository;
    private final InitialisationRepository initialisationRepository;
    private final PlayerRepository playerRepository;
    private final RoundRepository roundRepository;

    private final Map<Player, Observer> loggedPlayers = new HashMap<>();
    private final Map<Player, Observer> activePlayers = new HashMap<>();
    private final List<Round> currentRounds = new ArrayList<>();
    private int indexRound = 0;

    public MasterService(CardRepository cardRepository, GameRepository gameRepository, InitialisationRepository initialisationRepository, PlayerRepository playerRepository, RoundRepository roundRepository) {
        this.cardRepository = cardRepository;
        this.gameRepository = gameRepository;
        this.initialisationRepository = initialisationRepository;
        this.playerRepository = playerRepository;
        this.roundRepository = roundRepository;
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
        if (!activePlayers.isEmpty()) {  // there is an active game
            return null;
        }
        if (loggedPlayers.size() >= Constants.MINIMUM_NR_PLAYERS) {  // can start game
            Game game = new Game();
            gameRepository.save(game);
            loggedPlayers.forEach(activePlayers::putIfAbsent);
            sendCurrentGameToPlayers(game);
            sendOnlinePlayersToPlayers();
            sendCardsToPlayers(game);
            return game;
        }
        return null;
    }

    @Override
    public void sendCard(Round round) {
        round.setIndexRound(indexRound);
        currentRounds.add(round);
        roundRepository.save(round);
        Optional<Player> player = playerRepository.find(round.getPlayer().getId());
        if (player.isPresent()) { // update the player with the sent round
            player.get().getRounds().add(round);
            playerRepository.update(player.get());
        }

        if (currentRounds.size() == activePlayers.size()) {  // every player sent their card
            Player roundWinner = determineRoundWinner();
            sentSelectedCards(roundWinner == null ? "" : roundWinner.getUsername());
            endRound();
            indexRound++;

            if (indexRound == Constants.NR_CARDS_PER_PLAYER) {
                Optional<Game> game = gameRepository.find(round.getGame().getId());
                if (game.isPresent()) {
                    Long winnerId = gameRepository.findWinner(game.get());
                    sendWinnerToPlayers(playerRepository.find(winnerId).orElse(null));
                }
                endGame();
            }
        }
    }

    private void sendOnlinePlayersToPlayers() {
        List<Player> players = new ArrayList<>(activePlayers.keySet());
        activePlayers.values().forEach(observer -> {
            try {
                observer.setOnlinePlayers(players);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

    private void sendCurrentGameToPlayers(Game game) {
        activePlayers.values().forEach(observer -> {
            try {
                observer.startGame(game);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

    private void sendCardsToPlayers(Game game) {
        Random random = new Random();
        List<Card> allCards = StreamSupport.stream(cardRepository.findAll().spliterator(), false).collect(Collectors.toList());
        List<Initialisation> initialisations = new ArrayList<>();
        activePlayers.forEach((player, observer) -> {
            Set<Card> chosenCards = new HashSet<>();
            while (chosenCards.size() != Constants.NR_CARDS_PER_PLAYER) {
                chosenCards.add(allCards.get(random.nextInt(allCards.size())));
            }
            initialisations.add(new Initialisation(-1L, player, game, chosenCards));
            try {
                observer.setCards(chosenCards);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
        initialisations.forEach(initialisationRepository::save);
    }

    private void endRound() {
        currentRounds.clear();
    }

    private void endGame() {
        indexRound = 0;
        activePlayers.clear();
        currentRounds.clear();
    }

    private Player determineRoundWinner() {
        List<Card> sentCards = currentRounds.stream()
                .map(Round::getCardSent)
                .collect(Collectors.toList());
        Set<Card> sentCardsSet = new HashSet<>(sentCards);
        if (sentCards.size() != sentCardsSet.size()) {  // there are duplicates
            return null;
        }
        Optional<Round> roundWinner = currentRounds.stream()
                .min((r1, r2) -> -r1.getCardSent().compareTo(r2.getCardSent()));
        if (roundWinner.isPresent()) {
            roundWinner.get().setCardsWon(sentCardsSet);
            roundRepository.update(roundWinner.get());
            return roundWinner.get().getPlayer();
        }
        return null;
    }

    private void sentSelectedCards(String roundWinnerUsername) {
        Map<Player, Card> sentCards = new HashMap<>();
        currentRounds.forEach(round -> sentCards.putIfAbsent(round.getPlayer(), round.getCardSent()));
        activePlayers.values().forEach(observer -> {
            try {
                observer.displayCardsChosen(sentCards, roundWinnerUsername);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

    private void sendWinnerToPlayers(Player player) {
        activePlayers.values().forEach(observer -> {
            try {
                observer.displayWinner(player);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

}
