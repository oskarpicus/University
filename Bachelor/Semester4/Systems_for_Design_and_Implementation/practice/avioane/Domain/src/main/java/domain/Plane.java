package domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

public class Plane implements Entity<Long>{
    private Long id;
    private Integer line;
    private Integer column;
    private Player player;
    @JsonIgnore
    private Game game;

    public Plane() {
    }

    public Plane(Long id, Integer line, Integer column, Player player) {
        this.id = id;
        this.line = line;
        this.column = column;
        this.player = player;
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
        return "Plane{" + "id=" + id +
                ", line=" + line +
                ", column=" + column +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plane plane = (Plane) o;
        return Objects.equals(id, plane.id) &&
                Objects.equals(line, plane.line) &&
                Objects.equals(column, plane.column);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, line, column);
    }
}
