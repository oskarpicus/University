package services;

import domain.JuryMember;
import domain.Mark;

public interface Service {
    JuryMember login(String username, String password);

    void addObserver(JuryMember juryMember, Observer observer);

    void removeObserver(JuryMember juryMember);

    Mark addMark(Mark mark);
}
