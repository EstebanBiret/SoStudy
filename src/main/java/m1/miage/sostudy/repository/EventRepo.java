package m1.miage.sostudy.repository;

import m1.miage.sostudy.model.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for the Event entity
 */
@Repository
public interface EventRepo extends JpaRepository<Event, Integer> {

    /**
     * Find an event by its id
     * @param id the id of the event
     * @return the event
     */
    Event findById(int id);

}
