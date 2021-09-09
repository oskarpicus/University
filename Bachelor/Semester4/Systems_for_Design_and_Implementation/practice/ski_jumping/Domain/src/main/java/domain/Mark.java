package domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

public class Mark implements Entity<Long>{
    private Long id;
    @JsonIgnore
    private Participant participant;
    @JsonIgnore
    private JuryMember juryMember;
    private Integer points;
    private Integer jumpNumber;

    public Mark() {
    }

    public Mark(Long id, Participant participant, JuryMember juryMember, Integer points, Integer jumpNumber) {
        this.id = id;
        this.participant = participant;
        this.juryMember = juryMember;
        this.points = points;
        this.jumpNumber = jumpNumber;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public JuryMember getJuryMember() {
        return juryMember;
    }

    public void setJuryMember(JuryMember juryMember) {
        this.juryMember = juryMember;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "Mark{" + "id=" + id +
                ", participant=" + participant +
                ", juryMember=" + juryMember +
                ", points=" + points +
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

    public Integer getJumpNumber() {
        return jumpNumber;
    }

    public void setJumpNumber(Integer jumpNumber) {
        this.jumpNumber = jumpNumber;
    }
}
