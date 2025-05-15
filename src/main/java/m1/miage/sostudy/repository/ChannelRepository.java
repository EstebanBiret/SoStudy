package m1.miage.sostudy.repository;

import m1.miage.sostudy.model.entity.Channel;
import m1.miage.sostudy.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Channel entity.
 */
@Repository
public interface ChannelRepository extends JpaRepository<Channel, Integer> {

    /**
     * Find a Channel by its channel name.
     *
     * @param channelName the name of the channel
     * @return the Channel entity if found, otherwise null
     */
    Channel findByChannelName(String channelName);

    /**
     * Find a Channel by its id.
     *
     * @param id the id of the channel
     * @return the Channel entity if found, otherwise null
     */
    Channel findById(int id);

    @Query("""
    SELECT DISTINCT u FROM User u
    JOIN u.subscribedChannels c
    WHERE c IN (
        SELECT c2 FROM Channel c2
        JOIN c2.users u2
        WHERE u2.idUser = :userId
        GROUP BY c2
        HAVING COUNT(u2) = 2
    )
    AND u.idUser != :userId
""")
    List<User> findUsersSharingChannelOfTwoWith(@Param("userId") int userId);
}
