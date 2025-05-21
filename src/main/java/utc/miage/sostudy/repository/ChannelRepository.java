package utc.miage.sostudy.repository;

import utc.miage.sostudy.model.entity.Channel;
import utc.miage.sostudy.model.entity.Message;
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

    /**
     * Find all private channels between two users.
     *
     * @param userId the id of the first user
     * @param user2Id the id of the second user
     * @return the list of private channels between the two users
     */
    @Query("""
    SELECT c FROM Channel c
    JOIN c.users u
    WHERE u.idUser IN (:userId, :user2Id)
    GROUP BY c
    HAVING COUNT(DISTINCT u.idUser) = 2 AND SIZE(c.users) = 2
""")
    List<Channel> findPrivateChannelBetween(@Param("userId") int userId, @Param("user2Id") int user2Id);

    /**
     * Find all channels subscribed by a user.
     *
     * @param userID the id of the user
     * @return the list of channels subscribed by the user
     */
    @Query("""
    SELECT c FROM User u
    JOIN u.subscribedChannels c
    WHERE u.idUser = :userID
""")
    List<Channel> findByUsers(@Param("userID") int userID);

    /**
     * Find the last message of a channel.
     *
     * @param channel the channel
     * @return the last message of the channel
     */
    @Query("""
    SELECT m FROM Message m
    WHERE m.channel = :channel
    ORDER BY m.dateMessage DESC
    LIMIT 1
    """)
    Message findLastMessageByChannel(Channel channel);
}
