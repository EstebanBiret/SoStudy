package m1.miage.sostudy.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import m1.miage.sostudy.model.entity.Channel;
import m1.miage.sostudy.model.entity.Message;
import m1.miage.sostudy.model.entity.User;
import m1.miage.sostudy.repository.ChannelRepository;
import m1.miage.sostudy.repository.MessageRepository;
import m1.miage.sostudy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for managing channels.
 */
@Controller
@RequestMapping("/channels")
public class ChannelController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private MessageRepository messageRepository;


    /**
     * Displays the list of all channels.
     *
     * @return the name of the view to be rendered
     */
    @GetMapping("/")
    public String getAllChannels(Model model, HttpServletRequest request, HttpSession session) {
        model.addAttribute("currentUri", request.getRequestURI());

        userRepository.findById(1).ifPresent(user -> {
            if (session.getAttribute("connectedUser") == null) {
                session.setAttribute("connectedUser", user);
            }
        });

        hasACanalAlreayd((User) session.getAttribute("connectedUser"));


        return "message/list_channel";
    }

    /**
     * Displays the form to create a new channel.
     *
     * @return the name of the view to be rendered
     */
    @GetMapping("/new")
    public String createChannel(Model model, HttpServletRequest request, HttpSession session) {
        model.addAttribute("currentUri", request.getRequestURI());

        User sessionUser = (User) session.getAttribute("connectedUser");
        if (sessionUser == null) return "redirect:/login";

        User userConnected = userRepository.findById(sessionUser.getIdUser())
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        List<User> following = userConnected.getFollowing();
        following.size();

        model.addAttribute("following", following);
        return "message/new_channel";
    }

    /**
     * Saves the new channel.
     *
     * @return a redirect to the list of channels
     */
    @PostMapping("/new")
    public String saveChannel(@RequestParam("selectedUsers") String selectedUsersCsv, @RequestParam("firstMessage") String firstMessage, @RequestParam(value = "channelName", required = false) String channelName, HttpSession session) {
        List<Integer> selectedUserIds = Arrays.stream(selectedUsersCsv.split(","))
                .map(Integer::parseInt)
                .toList();

        List<User> selectedUsers = new ArrayList<>();
        Channel channel = new Channel();

        for (Integer id : selectedUserIds) {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
            selectedUsers.add(user);
            channel.addUser(user);
        }

        User sessionUser = (User) session.getAttribute("connectedUser");
        if (sessionUser == null) return "redirect:/login";

        if ((selectedUserIds.size() > 1 && (channelName == null || channelName.isBlank()))) {
            channelName = "Groupe de discussion de " + selectedUsers.stream()
                    .map(User::getPseudo)
                    .collect(Collectors.joining(", "));
        } else if (selectedUsers.size() == 1) {
            channelName = selectedUsers.get(0).getPseudo();
        }

        channel.setChannelName(channelName);
        channel.setChannelImagePath("default.png");
        channel.setCreator(sessionUser);

        channel = channelRepository.save(channel);

        Message message = new Message();
        message.setContent(firstMessage);
        message.setDateMessage(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        message.setChannel(channel);
        message.setSender(sessionUser);

        message = messageRepository.save(message);

        channel.addMessage(message);
        channelRepository.save(channel);


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

    /**
     * Checks if the user already has a channel.
     * @param user the user to check
     * @return true if the user has a channel, false otherwise
     */
    public boolean hasACanalAlreayd(User user) {

        List<User> listU = channelRepository.findUsersSharingChannelOfTwoWith(user.getIdUser());

        if (listU.isEmpty()) {
            System.out.println("No users found sharing channels with user ID: " + user.getIdUser());
            return true;
        }
        for(User u : listU) {
            System.out.println("User: " + u.getIdUser());
        }

        return false;
    }

}
