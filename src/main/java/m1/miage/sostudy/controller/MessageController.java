package m1.miage.sostudy.controller;

import m1.miage.sostudy.model.entity.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/message")
public class MessageController {

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

    @MessageMapping("/send")
    @SendTo("/topic/messages")
    public Message sendMessage(@Payload Message message) {
        return message;
    }



}
