package m1.miage.sostudy.repository;

import m1.miage.sostudy.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    /**
     * Finds users with pseudo matching the given pattern.
     *
     * @param pseudo the pattern to match
     * @return a list of User entities matching the pattern
     */
    @Query("""
    SELECT u
    FROM User u
    WHERE LOWER(u.pseudo) LIKE LOWER(CONCAT('%', :pseudo, '%'))
    """)
    List<User> findByPseudoLike(@Param("pseudo") String pseudo);

}


