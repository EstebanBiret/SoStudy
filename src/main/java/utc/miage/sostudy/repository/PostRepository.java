package utc.miage.sostudy.repository;

import utc.miage.sostudy.model.entity.Post;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Post entity.
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    /**
     * Find only top-level posts (not comments or replies) by user ID.
     * @param idUser the ID of the user.
     * @return a list of top-level posts created by the user.
     */
    List<Post> findByUser_IdUserAndCommentFatherIsNull(Integer idUser);

    /**
     * Find all comments (direct or replies) for a given post.
     * @param postId the ID of the post.
     * @return a list of comments and replies.
     */
    List<Post> findByCommentFather_PostId(Integer postId);

    /**
     * Count all comments (direct or replies) for a given post.
     * @param postId the ID of the post.
     * @return the total number of comments and replies.
     */
    Integer countByCommentFather_PostId(Integer postId);

    /**
     * Find all direct comments for a given post.
     * @param postId the ID of the post.
     * @return a list of direct comments.
     */
    List<Post> findByCommentFather_PostIdAndCommentFatherIsNull(Integer postId);

    /**
     * Count all direct comments for a given post.
     * @param postId the ID of the post.
     * @return the number of direct comments.
     */
    Integer countByCommentFather_PostIdAndCommentFatherIsNull(Integer postId);
}
