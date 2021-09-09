package service;

import domain.*;
import services.Observer;
import services.Service;
import utils.Constants;

import java.rmi.RemoteException;
import java.util.*;
import java.util.stream.Collectors;

public class MasterService implements Service {

    private final CardService cardService;
    private final GameService gameService;
    private final InitialisationService initialisationService;
    private final PlayerService playerService;
    private final RoundService roundService;

    private final Map<Long, Observer> observers = new HashMap<>();
    private final Map<Long, Card> sentCards = new HashMap<>();
    private final Map<String, Integer> results = new HashMap<>();
    private Long currentGameId;
    private Integer indexRound = 0;

    public MasterService(CardService cardService, GameService gameService, InitialisationService initialisationService, PlayerService playerService, RoundService roundService) {
        this.cardService = cardService;
        this.gameService = gameService;
        this.initialisationService = initialisationService;
        this.playerService = playerService;
        this.roundService = roundService;
    }

    @Override
    public Player login(String username, String password) {
        return playerService.login(username, password).orElse(null);
    }

    @Override
    public void startGame(Long playerId, Observer observer) {
        observers.putIfAbsent(playerId, observer);
        if (observers.size() == Constants.NUMBER_OF_PLAYERS) {  // can start game
            Game game = new Game();
            game.setId(-1L);
            gameService.save(game);
            currentGameId = game.getId();
            this.sendOnlinePlayers();
            this.assignCards(game);
            //gameService.update(currentGame);
        }
    }

    @Override
    public void sendCard(Long playerId, Card card) {
        sentCards.putIfAbsent(playerId, card);
        if (sentCards.size() == Constants.NUMBER_CARDS_PER_ROUND) {
            endRound();
            sentCards.clear();
        }
    }

    private void sendOnlinePlayers() {
        List<Player> players = new ArrayList<>();
        observers.forEach(((aLong, observer) -> players.add(this.playerService.find(aLong).orElse(null))));
        List<String> usernames = players
                .stream()
                .map(Player::getUsername)
                .collect(Collectors.toList());
        observers.forEach((((aLong, observer) -> {
            try {
                observer.setPlayers(usernames);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        })));
        usernames.forEach(s -> results.putIfAbsent(s, 0));
    }

    private void assignCards(Game game) {
        //Set<Initialisation> initialisations = new HashSet<>();
        List<Card> cards = cardService.findAll();
        Random random = new Random();
        for (var entry : observers.entrySet()) {
            Long aLong = entry.getKey();
            Observer observer = entry.getValue();
            Player player = playerService.find(aLong).orElse(null);
            Set<Card> assignedCards = new HashSet<>();
            for (int i = 0; i < Constants.NUMBER_CARDS_PER_PLAYER; i++) {
                int index = random.nextInt(cards.size());
                assignedCards.add(cards.remove(index));
            }
            Initialisation initialisation = new Initialisation(-1L, player, game, assignedCards);
            initialisationService.save(initialisation);
            //initialisations.add(initialisation);
            try {
                observer.setStartCards(assignedCards);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //game.setInitialisations(initialisations);
    }

    private void endRound() {
        // determine winner
        String winner;
        indexRound++;
        int nrRed = (int)sentCards.values().stream()
                .filter(card -> card.getColor().equals("red"))
                .count();
        switch (nrRed) {
            case 1 -> winner = playerOnlyRed();
            case 2, 3 -> winner = playerTwoReds();
            default -> winner = playerZeroReds();
        }

        // save rounds
        //Set<Round> rounds = new HashSet<>();
        Set<Card> cardsWon = new HashSet<>(sentCards.values());
        for (var entry : observers.entrySet()) {
            Long aLong = entry.getKey();
            Card cardSent = sentCards.get(aLong);
            Optional<Player> player = playerService.find(aLong);
            if (player.isPresent()) {
                Set<Card> resultRoundPlayer = winner.equals(player.get().getUsername()) ?
                        cardsWon : new HashSet<>();
                Game currentGame = gameService.find(currentGameId).orElse(null);
                Round round = new Round(-1L, player.get(), currentGame, resultRoundPlayer, cardSent);
                roundService.save(round);
                if (resultRoundPlayer.size() > 0) {
                    int before = results.get(player.get().getUsername());
                    results.replace(player.get().getUsername(), before + resultRoundPlayer.size());
                }
                //rounds.add(round);
            }
            try {
                entry.getValue().displayResultRound(cardsWon, winner);
                System.out.println("Send result round");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        if (indexRound == Constants.NUMBER_OF_ROUNDS) {
            //end game
            sendWinner();
            results.clear();
            currentGameId = null;
            indexRound = 0;
        }
    }

    private String playerOnlyRed() {
        Optional<Optional<Player>> player = sentCards.entrySet().stream()
                .filter(longCardEntry -> longCardEntry.getValue().getColor().equals("red"))
                .map(longCardEntry -> playerService.find(longCardEntry.getKey()))
                .findFirst();
        if (player.isPresent() && player.get().isPresent())
            return player.get().get().getUsername();
        return "";
    }

    private String playerTwoReds() {
        var idsCards = sentCards.entrySet().stream()
                .filter(longCardEntry -> longCardEntry.getValue().getColor().equals("red"))
                .collect(Collectors.toList());
        Long idWinner = idsCards.get(0).getValue().getNumber() > idsCards.get(1).getValue().getNumber() ?
                idsCards.get(0).getKey() :
                idsCards.get(1).getKey();
        var player = playerService.find(idWinner);
        if (player.isPresent())
            return player.get().getUsername();
        return "";
    }

    private String playerZeroReds() {
        var entry = sentCards.entrySet().stream().min(Comparator.comparing(e -> e.getValue().getNumber()));
        if (entry.isPresent()) {
            var player = playerService.find(entry.get().getKey());
            if (player.isPresent()) {
                return player.get().getUsername();
            }
        }
        return "";
    }

    private void sendWinner() {
        observers.values().forEach(observer -> {
            try {
                observer.displayFinalResult(results);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }
}
