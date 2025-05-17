package m1.miage.sostudy.repository;

import m1.miage.sostudy.model.entity.Post;

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
}
