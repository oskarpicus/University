package domain;

import java.util.Objects;
import java.util.Set;

public class Game implements Entity<Long>{
    private Long id;
    private Set<Round> rounds;

    public Game() {
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Round> getRounds() {
        return rounds;
    }

    public void setRounds(Set<Round> rounds) {
        this.rounds = rounds;
    }

    @Override
    public String toString() {
        return "Game{" + "id=" + id +
                ", rounds=" + rounds +
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
