package domain;

import java.util.Objects;
import java.util.Set;

public class Game implements Entity<Long>{
    private Long id;
    private Set<Initialisation> initialisations;
    private Set<Round> rounds;

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

    public Set<Initialisation> getInitialisations() {
        return initialisations;
    }

    public void setInitialisations(Set<Initialisation> initialisations) {
        this.initialisations = initialisations;
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
