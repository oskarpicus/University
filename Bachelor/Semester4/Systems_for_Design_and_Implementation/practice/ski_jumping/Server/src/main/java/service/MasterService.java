package service;

import domain.JuryMember;
import domain.Mark;
import domain.Participant;
import repository.JuryMemberRepository;
import repository.MarkRepository;
import repository.ParticipantRepository;
import services.Observer;
import services.Service;
import utils.Constants;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MasterService implements Service {
    private final ParticipantRepository participantRepository;
    private final MarkRepository markRepository;
    private final JuryMemberRepository juryMemberRepository;

    private final Map<JuryMember, Observer> observers = new HashMap<>();

    public MasterService(ParticipantRepository participantRepository, MarkRepository markRepository, JuryMemberRepository juryMemberRepository) {
        this.participantRepository = participantRepository;
        this.markRepository = markRepository;
        this.juryMemberRepository = juryMemberRepository;
    }

    @Override
    public JuryMember login(String username, String password) {
        return juryMemberRepository.findByUsernamePassword(username, password).orElse(null);
    }

    @Override
    public void addObserver(JuryMember juryMember, Observer observer) {
        observers.putIfAbsent(juryMember, observer);
        List<Participant> participants = StreamSupport
                .stream(participantRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        try {
            observer.setParticipants(participants);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeObserver(JuryMember juryMember) {
        observers.remove(juryMember);
    }

    @Override
    public Mark addMark(Mark mark) {
        if (markRepository.getMarkByJuryParticipant(mark.getJuryMember(), mark.getParticipant(), mark.getJumpNumber()).isPresent()) {
            return null;
        }
        markRepository.save(mark);
        Optional<Participant> participant = participantRepository.find(mark.getParticipant().getId());
        if (participant.isPresent()) {
            participant.get().getMarks().add(mark);
            if (participant.get().getMarks().size() % Constants.NUMBER_OF_JURORS == 0) {  // every juror evaluated his jump
                participant.get().updateStatus();
                observers.values().forEach(observer -> {
                    try {
                        observer.updateParticipant(participant.get());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                });
            }
            participantRepository.update(participant.get());
        }
        return mark;
    }
}
