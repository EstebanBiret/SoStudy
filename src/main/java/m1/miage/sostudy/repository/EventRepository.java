package m1.miage.sostudy.repository;

import m1.miage.sostudy.model.entity.Event;
import m1.miage.sostudy.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Event entity.
 */
@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    /**
     * Find an event by its id
     * @param id the id of the event
     * @return the event
     */
    Event findById(int id);

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