package domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Round implements Entity<Long>{
    private Long id;
    private Integer indexRound;
    @JsonIgnore
    private Player player;
    @JsonIgnore
    private Game game;
    private Card cardSent;
    private Set<Card> cardsWon;

    public Round() {
    }

    public Round(Long id, Player player, Game game, Card cardSent) {
        this.id = id;
        this.player = player;
        this.game = game;
        this.cardSent = cardSent;
        this.cardsWon = new HashSet<>();
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
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

    public Card getCardSent() {
        return cardSent;
    }

    public void setCardSent(Card cardSent) {
        this.cardSent = cardSent;
    }

    public Set<Card> getCardsWon() {
        return cardsWon;
    }

    public void setCardsWon(Set<Card> cardsWon) {
        this.cardsWon = cardsWon;
    }

    @Override
    public String toString() {
        return "Round{" + "id=" + id +
                ", player=" + player +
                ", game=" + game +
                ", cardSent=" + cardSent +
                ", cardsWon=" + cardsWon +
                ", indexRound=" + indexRound +
                '}';
    }

    public Integer getIndexRound() {
        return indexRound;
    }

    public void setIndexRound(Integer indexRound) {
        this.indexRound = indexRound;
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
}
