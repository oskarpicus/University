package repository;

import domain.Participant;

public interface ParticipantRepository extends Repository<Long, Participant> {
    Integer getNumberOfMarksByParticipant(Participant participant);
}
