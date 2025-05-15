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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
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
        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null) return "redirect:/login";

        List<Channel> chans = ((User) session.getAttribute("user")).getSubscribedChannels();

        if (chans.isEmpty()) {
            model.addAttribute("NoChannel", "Vous n'avez pas encore de canal");
            return "message/list_channel";
        }

        HashMap<Channel, Message> lastMessageMap = new HashMap<>();
        HashMap<Channel, String> pathProfPicMap = new HashMap<>();

        for (Channel channel : chans) {
            if (channel.getUsers().size() == 2) {
                pathProfPicMap.put(channel, channel.getUsers().stream()
                        .filter(user -> user.getIdUser() != ((User) session.getAttribute("user")).getIdUser())
                        .findFirst()
                        .map(User::getPersonImagePath)
                        .orElse("images/profiles_pictures/defaultProfilePic.png"));
            }
            Message lastMessage = channelRepository.findLastMessageByChannel(channel);
            lastMessageMap.put(channel, lastMessage);
        }

        model.addAttribute("profPic", pathProfPicMap);
        model.addAttribute("lastMessageMap", lastMessageMap);


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

        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null) return "redirect:/login";

        User userConnected = userRepository.findById(sessionUser.getIdUser())
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        List<User> following = userConnected.getFollowing();

        model.addAttribute("following", following);
        return "message/new_channel";
    }

    /**
     * Saves the new channel.
     *
     * @return a redirect to the list of channels
     */
    @PostMapping("/new")
    public String saveChannel(@RequestParam("selectedUsers") String selectedUsersCsv, @RequestParam("firstMessage") String firstMessage, @RequestParam(value = "channelName", required = false) String channelName, HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null) return "redirect:/login";

        List<Integer> selectedUserIds = Arrays.stream(selectedUsersCsv.split(","))
                .map(Integer::parseInt)
                .toList();

        List<User> selectedUsers = new ArrayList<>();
        for (Integer id : selectedUserIds) {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
            selectedUsers.add(user);
        }

        // Check if a private channel already exists between the users
        for (User user : selectedUsers) {
            if (hasACanalAlreayd(sessionUser, user)) {
                redirectAttributes.addFlashAttribute("error", "Un canal privé existe déjà entre vous et " + user.getPseudo());
                return "redirect:/channels/new"; // Redirect to the list of channels if a private channel already exists
            }
        }

        Channel channel = new Channel();
        channel.setChannelName(
                (selectedUserIds.size() > 1 && (channelName == null || channelName.isBlank()))
                        ? "Groupe de discussion de " + sessionUser.getPseudo()+", " + selectedUsers.stream()
                        .map(User::getPseudo)
                        .collect(Collectors.joining(", "))
                        : selectedUsers.size() == 1
                        ? selectedUsers.get(0).getPseudo()
                        : channelName
        );
        channel.setChannelImagePath("default.png");
        channel.setCreator(sessionUser);

        channel = channelRepository.save(channel);

        for (Integer id : selectedUserIds) {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
            user.getSubscribedChannels().add(channel);
            userRepository.save(user);

            channel.addUser(user);
        }

        sessionUser.getSubscribedChannels().add(channel);
        userRepository.save(sessionUser);

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
    public boolean hasACanalAlreayd(User user, User user2) {
        List<Channel> existing = channelRepository.findPrivateChannelBetween(user.getIdUser(), user2.getIdUser());
        return !existing.isEmpty();
    }

}
