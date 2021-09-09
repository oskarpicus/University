package domain;

import java.util.Objects;
import java.util.Set;

public class JuryMember implements Entity<Long>{
    private Long id;
    private String username;
    private String password;
    private Aspect aspect;
    private Set<Mark> marks;

    public JuryMember() {
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Aspect getAspect() {
        return aspect;
    }

    public void setAspect(Aspect aspect) {
        this.aspect = aspect;
    }

    public Set<Mark> getMarks() {
        return marks;
    }

    public void setMarks(Set<Mark> marks) {
        this.marks = marks;
    }

    @Override
    public String toString() {
        return "JuryMember{" + "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", aspect=" + aspect +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JuryMember that = (JuryMember) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
