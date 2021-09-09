package domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;
import java.util.Set;

public class Game implements Entity<Long>{
    private Long id;
    @JsonIgnore
    private Set<Guess> guesses;
    @JsonIgnore
    private Set<Item> items;

    public Game() {
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Set<Guess> getGuesses() {
        return guesses;
    }

    public void setGuesses(Set<Guess> guesses) {
        this.guesses = guesses;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
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
}
