package utc.miage.sostudy.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;
import utc.miage.sostudy.model.entity.dto.ChannelDTO;
import utc.miage.sostudy.model.entity.Channel;
import utc.miage.sostudy.model.entity.Message;
import utc.miage.sostudy.model.entity.User;
import utc.miage.sostudy.repository.ChannelRepository;
import utc.miage.sostudy.repository.MessageRepository;
import utc.miage.sostudy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    private String uploadDir = "./src/main/resources/static/images/channel/";

    /**
     * Displays the list of all channels.
     * @param model the model to be used in the view
     * @param request the HTTP request
     * @param session the HTTP session
     * @return the name of the view to be rendered
     * @throws JsonProcessingException if an error occurs while processing the JSON
     */
    @GetMapping("/")
    public String getAllChannels(Model model, HttpServletRequest request, HttpSession session) throws JsonProcessingException {
        model.addAttribute("currentUri", request.getRequestURI());
        User sessionUserTemp = (User) session.getAttribute("user");
        if (sessionUserTemp == null) return "redirect:/auth/login";

        User sessionUser = userRepository.findById(sessionUserTemp.getIdUser())
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        ArrayList<Channel> chans = (ArrayList<Channel>) channelRepository.findChannelsByUserOrderByLastMessageDate(sessionUser.getIdUser());

        if (chans.isEmpty()) {
            model.addAttribute("NoChannel", "Vous n'avez pas encore de canal");
            return "message/list_channel";
        }

        HashMap<Channel, Message> lastMessageMap = new HashMap<>();
        HashMap<Channel, String> profPicMap = new HashMap<>();
        HashMap<Channel, String> profPseudoMap = new HashMap<>();


        for (Channel channel : chans) {
            //have the path of the profile picture of the other user
            if (channel.getUsers().size() == 2) {
                profPicMap.put(channel, channel.getUsers().stream()
                        .filter(user -> user.getIdUser() != sessionUser.getIdUser())
                        .findFirst()
                        .map(User::getPersonImagePath)
                        .orElse("/images/profiles_pictures/defaultProfilePic.jpg"));
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




        model.addAttribute("currentUser", sessionUser);
        model.addAttribute("channels", chans);

        model.addAttribute("profPicMap", profPicMap);
        model.addAttribute("lastMessageMap", lastMessageMap);
        model.addAttribute("profPseudoMap", profPseudoMap);

        Map<String, String> jsonChannelMap = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();

        for (Channel channel : chans) {
            ChannelDTO dto = new ChannelDTO(channel);
            String json = objectMapper.writeValueAsString(dto);
            jsonChannelMap.put(String.valueOf(channel.getChannelId()), json);
        }

        model.addAttribute("jsonChannelMap", jsonChannelMap);


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
     * @param selectedUsers the list of selected users
     * @param channelImage the image of the channel
     * @param firstMessage the first message of the channel
     * @param channelName the name of the channel
     * @param session the HTTP session
     * @param model the model to be used in the view
     * @param redirectAttributes the redirect attributes
     * @return a redirect to the list of channels
     * @throws IOException if an I/O error occurs
     */
    @PostMapping("/new")
    public String saveChannel(@RequestParam String selectedUsers, @RequestParam MultipartFile channelImage, @RequestParam String firstMessage, @RequestParam(required = false) String channelName, HttpSession session, Model model, RedirectAttributes redirectAttributes) throws IOException {
        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null) return "redirect:/auth/login";

        String fileName = null;
        if (!channelImage.isEmpty()) {
            String rawFileName = UUID.randomUUID().toString() + "_" + channelImage.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, rawFileName);
            Files.createDirectories(filePath.getParent());
            Files.copy(channelImage.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            fileName = "/images/channel/" + rawFileName;
        } else {
            fileName = "/images/channel/defaultChannelImage.jpg";
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

        session.setAttribute("user", sessionUser);

        // Add a small delay using a temporary redirect
        return "redirect:/channels/temporary-redirect";
    }

    /**
     * Temporary redirect to ensure the image is properly saved
     * @return redirect to channel list
     * @throws InterruptedException if the thread is interrupted
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
     * Updates a channel.
     * @param channelId the ID of the channel to update
     * @param channelName the new name of the channel
     * @param deletedUserIdsJson the JSON string containing the IDs of users to delete from the channel
     * @param channelImage the new image of the channel
     * @return a ResponseEntity containing the updated channel
     * @throws IOException if an I/O error occurs
     */
    @PostMapping("/update")
    public ResponseEntity<?> updateChannel(
            @RequestParam int channelId,
            @RequestParam String channelName,
            @RequestParam(value = "deletedUserIds", required = false) String deletedUserIdsJson,
            @RequestParam(required = false) MultipartFile channelImage
    ) throws IOException {
        List<Integer> deletedIds = new ArrayList<>();
        if (deletedUserIdsJson != null && !deletedUserIdsJson.isEmpty()) {
            ObjectMapper mapper = new ObjectMapper();
            deletedIds = mapper.readValue(deletedUserIdsJson, new TypeReference<List<Integer>>() {});
        }

        Channel channel = channelRepository.findById(channelId);
        channel.setChannelName(channelName);

        String fileName = null;
        if (channelImage !=null && !channelImage.isEmpty()) {
            String rawFileName = UUID.randomUUID().toString() + "_" + channelImage.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, rawFileName);
            Files.createDirectories(filePath.getParent());
            Files.copy(channelImage.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            fileName = "/images/channel/" + rawFileName;

            channel.setChannelImagePath(fileName);
        }

        if (!deletedIds.isEmpty()) {
            User deletedUser = userRepository.findByPseudo("utilisateurSupprime");
            deletedUser.addSubscribedChannel(channel);
        }

        for (Integer userId : deletedIds) {
            User user = userRepository.findById(userId).orElse(null);
            if (user != null && channel.getCreator().getIdUser() != user.getIdUser()) {
                channel.getUsers().remove(user);
            }
            if (user != null) {
                user.getSubscribedChannels().remove(channel);
                userRepository.save(user);
            }
        }

        channelRepository.save(channel);

        return ResponseEntity.ok(Map.of("message", "Canal mis à jour")); // ou retourne une 200 JSON si tu veux
    }

    /**
     * Deletes a channel.
     * @param channelId the ID of the channel to delete
     * @return a ResponseEntity indicating the result of the operation
     */
    @PostMapping("/delete")
    public ResponseEntity<?> deleteChannel(@RequestParam int channelId, HttpSession session) {
        Channel channel = channelRepository.findById(channelId);

        User sessionUser = (User) session.getAttribute("user");

        if (channel == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "Canal introuvable"));
        }

        if (channel.getCreator().getIdUser() != sessionUser.getIdUser()) {
            return ResponseEntity.status(403).body(Map.of("message", "Vous n'êtes pas autorisé à supprimer ce canal"));
        }

        // Remove the channel from all users
        for (User user : channel.getUsers()) {
            user.getSubscribedChannels().remove(channel);
            userRepository.save(user);
        }
        // Delete the channel
        channelRepository.delete(channel);
        return ResponseEntity.ok(Map.of("message", "Canal supprimé"));
    }


    /**
     * Leaves a channel.
     * @param data the data containing the channel ID
     * @param session the HTTP session
     * @return a ResponseEntity indicating the result of the operation
     */
    @PostMapping("/leave")
    public ResponseEntity<?> leaveChannel(@RequestBody Map<String, Object> data, HttpSession session) {
        int channelId = (Integer) data.get("channelId");

        Channel channel = channelRepository.findById(channelId);

        User sessionUserTemp = (User) session.getAttribute("user");

        User sessionUser = userRepository.findById(sessionUserTemp.getIdUser()).orElse(null);

        User deletedUser = userRepository.findByPseudo("utilisateurSupprime");

        if (channel == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "Canal introuvable"));
        }

        if (channel.getCreator().getIdUser() == sessionUser.getIdUser()) {
            return ResponseEntity.status(403).body(Map.of("message", "Vous ne pouvez pas quitter le canal que vous avez créé"));
        }

        if (!channel.getUsers().contains(sessionUser)) {
            return ResponseEntity.status(403).body(Map.of("message", "Vous ne faites pas partie de ce canal"));
        }
        // Remove the channel from the user


        // Remove the user from the channel
        sessionUser.removeSubscribedChannel(channel);
        deletedUser.addSubscribedChannel(channel);

        channel.removeUser(sessionUser);

        userRepository.save(sessionUser);
        channelRepository.save(channel);

        return ResponseEntity.ok(Map.of("message", "Vous avez quitté le canal"));
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
