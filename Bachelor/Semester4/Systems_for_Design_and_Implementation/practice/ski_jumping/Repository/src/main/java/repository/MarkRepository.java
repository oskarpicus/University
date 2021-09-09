package repository;

import domain.JuryMember;
import domain.Mark;
import domain.Participant;

import java.util.Optional;

public interface MarkRepository extends Repository<Long, Mark> {
    Optional<Mark> getMarkByJuryParticipant(JuryMember juryMember, Participant participant, Integer jumpNumber);
}
