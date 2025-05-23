package utc.miage.sostudy.repository;

import utc.miage.sostudy.model.entity.Repost;
import utc.miage.sostudy.model.entity.Post;
import utc.miage.sostudy.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repository interface for Repost entity.
 */
public interface RepostRepository extends JpaRepository<Repost, Integer> {

    /**
     * Find all reposts of a given post.
     * @param post the original post.
     * @return List of reposts associated.
     */
    List<Repost> findByOriginalPost(Post post);

    /**
     * Find all reposts created by a given user.
     * @param user the user.
     * @return List of reposts created by this user.
     */
    List<Repost> findByUser(User user);

    /**
     * Count the number of reposts of a given post.
     * @param post the original post.
     * @return the number of reposts.
     */
    Integer countByOriginalPost(Post post);

}
