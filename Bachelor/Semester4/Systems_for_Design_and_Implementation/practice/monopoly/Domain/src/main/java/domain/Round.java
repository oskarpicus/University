package domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

public class Round implements Entity<Long>{
    private Long id;
    private Integer n;
    private Integer paid;
    private Integer received;
    private Integer total;
    private Integer indexRound;
    @JsonIgnore
    private Player player;
    @JsonIgnore
    private Game game;
    private Position oldPosition;
    private Position newPosition;

    public Round() {
    }

    public Round(Long id, Integer n, Player player, Game game, Position oldPosition) {
        this.id = id;
        this.n = n;
        this.player = player;
        this.game = game;
        this.oldPosition = oldPosition;
        this.paid = 0;
        this.received = 0;
        this.total = 0;
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

    public Integer getPaid() {
        return paid;
    }

    public void setPaid(Integer paid) {
        this.paid = paid;
    }

    public Integer getReceived() {
        return received;
    }

    public void setReceived(Integer received) {
        this.received = received;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
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

    public Position getNewPosition() {
        return newPosition;
    }

    public void setNewPosition(Position newPosition) {
        this.newPosition = newPosition;
    }

    @Override
    public String toString() {
        return "Round{" + "id=" + id +
                ", n=" + n +
                ", paid=" + paid +
                ", received=" + received +
                ", total=" + total +
                '}';
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

    public Position getOldPosition() {
        return oldPosition;
    }

    public void setOldPosition(Position oldPosition) {
        this.oldPosition = oldPosition;
    }

    public Integer getIndexRound() {
        return indexRound;
    }

    public void setIndexRound(Integer indexRound) {
        this.indexRound = indexRound;
    }
}
