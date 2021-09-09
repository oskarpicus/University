package services;

import domain.JuryMember;
import domain.Mark;
import domain.Participant;

import java.util.List;

public interface Service {

    JuryMember login(String username, String password);

    List<Participant> getAllParticipants();

    /**
     * Method for adding a Mark
     * @param mark: Mark, the mark to be added
     * @return - null, if the mark was successfully saved
     *          - the mark, otherwise (e.g. mark already added)
     */
    Mark addMark(Mark mark);

    void addObserver(Observer observer);

    void removeObserver(Observer observer);
}
