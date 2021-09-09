package domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Game implements Entity<Long>{
    private Long id;
    private Set<Plane> planes;
    @JsonIgnore
    private Set<Guess> guesses;
    private Player winner;

    public Game() {
    }

    public Game(Long id, Set<Plane> planes) {
        this.id = id;
        this.planes = planes;
        this.guesses = new HashSet<>();
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Set<Plane> getPlanes() {
        return planes;
    }

    public void setPlanes(Set<Plane> planes) {
        this.planes = planes;
    }

    public Set<Guess> getGuesses() {
        return guesses;
    }

    public void setGuesses(Set<Guess> guesses) {
        this.guesses = guesses;
    }

    @Override
    public String toString() {
        return "Game{" + "id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return Objects.equals(id, game.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }
}
