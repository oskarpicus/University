package domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

public class Round implements Entity<Long>{
    private Long id;
    private Player player;
    @JsonIgnore
    private Game game;
    private Word correctWord;
    private String answer;
    private Integer index;
    private Integer points;

    public Round() {
    }

    public Round(Long id, Player player, Game game, String answer) {
        this.id = id;
        this.player = player;
        this.game = game;
        this.answer = answer;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Word getCorrectWord() {
        return correctWord;
    }

    public void setCorrectWord(Word correctWord) {
        this.correctWord = correctWord;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "Round{" + "id=" + id +
                ", player=" + player +
                ", game=" + game +
                ", correctWord=" + correctWord +
                ", answer='" + answer + '\'' +
                ", index=" + index +
                ", points=" + points +
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
}
