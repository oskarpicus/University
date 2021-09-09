package repository;

import domain.JuryMember;

import java.util.Optional;

public interface JuryMemberRepository extends Repository<Long, JuryMember>{
    /**
     * Method for finding a user based on its username or password (e.g. login)
     * @param username: String, the desired username
     * @param password: String, the desired password
     * @return an {@code Optional}
     *         - null, if there is no User with username and password
     *         - the user, otherwise
     */
    Optional<JuryMember> findByUsernamePassword(String username, String password);
}
