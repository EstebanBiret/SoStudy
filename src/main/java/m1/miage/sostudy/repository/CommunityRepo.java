package m1.miage.sostudy.repository;

import m1.miage.sostudy.model.entity.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for the Community entity
 */
@Repository
public interface CommunityRepo extends JpaRepository<Community, Integer> {
    /**
     * Find a community by its id
     * @param id the id of the community
     * @return the community
     */
    Community findById(int id);
}
