package m1.miage.sostudy.repository;

import m1.miage.sostudy.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for User entity.
 * This interface extends JpaRepository to provide CRUD operations for User entities.
 */
@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
}
