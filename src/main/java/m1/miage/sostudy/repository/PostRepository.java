package m1.miage.sostudy.repository;

import m1.miage.sostudy.model.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Post entity.
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
}
