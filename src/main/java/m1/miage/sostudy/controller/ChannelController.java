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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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

    /**
     * User repository for database operations.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Channel repository for database operations.
     */
    @Autowired
    private ChannelRepository channelRepository;

    /**
     * Message repository for database operations.
     */
    @Autowired
    private MessageRepository messageRepository;

    /**
     * Default path for uploading files.
     */
    private final String UPLOAD_DIR = "./src/main/resources/static/images/channel/";



    /**
     * Displays the list of all channels.
     * @param model the model to be used in the view
     * @param request the HTTP request
     * @param session the HTTP session
     * @return the name of the view to be rendered
     */
    @GetMapping("/")
    public String getAllChannels(Model model, HttpServletRequest request, HttpSession session) {
        model.addAttribute("currentUri", request.getRequestURI());
        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null) return "redirect:/auth/login";

        List<Channel> chans = sessionUser.getSubscribedChannels();

        if (chans.isEmpty()) {
            model.addAttribute("NoChannel", "Vous n'avez pas encore de canal");
            return "message/list_channel";
        }

        HashMap<Channel, Message> lastMessageMap = new HashMap<>();
        HashMap<Channel, String> profPicMap = new HashMap<>();
        HashMap<Channel, String> profPseudoMap = new HashMap<>();

        System.out.println("chans: " + chans);

        for (Channel channel : chans) {
            //have the path of the profile picture of the other user
            if (channel.getUsers().size() == 2) {
                profPicMap.put(channel, channel.getUsers().stream()
                        .filter(user -> user.getIdUser() != sessionUser.getIdUser())
                        .findFirst()
                        .map(User::getPersonImagePath)
                        .orElse("/images/profiles_pictures/defaultProfilePic.png"));
                //have the pseudo of the other user
                profPseudoMap.put(channel, channel.getUsers().stream()
                        .filter(user -> user.getIdUser() != sessionUser.getIdUser())
                        .findFirst()
                        .map(User::getPseudo)
                        .orElse("Utilisateur anonyme"));
            }

            Message lastMessage = channelRepository.findLastMessageByChannel(channel);
            lastMessageMap.put(channel, lastMessage);
        }

        System.out.println("lastMessageMap: " + lastMessageMap);
        model.addAttribute("currentUser", sessionUser);

        model.addAttribute("profPicMap", profPicMap);
        model.addAttribute("lastMessageMap", lastMessageMap);
        model.addAttribute("profPseudoMap", profPseudoMap);

        return "message/list_channel";
    }

    /**
     * Displays the form to create a new channel.
     * @param model the model to be used in the view
     * @param request the HTTP request
     * @param session the HTTP session
     * @return the name of the view to be rendered
     */
    @GetMapping("/new")
    public String createChannel(Model model, HttpServletRequest request, HttpSession session) {
        model.addAttribute("currentUri", request.getRequestURI());

        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null) return "redirect:/auth/login";

        User userConnected = userRepository.findById(sessionUser.getIdUser())
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        List<User> following = userConnected.getFollowing();

        model.addAttribute("following", following);
        return "message/new_channel";
    }

    /**
     * Saves the new channel.
     * @param selectedUsersCsv the list of selected users
     * @param firstMessage the first message of the channel
     * @param channelName the name of the channel
     * @param session the HTTP session
     * @param model the model to be used in the view
     * @param redirectAttributes the redirect attributes
     * @return a redirect to the list of channels
     */
    @PostMapping("/new")
    public String saveChannel(@RequestParam String selectedUsers, @RequestParam MultipartFile channelImage, @RequestParam String firstMessage, @RequestParam(required = false) String channelName, HttpSession session, Model model, RedirectAttributes redirectAttributes) throws IOException {
        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null) return "redirect:/auth/login";

        String fileName = null;
        if (!channelImage.isEmpty()) {
            String rawFileName = UUID.randomUUID().toString() + "_" + channelImage.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR, rawFileName);
            Files.createDirectories(filePath.getParent());
            Files.copy(channelImage.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            fileName = "/images/channel/" + rawFileName;
        } else {
            fileName = "/images/channel/defaultChannelImage.png";
        }

        List<Integer> selectedUserIds = Arrays.stream(selectedUsers.split(","))
                .map(Integer::parseInt)
                .toList();

        List<User> selectedUsersList = new ArrayList<>();
        for (Integer id : selectedUserIds) {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
            selectedUsersList.add(user);
        }

        // Check if a private channel already exists between the users
        for (User user : selectedUsersList) {
            if (hasACanalAlready(sessionUser, user) && selectedUsersList.size() == 1) {
                redirectAttributes.addFlashAttribute("error", "Un canal privé existe déjà entre vous et " + user.getPseudo());
                return "redirect:/channels/new"; // Redirect to the list of channels if a private channel already exists
            }
        }

        //todo régler soucis quand on créé un canal avec un user avec qui on est déjà en discussion et un autre nouveau, ça bloque sur celui avec qui on est déjà en discussion
        //todo quand on vient de créer un canal à 2, l'image du canal ne marche pas, mais marche quand on se déco-reco
        //todo ajouter dans form quand c'est > 2 un champ pour mettre l'image du canal

        Channel channel = new Channel();
        channel.setChannelImagePath(fileName);
        channel.setChannelName(
                (selectedUserIds.size() > 1 && (channelName == null || channelName.isBlank()))
                        ? sessionUser.getPseudo()+", " + selectedUsersList.stream()
                        .map(User::getPseudo)
                        .collect(Collectors.joining(", "))
                        : selectedUsersList.size() == 1
                        ? selectedUsersList.get(0).getPseudo() + " - " + sessionUser.getPseudo()
                        : channelName
        );
        channel.setCreator(sessionUser);

        channel = channelRepository.save(channel);

        for (User user : selectedUsersList) {
            channel.addUser(user);
            user.getSubscribedChannels().add(channel);
            userRepository.save(user);
        }

        channel.addUser(sessionUser);
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

        // Add a small delay using a temporary redirect
        return "redirect:/channels/temporary-redirect";
    }

    /**
     * Temporary redirect to ensure the image is properly saved
     * @return redirect to channel
     * @throws InterruptedException
     */
    @GetMapping("/temporary-redirect")
    public String temporaryRedirect() throws InterruptedException {
        // Wait for 1 second to ensure the image is properly saved
        Thread.sleep(1000);
        return "redirect:/channels/";
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
     * @param user2 the other user to check
     * @return true if the user has a channel, false otherwise
     */
    public boolean hasACanalAlready(User user, User user2) {
        List<Channel> existing = channelRepository.findPrivateChannelBetween(user.getIdUser(), user2.getIdUser());
        return !existing.isEmpty();
    }

}
