package m1.miage.sostudy.repository;

import m1.miage.sostudy.model.entity.Channel;
import m1.miage.sostudy.model.entity.Message;
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
    SELECT c FROM Channel c
    JOIN c.users u
    WHERE u.idUser IN (:userId, :user2Id)
    GROUP BY c
    HAVING COUNT(DISTINCT u.idUser) = 2 AND SIZE(c.users) = 2
""")
    List<Channel> findPrivateChannelBetween(@Param("userId") int userId, @Param("user2Id") int user2Id);

    @Query("""
    SELECT c FROM User u
    JOIN u.subscribedChannels c
    WHERE u.idUser = :userID
""")
    List<Channel> findByUsers(@Param("userID") int userID);

    @Query("""
    SELECT m FROM Message m
    WHERE m.channel = :channel
    ORDER BY m.dateMessage DESC
    LIMIT 1
    """)

    Message findLastMessageByChannel(Channel channel);
}
