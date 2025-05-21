package m1.miage.sostudy.repository;

import m1.miage.sostudy.model.entity.Message;
import m1.miage.sostudy.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Message entity.
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    /**
     * Finds messages by channel ID and orders them by date in ascending order.
     *
     * @param channelId the ID of the channel
     * @return a list of Message entities ordered by date in ascending order
     */
    List<Message> findByChannel_ChannelIdOrderByDateMessageAsc(int channelId);

    /**
     * Finds messages by sender (user)
     *
     * @param user the sender of the message
     * @return a list of Message entities
     */
    @Query("""
    SELECT m
    FROM Message m
    WHERE m.sender = :user
""")
    List<Message> findBySender(@Param("user") User user);

}