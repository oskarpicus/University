package dtos;

import domain.Player;

import java.io.Serializable;

public class GameRanking implements Serializable {
    private Player player;
    private int points;

    public GameRanking(Player player, int points) {
        this.player = player;
        this.points = points;
    }

    public GameRanking() {
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
