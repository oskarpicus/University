package service;

import domain.Participant;
import domain.Status;
import repository.ParticipantRepository;
import utils.Constants;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ParticipantService {
    private final ParticipantRepository repository;

    public ParticipantService(ParticipantRepository repository) {
        this.repository = repository;
    }

    public List<Participant> findAll() {
        Iterable<Participant> participants = repository.findAll();
        return StreamSupport.stream(participants.spliterator(), false).collect(Collectors.toList());
    }

    public void updateStatus(Participant participant) {
        int nrMarks = repository.getNumberOfMarksByParticipant(participant);
        Status status = switch (nrMarks) {
            case 0 -> Status.NO_RESULTS;
            case Constants
                    .NUMBER_OF_MARKS -> Status.FINISHED;
            default -> Status.PENDING;
        };
        participant.setStatus(status);
        repository.update(participant);
    }

    public Optional<Participant> update(Participant participant) {
        return repository.update(participant);
    }
}
