package m1.miage.sostudy.repository;

import m1.miage.sostudy.model.entity.UserPostReaction;
import m1.miage.sostudy.model.entity.id.UserPostReactionID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for UserPostReaction entity.
 */
@Repository
public interface UserPostReactionRepository extends JpaRepository<UserPostReaction, UserPostReactionID> {
    List<UserPostReaction> findByPost_PostId(Integer postId);
}
