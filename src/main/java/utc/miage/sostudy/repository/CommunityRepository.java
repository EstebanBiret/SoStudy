package utc.miage.sostudy.repository;

import utc.miage.sostudy.model.entity.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
     * Find all communities
     * @return list of communities
     */
    @SuppressWarnings("null")
    List<Community> findAll();
    
    /**
     * Count the number of members in a community
     * @param communityId the ID of the community
     * @return the number of members
     */
    @Query("SELECT COUNT(u) FROM Community c JOIN c.users u WHERE c.communityId = :communityId")
    int countUsersInCommunity(@Param("communityId") int communityId);

    /**
     * Count the number of posts in a community
     * @param communityId the ID of the community
     * @return the number of posts
     */
    @Query("SELECT COUNT(p) FROM Community c JOIN c.posts p WHERE c.communityId = :communityId")
    Integer countPostsInCommunity(@Param("communityId") Integer communityId);
    
    /**
     * Find all communities sorted by creation date
     * @return list of communities sorted by creation date (newest first)
     */
    @Query("SELECT c FROM Community c ORDER BY c.communityCreationDate DESC")
    List<Community> findAllOrderByCreationDate();

    /**
     * Find all communities that a user belongs to
     * @param userId the id of the user
     * @return list of communities
     */
    List<Community> findByUsers_IdUser(Integer userId);

    /**
     * Check if a user is a member of a community
     * @param userId the id of the user
     * @param communityId the id of the community
     * @return true if the user is a member of the community, false otherwise
     */
    boolean existsByUsers_IdUserAndCommunityId(Integer userId, Integer communityId);

    
}