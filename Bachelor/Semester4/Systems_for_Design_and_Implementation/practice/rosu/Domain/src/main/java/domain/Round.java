package domain;

import java.util.Objects;
import java.util.Set;

public class Round implements Entity<Long>{
    private Long id;
    private Player player;
    private Game game;
    private Set<Card> cardsWon;
    private Card cardSent;

    public Round() {
    }

    public Round(Long id, Player player, Game game, Set<Card> cardsWon, Card cardSent) {
        this.id = id;
        this.player = player;
        this.game = game;
        this.cardsWon = cardsWon;
        this.cardSent = cardSent;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public String toString() {
        return "Round{" + "id=" + id +
                ", player=" + player +
                ", game=" + game +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Round round = (Round) o;
        return Objects.equals(id, round.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Set<Card> getCardsWon() {
        return cardsWon;
    }

    public void setCardsWon(Set<Card> cardsWon) {
        this.cardsWon = cardsWon;
    }

    public Card getCardSent() {
        return cardSent;
    }

    public void setCardSent(Card cardSent) {
        this.cardSent = cardSent;
    }
}
