package domain;

import java.util.Objects;

public class Guess implements Entity<Long>{
    private Long id;
    private Integer points;
    private Integer indexRound;
    private Integer position;
    private Player guesser;
    private Game game;
    private Player targetPlayer;


    public Guess() {
    }

    public Guess(Long id, Integer indexRound, Integer position, Player guesser, Game game, Player targetPlayer) {
        this.id = id;
        this.indexRound = indexRound;
        this.position = position;
        this.guesser = guesser;
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

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getIndexRound() {
        return indexRound;
    }

    public void setIndexRound(Integer indexRound) {
        this.indexRound = indexRound;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Player getGuesser() {
        return guesser;
    }

    public void setGuesser(Player guesser) {
        this.guesser = guesser;
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
                ", points=" + points +
                ", indexRound=" + indexRound +
                ", position=" + position +
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
