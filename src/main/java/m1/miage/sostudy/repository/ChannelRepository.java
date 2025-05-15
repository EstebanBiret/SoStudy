package m1.miage.sostudy.repository;

import m1.miage.sostudy.model.entity.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
}
