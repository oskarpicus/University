package service;

import domain.JuryMember;
import repository.JuryMemberRepository;

import java.util.Optional;

public class JuryMemberService {
    private final JuryMemberRepository repository;

    public JuryMemberService(JuryMemberRepository repository) {
        this.repository = repository;
    }

    public Optional<JuryMember> findByUsernamePassword(String username, String password) {
        return repository.findByUsernamePassword(username, password);
    }
}
