package domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

public class Guess implements Entity<Long>{
    private Long id;
    private Integer line;
    private Integer column;
    @JsonIgnore
    private Player player;
    @JsonIgnore
    private Game game;

    public Guess() {
    }

    public Guess(Long id, Integer line, Integer column, Player player, Game game) {
        this.id = id;
        this.line = line;
        this.column = column;
        this.player = player;
        this.game = game;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLine() {
        return line;
    }

    public void setLine(Integer line) {
        this.line = line;
    }

    public Integer getColumn() {
        return column;
    }

    public void setColumn(Integer column) {
        this.column = column;
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
        return "Guess{" + "id=" + id +
                ", line=" + line +
                ", column=" + column +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Guess guess = (Guess) o;
        return Objects.equals(id, guess.id) &&
                Objects.equals(line, guess.line) &&
                Objects.equals(column, guess.column);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, line, column);
    }
}
