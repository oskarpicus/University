package domain;

import java.util.Objects;

public class Sea implements Entity<Long> {
    private Long id;
    private String name;

    public Sea() {
    }

    @Override
    public Long getId() {
        return id;
    }

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
        return "Country{" + "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sea sea = (Sea) o;
        return Objects.equals(id, sea.id) &&
                Objects.equals(name, sea.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
