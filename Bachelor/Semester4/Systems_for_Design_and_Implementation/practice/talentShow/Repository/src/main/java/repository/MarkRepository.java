package repository;

import domain.JuryMember;
import domain.Mark;
import domain.Participant;

public interface MarkRepository extends Repository<Long, Mark> {
    boolean isMarkAdded(Participant participant, JuryMember juryMember);
}
