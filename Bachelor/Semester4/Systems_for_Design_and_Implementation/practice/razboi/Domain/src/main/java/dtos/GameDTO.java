package dtos;

import domain.Game;
import domain.Player;

import java.io.Serializable;

public class GameDTO implements Serializable {

    private Game game;
    private String winnerUsername;

    public GameDTO(Game game, Player winner) {
        this.game = game;
        this.winnerUsername = winner.getUsername();
    }

    public GameDTO() {
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String getWinnerUsername() {
        return winnerUsername;
    }

    public void setWinnerUsername(String winnerUsername) {
        this.winnerUsername = winnerUsername;
    }
}
