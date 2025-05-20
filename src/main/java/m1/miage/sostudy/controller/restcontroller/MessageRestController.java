package m1.miage.sostudy.controller.restcontroller;

import m1.miage.sostudy.model.entity.Message;
import m1.miage.sostudy.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for managing messages.
 */
@RestController
@RequestMapping("/api/messages")
public class MessageRestController {
    /**
     * Message repository for database operations.
     */
    @Autowired
    private MessageRepository messageRepository;

    /**
     * Retrieves all messages for a specific channel.
     *
     * @param channelId the ID of the channel
     * @return a list of messages for the specified channel
     */
    @GetMapping("/channel/{channelId}")
    public List<Message> getMessagesByChannel(@PathVariable int channelId) {
        return messageRepository.findByChannel_ChannelIdOrderByDateMessageAsc(channelId);
    }
}
