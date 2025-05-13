package m1.miage.sostudy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for managing channels.
 */
@Controller
@RequestMapping("/channels")
public class ChannelController {

    /**
     * Displays the list of all channels.
     *
     * @return the name of the view to be rendered
     */
    @GetMapping("/")
    public String getAllChannels() {
        // Logic to retrieve all channels
        return "channels"; // Return the name of the view (e.g., Thymeleaf template)
    }

    /**
     * Displays the form to create a new channel.
     *
     * @return the name of the view to be rendered
     */
    @GetMapping("/new")
    public String createChannel() {
        // Logic to create a new channel
        return "create_channel"; // Return the name of the view (e.g., Thymeleaf template)
    }

    /**
     * Saves the new channel.
     *
     * @return a redirect to the list of channels
     */
    @PostMapping("/new")
    public String saveChannel() {
        // Logic to save the new channel
        return "redirect:/channels/"; // Redirect to the list of channels after saving
    }

    /**
     * Displays the correct channel.
     *
     * @return the name of the view to be rendered
     */
    @GetMapping("/{id}")
    public String getChannelById() {
        // Logic to retrieve a channel by its ID
        return "channel"; // Return the name of the view (e.g., Thymeleaf template)
    }

}
