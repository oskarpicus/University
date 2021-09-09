package domain;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Card implements Entity<Long>, Comparable<Card> {
    private Long id;
    private String name;
    private static final List<String> possibleNames = Arrays.asList("6", "7", "8", "9", "J", "Q", "K", "A");

    public Card() {
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Card{" + "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(id, card.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int compareTo(Card o) {
        if (o != null) {
            String name1 = getName(), name2 = o.getName();
            return Integer.compare(possibleNames.indexOf(name1), possibleNames.indexOf(name2));
        }
        return -1;
    }
}
