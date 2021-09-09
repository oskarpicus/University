package domain;

import java.util.Objects;

public class Round implements Entity<Long> {
    private Long id;
    private Integer n;
    private Integer indexRound;
    private Boolean displaced;
    private Position oldPosition;
    private Position newPosition;
    private Game game;
    private Player player;

    public Round() {
    }

    public Round(Long id, Integer n, Integer indexRound, Position oldPosition, Game game, Player player) {
        this.id = id;
        this.n = n;
        this.indexRound = indexRound;
        this.oldPosition = oldPosition;
        this.game = game;
        this.player = player;
        this.displaced = false;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Integer getN() {
        return n;
    }

    public void setN(Integer n) {
        this.n = n;
    }

    public Integer getIndexRound() {
        return indexRound;
    }

    public void setIndexRound(Integer indexRound) {
        this.indexRound = indexRound;
    }

    public Boolean getDisplaced() {
        return displaced;
    }

    public void setDisplaced(Boolean displaced) {
        this.displaced = displaced;
    }

    public Position getOldPosition() {
        return oldPosition;
    }

    public void setOldPosition(Position oldPosition) {
        this.oldPosition = oldPosition;
    }

    public Position getNewPosition() {
        return newPosition;
    }

    public void setNewPosition(Position newPosition) {
        this.newPosition = newPosition;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
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

    @Override
    public String toString() {
        return "Round{" + "id=" + id +
                ", n=" + n +
                ", indexRound=" + indexRound +
                ", displaced=" + displaced +
                '}';
    }
}
