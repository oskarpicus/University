package domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;
import java.util.Set;

public class Initialisation implements Entity<Long>{
    private Long id;
    private Player player;
    @JsonIgnore
    private Game game;
    private Set<Card> startingCards;

    public Initialisation() {
    }

    public Initialisation(Long id, Player player, Game game, Set<Card> startingCards) {
        this.id = id;
        this.player = player;
        this.game = game;
        this.startingCards = startingCards;
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

    public Set<Card> getStartingCards() {
        return startingCards;
    }

    public void setStartingCards(Set<Card> startingCards) {
        this.startingCards = startingCards;
    }

    @Override
    public String toString() {
        return "Initialisation{" + "id=" + id +
                ", player=" + player +
                ", game=" + game +
                ", startingCards=" + startingCards +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Initialisation that = (Initialisation) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
