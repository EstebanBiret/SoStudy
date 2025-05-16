package m1.miage.sostudy.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import m1.miage.sostudy.model.entity.Post;
import m1.miage.sostudy.model.entity.Repost;
import m1.miage.sostudy.model.entity.User;
import m1.miage.sostudy.model.entity.UserPostReaction;
import m1.miage.sostudy.model.enums.ReactionType;
import m1.miage.sostudy.repository.PostRepository;
import m1.miage.sostudy.repository.RepostRepository;
import m1.miage.sostudy.repository.UserPostReactionRepository;
import m1.miage.sostudy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static m1.miage.sostudy.controller.AuthController.hashPassword;
import static m1.miage.sostudy.controller.IndexController.formatPostDate;

/**
 * UserController handles user-related requests.
 * It provides endpoints for viewing, editing, deleting, and following/unfollowing users.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    /**
     * Autowired UserPostReactionRepository
     */
    @Autowired
    private UserPostReactionRepository userPostReactionRepository;

    /**
     * Autowired RepostRepository
     */
    @Autowired
    private RepostRepository repostRepository;

    /**
     * Autowired PostRepository
     */
    @Autowired
    private PostRepository postRepository;

    /**
     * Path for uploading files.
     */
    private final String UPLOAD_DIR = "./src/main/resources/static/images/profiles_pictures/";

    /**
     * Displays the user profile page for a specific user identified by their pseudo.
     * @param pseudo the pseudo of the user
     * @param model the model to be used in the view
     * @param session the HTTP session
     * @param request the HTTP request
     * @return the name of the user profile view
     */
    @GetMapping("/{pseudo}")
    public String userByPseudo(@PathVariable String pseudo, Model model, HttpSession session, HttpServletRequest request) {
        if (session.getAttribute("user") == null) {
            return"redirect:/auth/login";
        }

        User userProfile = userRepository.findByPseudo(pseudo);
        if (userProfile == null) {
            return "redirect:/";
        }
        model.addAttribute("userProfile", userProfile);
        model.addAttribute("currentUri", request.getRequestURI());

        List<Post> posts = new ArrayList<>();
        for (Post post : postRepository.findByUser_IdUser(userProfile.getIdUser())) {
            posts.add(post);
        }
        for(Repost repost : repostRepository.findByUser(userProfile)) {
            posts.add(repost.getOriginalPost());
        }

        //format date
        for (Post post : posts) {
            LocalDate date = LocalDate.parse(post.getPostPublicationDate());
            post.setFormattedDate(formatPostDate(date));
        }

        // add reactions
        for (Post post : posts) {
            List<UserPostReaction> reactions = userPostReactionRepository.findByPost_PostId(post.getPostId());
            post.setReactions(reactions);

            // count reactions by type
            long likeCount = reactions.stream()
                    .filter(r -> r.getReaction().getReactionType() == ReactionType.LIKE)
                    .count();
            long loveCount = reactions.stream()
                    .filter(r -> r.getReaction().getReactionType() == ReactionType.LOVE)
                    .count();
            long laughCount = reactions.stream()
                    .filter(r -> r.getReaction().getReactionType() == ReactionType.LAUGH)
                    .count();
            long cryCount = reactions.stream()
                    .filter(r -> r.getReaction().getReactionType() == ReactionType.CRY)
                    .count();
            long angryCount = reactions.stream()
                    .filter(r -> r.getReaction().getReactionType() == ReactionType.ANGRY)
                    .count();

            // store counters in post
            post.setLikeCount(likeCount);
            post.setLoveCount(loveCount);
            post.setLaughCount(laughCount);
            post.setCryCount(cryCount);
            post.setAngryCount(angryCount);
        }

        // sort posts by date
        posts.sort((post1, post2) -> {
            LocalDate date1 = LocalDate.parse(post1.getPostPublicationDate());
            LocalDate date2 = LocalDate.parse(post2.getPostPublicationDate());
            return date2.compareTo(date1);
        });

        List<Post> reposts = new ArrayList<>();
        for(Repost repost : repostRepository.findByUser(userProfile)) {
            reposts.add(repost.getOriginalPost());
        }
        model.addAttribute("reposts", reposts);
        model.addAttribute("posts", posts);

        return "profile/profile";
    }

    /**
     * Displays the edit user page.
     * @param model the model to be used in the view
     * @param session the HTTP session
     * @param request the HTTP request
     * @return the name of the edit user view
     */
    @GetMapping("/edit")
    public String editUser(Model model, HttpSession session, HttpServletRequest request) {
        if (session.getAttribute("user") == null) {
            return"redirect:/auth/login";
        }
        return "profile/form_edit_profile";
    }


    /**
     * Handles the submission of the edit user form.
      * @param model the model to be used in the view
     * @param session the HTTP session
     * @param nom the name of the user
     * @param prenom the first name of the user
     * @param pseudo the pseudo of the user
     * @param password the password of the user
     * @param birthdate the date of birth of the user
     * @param bioUser the bio of the user
     * @param image the image of the user
     * @return a redirect to the user profile page
     * @throws IOException if an I/O error occurs
     */
    @PostMapping("/edit")
    public String editUserPost(Model model, HttpSession session, @RequestParam String nom, @RequestParam String prenom,
                               @RequestParam String pseudo,
                               @RequestParam String password,
                               @RequestParam String birthdate,
                               @RequestParam String bioUser,
                               @RequestParam MultipartFile image) throws IOException {

        if (session.getAttribute("user") == null) {
            return "redirect:/auth/login";
        }

        User user = (User) session.getAttribute("user");
        user.setName(nom);
        user.setFirstName(prenom);
        user.setPseudo(pseudo);
        user.setPassword(hashPassword(password));
        user.setBirthDate(birthdate);
        user.setBioUser(bioUser);

        if (!image.isEmpty()) {
            // Supprimer l'ancienne image si elle n'est pas la photo par défaut
            String oldImagePath = user.getPersonImagePath();
            if (oldImagePath != null && !oldImagePath.contains("defaultProfilePic.jpg")) {
                Path oldImageFilePath = Paths.get("src/main/resources/static" + oldImagePath);
                try {
                    Files.deleteIfExists(oldImageFilePath);
                } catch (IOException e) {
                    System.err.println("Erreur lors de la suppression de l'ancienne image : " + e.getMessage());
                }
            }

            // Ajouter la nouvelle image
            String rawFileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
            Path filePath = Paths.get("src/main/resources/static/images/profiles_pictures", rawFileName);
            Files.createDirectories(filePath.getParent());
            Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            String fileName = "/images/profiles_pictures/" + rawFileName;
            user.setPersonImagePath(fileName);
        }

        userRepository.save(user);
        session.setAttribute("user", user);
        return "redirect:/user/" + user.getPseudo();
    }


    /**
     * Displays the delete user page.
     * @return the name of the delete user view
     */
    @PostMapping("/delete")
    public String deleteUser() {
        return "redirect:/";
    }

    /**
     * Displays the follow user page for a specific user identified by their pseudo.
     * @return the name of the follow user view
     */
    @PostMapping("/follow/{pseudo}")
    public String followUser() {
        return "redirect:/user";
    }

    /**
     * Displays the unfollow user page for a specific user identified by their pseudo.
     * @return the name of the unfollow user view
     */
    @PostMapping("/unfollow/{pseudo}")
    public String unfollowUser() {
        return "redirect:/user";
    }


}
