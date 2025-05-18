package m1.miage.sostudy.controller;

import m1.miage.sostudy.model.entity.Channel;
import m1.miage.sostudy.model.entity.Message;
import m1.miage.sostudy.repository.ChannelRepository;
import m1.miage.sostudy.repository.MessageRepository;
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

    @MessageMapping("/channels/{channelId}/send")
    @SendTo("/topic/messages/{channelId}")
    public Message sendMessage(@DestinationVariable Integer channelId, Message message) {

        Channel channel = chan.findById(channelId)
                .orElseThrow(() -> new RuntimeException("Canal introuvable"));

        message.setChannel(channel);
        message.setDateMessage(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));

        Message saved = messageRepository.save(message);

        return saved;
    }



}
