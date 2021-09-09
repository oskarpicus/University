package domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

public class Answer implements Entity<Long>{
    private Long id;
    @JsonIgnore
    private Player player;
    @JsonIgnore
    private Game game;
    private Integer points;
    private Integer indexRound;
    private String characteristic1;
    private String characteristic2;
    private Word word;

    public Answer() {
    }

    public Answer(Long id, Player player, Game game, Integer indexRound, String characteristic1, String characteristic2, Word word) {
        this.id = id;
        this.player = player;
        this.game = game;
        this.indexRound = indexRound;
        this.characteristic1 = characteristic1;
        this.characteristic2 = characteristic2;
        this.word = word;
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

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public String getCharacteristic1() {
        return characteristic1;
    }

    public void setCharacteristic1(String characteristic1) {
        this.characteristic1 = characteristic1;
    }

    public String getCharacteristic2() {
        return characteristic2;
    }

    public void setCharacteristic2(String characteristic2) {
        this.characteristic2 = characteristic2;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    @Override
    public String toString() {
        return "Answer{" + "id=" + id +
                ", points=" + points +
                ", characteristic1='" + characteristic1 + '\'' +
                ", characteristic2='" + characteristic2 + '\'' +
                ", word=" + word +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer = (Answer) o;
        return Objects.equals(id, answer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Integer getIndexRound() {
        return indexRound;
    }

    public void setIndexRound(Integer indexRound) {
        this.indexRound = indexRound;
    }
}
