package domain;

import java.util.Objects;

public class Answer implements Entity<Long>{
    private Long id;
    private String letterChosen;
    private Integer points;
    private String city;
    private String sea;
    private String country;
    private Player player;
    private Game game;

    public Answer() {
    }

    public Answer(Long id, String letterChosen, Integer points, String city, String sea, String country, Player player, Game game) {
        this.id = id;
        this.letterChosen = letterChosen;
        this.points = points;
        this.city = city;
        this.sea = sea;
        this.country = country;
        this.player = player;
        this.game = game;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLetterChosen() {
        return letterChosen;
    }

    public void setLetterChosen(String letterChosen) {
        this.letterChosen = letterChosen;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSea() {
        return sea;
    }

    public void setSea(String sea) {
        this.sea = sea;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    @Override
    public String toString() {
        return "Answer{" + "id=" + id +
                ", letterChosen='" + letterChosen + '\'' +
                ", points=" + points +
                ", city='" + city + '\'' +
                ", sea='" + sea + '\'' +
                ", country='" + country + '\'' +
                ", player=" + player +
                ", game=" + game +
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

    public void addPoints(Integer points) {
        this.setPoints(getPoints() + points);
    }
}
