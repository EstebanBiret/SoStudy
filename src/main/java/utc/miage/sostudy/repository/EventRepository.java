package utc.miage.sostudy.repository;

import utc.miage.sostudy.model.entity.Event;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import utc.miage.sostudy.model.entity.User;

/**
 * Repository interface for Event entity.
 */
@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {

    /**
     * Find all events sorted by creation date
     * @return list of events sorted by creation date (newest first)
     */
    @Query("SELECT e FROM Event e ORDER BY e.eventPublicationDate DESC")
    List<Event> findAllOrderByPublicationDate();

    /**
     * Count the number of members in an event
     * @param eventId the ID of the event
     * @return the number of members
     */
    @Query("SELECT COUNT(u) FROM Event e JOIN e.users u WHERE e.eventId = :eventId")
    int countUsersInEvent(@Param("eventId") int eventId);

    /**
     * Check if a user is a member of an event
     * @param userId the id of the user
     * @param eventId the id of the event
     * @return true if the user is a member of the event, false otherwise
     */
    boolean existsByUsers_IdUserAndEventId(Integer userId, Integer eventId);

    /**
     * Find all events by user
     * @param user the user who created the event
     * @return all events created by the user
     */
    @Query("SELECT e FROM Event e WHERE e.userCreator = :user")
    List<Event> findByUserCreator(@Param("user")User user);

    /**
     * Find all events where the given user is a participant
     * @param user the user who is a participant of the event
     * @return all events the user is a participant of
     */
    @Query("SELECT e FROM Event e WHERE :user MEMBER OF e.users")
    List<Event> findByUserParticipant(@Param("user") User user);
}