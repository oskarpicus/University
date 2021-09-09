package domain;

import java.util.Objects;

public class Mark implements Entity<Long> {
    private Long id;
    private Integer points;
    private JuryMember juryMember;
    private Participant participant;

    public Mark() {
    }

    public Mark(Long id, Integer points, JuryMember juryMember, Participant participant) {
        this.id = id;
        this.points = points;
        this.juryMember = juryMember;
        this.participant = participant;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public JuryMember getJuryMember() {
        return juryMember;
    }

    public void setJuryMember(JuryMember juryMember) {
        this.juryMember = juryMember;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Mark{" + "id=" + id +
                ", points=" + points +
                ", juryMember=" + juryMember +
                ", participant=" + participant +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mark mark = (Mark) o;
        return Objects.equals(id, mark.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
