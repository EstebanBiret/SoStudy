package utc.miage.sostudy.repository;

import utc.miage.sostudy.model.entity.UserPostReaction;
import utc.miage.sostudy.model.entity.id.UserPostReactionID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for UserPostReaction entity.
 */
@Repository

public interface UserPostReactionRepository extends JpaRepository<UserPostReaction, UserPostReactionID> {
    /**
     * Find all user post reactions by post id.
     * @param postId the id of the post
     * @return the list of user post reactions
     */
    List<UserPostReaction> findByPost_PostId(Integer postId);

    /**
     * Find all user post reactions by user id.
     * @param userId the id of the user
     * @return the list of user post reactions
     */
    List<UserPostReaction> findByUser_IdUser(Integer userId);
}
