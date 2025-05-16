package m1.miage.sostudy.repository;

import m1.miage.sostudy.model.entity.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository interface for Community entity.
 */
@Repository
public interface CommunityRepository extends JpaRepository<Community, Integer> {

    /**
     * Find a community by its id
     * @param id the id of the community
     * @return the community
     */
    Community findById(int id);
    
    /**
     * Find all communities that a user belongs to
     * @param userId the id of the user
     * @return list of communities
     */
    List<Community> findByUsers_IdUser(Integer userId);
    
}