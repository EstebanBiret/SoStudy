package m1.miage.sostudy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for the Message entity
 */
@Controller
@RequestMapping("/message")
public class MessageController {

    /**
     * send a message
     * @param idchannel the id of the channel
     * @return the name of the view to be rendered
     */
    @PostMapping("/{idchannel}/send")
    public String sendMessage(@PathVariable int idchannel) {
        return "redirect:/channels/";
    }

}
