package m1.miage.sostudy.repository;

import m1.miage.sostudy.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for User entity.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Finds a User by their pseudo.
     *
     * @param pseudo the pseudo of the user
     * @return the User entity if found, null otherwise
     */
    User findByPseudo(String pseudo);

    /**
     * Finds a User by their email.
     *
     * @param email the email of the user
     * @return the User entity if found, null otherwise
     */
    User findByEmail(String email);
}


