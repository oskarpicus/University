package service;

import domain.Mark;
import repository.MarkRepository;

import java.util.Optional;

public class MarkService {
    private final MarkRepository repository;

    public MarkService(MarkRepository repository) {
        this.repository = repository;
    }

    /**
     * Method for adding a mark
     * @param mark: Mark, the mark to be added
     * @return - null, if the mark was successfully added
     *          - the mark, otherwise (e.g. already added)
     */
    public Optional<Mark> save(Mark mark) {
        if (repository.isMarkAdded(mark.getParticipant(), mark.getJuryMember())) {
            return Optional.of(mark);
        }
        return repository.save(mark);
    }
}
