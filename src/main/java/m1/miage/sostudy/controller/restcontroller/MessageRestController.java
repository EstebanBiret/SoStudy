package m1.miage.sostudy.controller.restcontroller;

import m1.miage.sostudy.model.entity.Message;
import m1.miage.sostudy.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageRestController {
    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/channel/{channelId}")
    public List<Message> getMessagesByChannel(@PathVariable int channelId) {
        return messageRepository.findByChannel_ChannelIdOrderByDateMessageAsc(channelId);
    }
}
