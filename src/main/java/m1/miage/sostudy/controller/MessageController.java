package m1.miage.sostudy.controller;

import m1.miage.sostudy.model.entity.Channel;
import m1.miage.sostudy.model.entity.Message;
import m1.miage.sostudy.model.entity.User;
import m1.miage.sostudy.repository.ChannelRepository;
import m1.miage.sostudy.repository.MessageRepository;
import m1.miage.sostudy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation .MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private ChannelRepository chan;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository user;
    @Autowired
    private UserRepository userRepository;

    /**
     * Displays the form to send a message.
     *
     * @return the name of the view to be rendered
     */
    @PostMapping("/{idchannel}/send")
    public String sendMessage() {
        // Logic to send a message
        return "redirect:/channels/"; // Redirect to the list of channels after sending
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

        return saved;
    }



}
