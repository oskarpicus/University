package domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;
import java.util.Set;

public class Player implements Entity<Long>{
    private Long id;
    private String username;
    private String password;
    @JsonIgnore
    private Set<Plane> planes;
    @JsonIgnore
    private Set<Guess> guesses;
    @JsonIgnore
    private Set<Game> gamesWon;

    public Player() {
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        return "Player{" + "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(id, player.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Set<Game> getGamesWon() {
        return gamesWon;
    }

    public void setGamesWon(Set<Game> gamesWon) {
        this.gamesWon = gamesWon;
    }
}
