package domain;

import java.util.Objects;
import java.util.Set;

public class Participant implements Entity<Long>{
    private Long id;
    private String name;
    private Status status;
    private Set<Mark> marks;


    public Participant() {
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Set<Mark> getMarks() {
        return marks;
    }

    public void setMarks(Set<Mark> marks) {
        this.marks = marks;
    }

    @Override
    public String toString() {
        return "Participant{" + "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participant that = (Participant) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public int getPoints() {
        return getMarks().stream()
                .map(Mark::getPoints)
                .reduce(0, Integer::sum);
    }

    public void updateStatus() {
        switch (getStatus()) {
            case NO_JUMPS -> setStatus(Status.FIRST_JUMP);
            case FIRST_JUMP -> setStatus(Status.FINISHED);
        }
    }
}
