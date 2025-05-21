package utc.miage.sostudy.controller;

import utc.miage.sostudy.model.entity.Channel;
import utc.miage.sostudy.model.entity.Message;
import utc.miage.sostudy.model.entity.User;
import utc.miage.sostudy.repository.ChannelRepository;
import utc.miage.sostudy.repository.MessageRepository;
import utc.miage.sostudy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation .MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Controller for the Message entity
 */
@Controller
@RequestMapping("/message")
public class MessageController {

    /**
     * Channel repository
     */
    @Autowired
    private ChannelRepository chan;

    /**
     * Message repository
     */
    @Autowired
    private MessageRepository messageRepository;

    /**
     * User repository
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * send a message
     * @param idchannel the id of the channel
     * @return the name of the view to be rendered
     */
    @PostMapping("/{idchannel}/send")
    public String sendMessage(@PathVariable int idchannel) {
        return "redirect:/channels/";
    }

    /**
     * Handles the sending of a message to a specific channel.
     *
     * @param channelId the ID of the channel
     * @param message   the message to be sent
     * @return the sent message
     */
    @MessageMapping("/channels/{channelId}/send")
    @SendTo("/topic/messages/{channelId}")
    public Message sendMessage(@DestinationVariable Integer channelId, Message message) {

        Channel channel = chan.findById(channelId)
                .orElseThrow(() -> new RuntimeException("Canal introuvable"));

        User sender = userRepository.findById(message.getUserId())
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        message.setSender(sender);

        message.setChannel(channel);

        message.setDateMessage(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));

        Message saved = messageRepository.save(message);
        //TODO enlever le focus du champ après envoi
        return saved;
    }

}