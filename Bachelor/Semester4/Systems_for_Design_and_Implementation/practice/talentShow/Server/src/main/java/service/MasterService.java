package service;

import domain.JuryMember;
import domain.Mark;
import domain.Participant;
import services.Observer;
import services.Service;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MasterService implements Service {
    private final JuryMemberService juryMemberService;
    private final MarkService markService;
    private final ParticipantService participantService;

    private final List<Observer> observers = new ArrayList<>();

    public MasterService(JuryMemberService juryMemberService, MarkService markService, ParticipantService participantService) {
        this.juryMemberService = juryMemberService;
        this.markService = markService;
        this.participantService = participantService;
    }

    @Override
    public JuryMember login(String username, String password) {
        Optional<JuryMember> juryMember = juryMemberService.findByUsernamePassword(username, password);
        return juryMember.orElse(null);
    }

    @Override
    public List<Participant> getAllParticipants() {
        return this.participantService.findAll();
    }

    @Override
    public Mark addMark(Mark mark) {
        Optional<Mark> mark1 = markService.save(mark);
        if (mark1.isEmpty()) {  // successfully added
            mark.getParticipant().getMarks().add(mark);
            participantService.update(mark.getParticipant());
            participantService.updateStatus(mark.getParticipant());
            observers.forEach(observer -> {
                try {
                    observer.markAdded(mark);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            });
        }
        return mark1.orElse(null);
    }

    @Override
    public void addObserver(Observer observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }
}
