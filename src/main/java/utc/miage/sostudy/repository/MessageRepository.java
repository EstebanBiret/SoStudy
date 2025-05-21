package utc.miage.sostudy.repository;

import utc.miage.sostudy.model.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import utc.miage.sostudy.model.entity.User;

import java.util.List;

/**
 * Repository interface for Message entity.
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    /**
     * Find all messages by channel id
     * @param channelId the id of the channel
     * @return the list of messages
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