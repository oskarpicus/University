package dtos;

import domain.Game;
import domain.Player;

import java.io.Serializable;

public class GameDto implements Serializable {
    private Game game;
    private Player winner;

    public GameDto() {
    }

    public GameDto(Game game, Player winner) {
        this.game = game;
        this.winner = winner;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }
}
