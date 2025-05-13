package m1.miage.sostudy.repository;

import m1.miage.sostudy.model.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Message entity.
 * This interface extends JpaRepository to provide CRUD operations for Message entities.
 */
@Repository
public interface MessageRepo extends JpaRepository<Message, Integer> {

}
