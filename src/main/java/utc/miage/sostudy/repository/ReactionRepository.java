package utc.miage.sostudy.repository;

import utc.miage.sostudy.model.entity.Reaction;
import utc.miage.sostudy.model.enums.ReactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Reaction entity.
 */
@Repository
public interface ReactionRepository extends JpaRepository<Reaction, Integer> {

    /**
     * Find a reaction by its type
     * @param reactionType the type of the reaction
     * @return the reaction
     */
    Reaction findByReactionType(ReactionType reactionType);
}
