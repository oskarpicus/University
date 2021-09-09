package domain;

import java.util.Objects;

public class Position implements Entity<Long>{
    private Long id;
    private Integer indexPosition;
    private Boolean x;
    private Game game;
    private Player player;

    public Position() {
    }

    public Position(Long id, Integer index, Boolean x, Game game) {
        this.id = id;
        this.indexPosition = index;
        this.x = x;
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

    public Integer getIndexPosition() {
        return indexPosition;
    }

    public void setIndexPosition(Integer index) {
        this.indexPosition = index;
    }

    public Boolean getX() {
        return x;
    }

    public void setX(Boolean x) {
        this.x = x;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public String toString() {
        return "Position{" + "id=" + id +
                ", index=" + indexPosition +
                ", x=" + x +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return Objects.equals(id, position.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String getText() {
        return getX() ? "X" : "_";
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
