package domain;

import java.util.Objects;

public class Guess implements Entity<Long>{
    private Long id;
    private Integer indexRound;
    private Integer points;
    private String letter;
    private Player player;
    private Game game;
    private Player targetPlayer;

    public Guess() {
    }

    public Guess(Long id, String letter, Player player, Game game, Player targetPlayer) {
        this.id = id;
        this.letter = letter;
        this.player = player;
        this.game = game;
        this.targetPlayer = targetPlayer;
        this.points = 0;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIndexRound() {
        return indexRound;
    }

    public void setIndexRound(Integer indexRound) {
        this.indexRound = indexRound;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
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

    public Player getTargetPlayer() {
        return targetPlayer;
    }

    public void setTargetPlayer(Player targetPlayer) {
        this.targetPlayer = targetPlayer;
    }

    @Override
    public String toString() {
        return "Guess{" + "id=" + id +
                ", indexRound=" + indexRound +
                ", points=" + points +
                ", letter='" + letter + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Guess guess = (Guess) o;
        return Objects.equals(id, guess.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
