package m1.miage.sostudy.repository;

import m1.miage.sostudy.model.entity.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Reaction entity.
 */
@Repository
public interface ReactionRepository extends JpaRepository<Reaction, Integer> {
}
