package repository;

import domain.Participant;
import domain.Status;

import java.util.List;

public interface ParticipantRepository extends Repository<Long, Participant> {
    List<Participant> findParticipantByStatus(Status status);
}
