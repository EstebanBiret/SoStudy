package utc.miage.sostudy.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import utc.miage.sostudy.model.entity.*;
import utc.miage.sostudy.model.entity.dto.RepostDisplay;
import utc.miage.sostudy.model.enums.ReactionType;
import utc.miage.sostudy.repository.*;

import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static utc.miage.sostudy.controller.AuthController.hashPassword;
import static utc.miage.sostudy.controller.IndexController.*;
import static utc.miage.sostudy.model.entity.User.STUDY_LEVELS;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.*;

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
     * Autowired MessageRepository
     */
    @Autowired
    private MessageRepository messageRepository;

    /**
     * Autowired ChannelRepository
     */
    @Autowired
    private ChannelRepository channelRepository;

    /**
     * Autowired CommunityRepository
     */
    @Autowired
    private CommunityRepository communityRepository;

    /**
     * Autowired EventRepository
     */
    @Autowired
    private EventRepository eventRepository;

    /**
     * Redirect to login page
     */
    private String redirectLogin = "redirect:/auth/login";

    /**
     * Redirect to home page
     */
    private String redirectHome = "redirect:/";

    /**
     * Current URI
     */
    private String currentUri = "currentUri";

    /**
     * Deleted user
     */
    private String utilisateurSupprime = "utilisateurSupprime";

    /**
     * Path to static resources
     */
    private static final String IMG_PATH = "src/main/resources/static";

    /**
     * Logger
     */
    private Logger logger = Logger.getLogger(UserController.class.getName());


    /**
     * Checks if a post media file exists
     * @param mediaPath the path of the media file
     * @return true if the media file exists, false otherwise
     */
    public static boolean postMediaExists(String mediaPath) {
        if (mediaPath == null) return false;
        try {
            return Files.exists(Paths.get(IMG_PATH + "/" + mediaPath));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Displays the user profile page.
     * @param model the model to be used in the view
     * @param session the HTTP session
     * @param request the HTTP request
     * @return the name of the user profile view
     */
    @GetMapping("")
    public String user(Model model, HttpSession session, HttpServletRequest request) {
        if (session.getAttribute("user") == null) {
            return redirectLogin;
        }
        model.addAttribute(currentUri, request.getRequestURI());
        return "profile/profile";
    }


    /**
     * Displays the deleted user redirect page.
     *
     * @param model the model to be used in the view
     * @param session the HTTP session
     * @param request the HTTP request
     * @return the name of the deleted user redirect view
     */
    @GetMapping("/deleted-user-redirect")
    public String deletedUserRedirectPage(Model model, HttpSession session, HttpServletRequest request) {
        model.addAttribute(currentUri, request.getRequestURI());
        // user not logged in
        if (session.getAttribute("user") == null) {return redirectLogin;}
        return "deleted_user_redirect"; // Correspond à deleted-user-redirect.html
    }

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

        // user not logged in
        if (session.getAttribute("user") == null) {return redirectLogin;}

        // user is deleted
        if (pseudo.equals(utilisateurSupprime)) {
            return "redirect:/user/deleted-user-redirect";
        }

        User user = (User) session.getAttribute("user");
        User userProfile = userRepository.findByPseudo(pseudo);

        // user not found
        if (userProfile == null) {return redirectHome;}

        model.addAttribute("userProfile", userProfile);
        model.addAttribute(currentUri, request.getRequestURI());

        // --- GET POSTS ---
        List<Post> posts = postRepository.findByUser_IdUserAndCommentFatherIsNull(userProfile.getIdUser());

        for (Post post : posts) {
            // Format date
            LocalDate date = LocalDate.parse(post.getPostPublicationDate());
            post.setFormattedDate(formatPostDate(date));

            // Get reactions
            List<UserPostReaction> reactions = userPostReactionRepository.findByPost_PostId(post.getPostId());
            post.setReactions(reactions);

            // Count reactions
            post.setLikeCount(reactions.stream().filter(r -> r.getReaction().getReactionType() == ReactionType.LIKE).count());
            post.setLoveCount(reactions.stream().filter(r -> r.getReaction().getReactionType() == ReactionType.LOVE).count());
            post.setLaughCount(reactions.stream().filter(r -> r.getReaction().getReactionType() == ReactionType.LAUGH).count());
            post.setCryCount(reactions.stream().filter(r -> r.getReaction().getReactionType() == ReactionType.CRY).count());
            post.setAngryCount(reactions.stream().filter(r -> r.getReaction().getReactionType() == ReactionType.ANGRY).count());
        }

        // Map of post media presence
        Map<Integer, Boolean> postMediaExistsMap = new HashMap<>();
        for (Post post : posts) {
            postMediaExistsMap.put(post.getPostId(), postMediaExists(post.getPostMediaPath()));
        }

        // Map to check if current user has reposted these posts
        Map<Integer, Boolean> repostedPostIds = new HashMap<>();
        List<Repost> currentUserReposts = repostRepository.findByUser(user);
        for (Post post : posts) {
            boolean hasReposted = currentUserReposts.stream()
                .anyMatch(r -> r.getOriginalPost().getPostId().equals(post.getPostId()));
            repostedPostIds.put(post.getPostId(), hasReposted);
        }

        // Sort posts
        posts.sort((p1, p2) -> LocalDate.parse(p2.getPostPublicationDate()).compareTo(LocalDate.parse(p1.getPostPublicationDate())));

        //count comments for each post
        Map<Integer, Integer> postCommentCounts = new HashMap<>();
        for (Post post : posts) {
            postCommentCounts.put(post.getPostId(), countAllComments(post));
        }
        model.addAttribute("postCommentCounts", postCommentCounts);

        //count reposts for each post
        Map<Integer, Long> repostCounts = new HashMap<>();
        for (Post post : posts) {
            long repostCount = repostRepository.countByOriginalPost(post);
            repostCounts.put(post.getPostId(), repostCount);
        }
        
        // --- GET REPOSTS ---
        List<Repost> repostsFromUser = repostRepository.findByUser(userProfile);
        List<RepostDisplay> repostDisplays = new ArrayList<>();
        
        //count reposts for posts in reposts
        for (Repost repost : repostsFromUser) {
            Post originalPost = repost.getOriginalPost();
            long repostCount = repostRepository.countByOriginalPost(originalPost);
            repostCounts.put(originalPost.getPostId(), repostCount);
        }
        
        model.addAttribute("repostCounts", repostCounts);

        for (Repost repost : repostsFromUser) {
            // Format repost date
            LocalDate repostDate = LocalDate.parse(repost.getRepostDate());
            repost.setFormattedDate(formatRepostDate(repostDate));

            Post original = repost.getOriginalPost();

            // Format date of original post
            LocalDate originalDate = LocalDate.parse(original.getPostPublicationDate());
            original.setFormattedDate(formatPostDate(originalDate));

            // Get reactions
            List<UserPostReaction> reactions = userPostReactionRepository.findByPost_PostId(original.getPostId());
            original.setReactions(reactions);

            original.setLikeCount(reactions.stream().filter(r -> r.getReaction().getReactionType() == ReactionType.LIKE).count());
            original.setLoveCount(reactions.stream().filter(r -> r.getReaction().getReactionType() == ReactionType.LOVE).count());
            original.setLaughCount(reactions.stream().filter(r -> r.getReaction().getReactionType() == ReactionType.LAUGH).count());
            original.setCryCount(reactions.stream().filter(r -> r.getReaction().getReactionType() == ReactionType.CRY).count());
            original.setAngryCount(reactions.stream().filter(r -> r.getReaction().getReactionType() == ReactionType.ANGRY).count());

            // Check media
            postMediaExistsMap.put(original.getPostId(), postMediaExists(original.getPostMediaPath()));

            // Check if current user has reposted this original post
            boolean hasReposted = currentUserReposts.stream()
                .anyMatch(r -> r.getOriginalPost().getPostId().equals(original.getPostId()));
            repostedPostIds.put(original.getPostId(), hasReposted);

            repostDisplays.add(new RepostDisplay(repost, original));
        }

        //count comments for each repost
        for (RepostDisplay repostDisplay : repostDisplays) {
            Post originalPost = repostDisplay.getOriginalPost();
            if (!postCommentCounts.containsKey(originalPost.getPostId())) {
                postCommentCounts.put(originalPost.getPostId(), countAllComments(originalPost));
            }
        }

        // Get all reactions of the user
        Map<Integer, ReactionType> userReactedPosts = new HashMap<>();
        
        // For normal posts
        for (Post post : posts) {
            // Get the reaction type from the repository
            List<UserPostReaction> reactions = userPostReactionRepository.findByPost_PostId(post.getPostId());
            if (reactions != null && !reactions.isEmpty()) {
                // Find the reaction for this user
                for (UserPostReaction r : reactions) {
                    if (r.getUser().equals(user)) {
                        userReactedPosts.put(post.getPostId(), r.getReaction().getReactionType());
                        break;
                    }
                }
            }
        }
        
        // For reposts
        for (Repost repost : repostsFromUser) {
            Post originalPost = repost.getOriginalPost();
            if (originalPost != null) {
                List<UserPostReaction> reactions = userPostReactionRepository.findByPost_PostId(originalPost.getPostId());
                if (reactions != null && !reactions.isEmpty()) {
                    for (UserPostReaction r : reactions) {
                        if (r.getUser().equals(user)) {
                            userReactedPosts.put(originalPost.getPostId(), r.getReaction().getReactionType());
                            break;
                        }
                    }
                }
            }
        }
        
        //add attributes to model
        model.addAttribute("posts", posts);
        model.addAttribute("reposts", repostsFromUser.stream().map(Repost::getOriginalPost).toList());
        model.addAttribute("repostDisplays", repostDisplays);
        model.addAttribute("postMediaExistsMap", postMediaExistsMap);
        model.addAttribute("repostedPostIds", repostedPostIds);
        model.addAttribute("userReactedPosts", userReactedPosts);

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
            return redirectLogin;
        }
        model.addAttribute("niveauxEtude", STUDY_LEVELS);
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
     * @param niveauEtude the study level of the user
     * @param studyDomain the study domain of the user
     * @param university the university of the user
     * @return a redirect to the user profile page
     * @throws IOException if an I/O error occurs
     */
    @PostMapping("/edit")
    public String editUserPost(Model model, HttpSession session, @RequestParam String nom, @RequestParam String prenom,
                               @RequestParam String pseudo,
                               @RequestParam String password,
                               @RequestParam String birthdate,
                               @RequestParam String bioUser,
                               @RequestParam MultipartFile image,
                               @RequestParam String niveauEtude,
                               @RequestParam String studyDomain,
                               @RequestParam String university) throws IOException {

        if (session.getAttribute("user") == null) {return redirectLogin;}

        User user = (User) session.getAttribute("user");
        user.setName(nom);
        user.setFirstName(prenom);
        user.setPseudo(pseudo);
        user.setPassword(hashPassword(password));
        user.setBirthDate(birthdate);
        user.setBioUser(bioUser);
        user.setStudyLevel(niveauEtude);
        user.setStudyDomain(studyDomain);
        user.setUniversity(university);

        if (!image.isEmpty()) {
            // Supprimer l'ancienne image si elle n'est pas la photo par défaut
            String oldImagePath = user.getPersonImagePath();
            if (oldImagePath != null && !oldImagePath.contains("defaultProfilePic.jpg")) {
                Path oldImageFilePath = Paths.get(IMG_PATH + oldImagePath);
                try {
                    Files.deleteIfExists(oldImageFilePath);
                } catch (IOException e) {
                    logger.info("Erreur lors de la suppression de l'ancienne image : " + e.getMessage());
                }
            }

            // Ajouter la nouvelle image
            String rawFileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
            Path filePath = Paths.get(IMG_PATH + "/images/profiles_pictures", rawFileName);
            Files.createDirectories(filePath.getParent());
            Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            String fileName = "/images/profiles_pictures/" + rawFileName;
            user.setPersonImagePath(fileName);
        }

        userRepository.save(user);
        session.setAttribute("user", user);

        // Add a small delay using a temporary redirect
        return "redirect:/user/temporary-redirect/" + user.getPseudo();
    }

    /**
     * Temporary redirect to ensure the image is properly saved
     * @param pseudo the pseudo of the user
     * @return redirect to user profile
     * @throws InterruptedException if the thread is interrupted
     */
    @GetMapping("/temporary-redirect/{pseudo}")
    public String temporaryRedirect(@PathVariable String pseudo) throws InterruptedException {
        // Wait for 1 second to ensure the image is properly saved
        Thread.sleep(1000);
        return "redirect:/user/" + pseudo;
    }

    /**
     * Handles the deletion of the user.
     * @param model the model to be used in the view
     * @param session the HTTP session
     * @return the name of the delete user view
     */
    @PostMapping("/delete")
    public String deleteUser(Model model, HttpSession session) {
        if (session.getAttribute("user") == null) {return redirectLogin;}

        // Get the user from the session
        User user = (User) session.getAttribute("user");

        //Get the deleted user
        User deletedUser = userRepository.findByPseudo(utilisateurSupprime);

        //suppression de tous les messages
        List<Message> messages = messageRepository.findBySender(user);
        for (Message message : messages) {
            message.setUserId(deletedUser.getIdUser());
            message.setSender(deletedUser);
            messageRepository.save(message);
        }

        //suppression de tous les canaux
        List<Channel> channels = channelRepository.findByCreator(user);
        for (Channel channel : channels) {
            if (channel.getUsers().contains(user)) {
                user.removeSubscribedChannel(channel);
                deletedUser.addSubscribedChannel(channel);
            }
            channel.setCreator(deletedUser);
            userRepository.save(deletedUser);
            userRepository.save(user);
            channelRepository.save(channel);
        }

        //suppression de tous les follow et followers
        List<User> followers = user.getFollowers();
        for (User follower : followers) {
            follower.removeFollowing(user);
            userRepository.save(follower);
        }

        List<User> following = user.getFollowing();
        for (User followed : following) {
            followed.removeFollowers(user);
            userRepository.save(followed);
        }

        //suppression de toutes les communautés
        List<Community> communities = communityRepository.findByCreator(user);
        for (Community community : communities) {
            community.setUserCreator(deletedUser);
            communityRepository.save(community);
        }

        communities.clear();
        communities = communityRepository.findByUsers_IdUser(user.getIdUser());
        for (Community community : communities) {
            community.removeUser(user);
            communityRepository.save(community);
        }

        //suppression des reactions
        List<UserPostReaction> reactions = userPostReactionRepository.findByUser_IdUser(user.getIdUser());
        for (UserPostReaction reaction : reactions) {
            userPostReactionRepository.delete(reaction);
        }

        //suppression de tous les reposts
        List<Repost> repostsFromUser = repostRepository.findByUser(user);
        for (Repost repost : repostsFromUser) {
            repostRepository.delete(repost);
        }

        //suppression de tous les posts
        List<Post> posts = postRepository.findByUser_IdUser(user.getIdUser());
        for (Post post : posts) {
            // suppression de tous les commentaires
            List<Post> comments = postRepository.findByCommentFather_PostId(post.getPostId());
            for (Post comment : comments) {
                postRepository.delete(comment);
            }
            // suppression de tous les reposts du post
            List<Repost> reposts = repostRepository.findByOriginalPost(post);
            for (Repost repost : reposts) {
                repostRepository.delete(repost);
            }
            // suppression de toutes les reactions du post
            List<UserPostReaction> postReactions = userPostReactionRepository.findByPost_PostId(post.getPostId());
            for (UserPostReaction postReaction : postReactions) {
                userPostReactionRepository.delete(postReaction);
            }
            // suppression du media du post
            String mediaPath = post.getPostMediaPath();
            if (mediaPath != null) {
                Path mediaFilePath = Paths.get(IMG_PATH + mediaPath);
                try {
                    Files.deleteIfExists(mediaFilePath);
                } catch (IOException e) {
                    logger.info("Erreur lors de la suppression du fichier média : " + e.getMessage());
                }
            }
            // suppression du post
            postRepository.delete(post);
        }

        //suppression de tous les reposts de l'utilisateur
        List<Repost> reposts = repostRepository.findByUser(user);
        repostRepository.deleteAll(reposts);

        //suppression de tous les evenements
        List<Event> events = eventRepository.findByUserCreator(user);
        for (Event event : events) {
            event.setUserCreator(deletedUser);
            eventRepository.save(event);
        }

        //suppression de toute les participations aux evenements
        List<Event> eventsParticipated = eventRepository.findByUserParticipant(user);
        for (Event event : eventsParticipated) {
            event.removeUser(user);
            eventRepository.save(event);
        }

        //suppression de la photo de profil si pas celle par defaut
        String imagePath = user.getPersonImagePath();
        if (imagePath != null && !imagePath.contains("defaultProfilePic.jpg")) {
            Path imageFilePath = Paths.get(IMG_PATH + imagePath);
            try {
                Files.deleteIfExists(imageFilePath);
            } catch (IOException e) {
                logger.info("Erreur lors de la suppression de l'image : " + e.getMessage());
            }
        }

        userRepository.findByPseudo(user.getPseudo());

        // Delete the user from the database
        userRepository.delete(user);
        // Invalidate the session
        session.invalidate();
        return redirectLogin;
    }

    /**
     * Handles the follow user action for a specific user identified by their pseudo.
     *
     * @param model the model to be used in the view
     * @param session the HTTP session
     * @param pseudo the pseudo of the user to follow
     * @return the name of the follow user view
     */
    @PostMapping("/follow/{pseudo}")
    public String followUser(Model model, HttpSession session, @PathVariable String pseudo) {

        if (session.getAttribute("user") == null) {return redirectLogin;}

        User user = (User) session.getAttribute("user");
        User userToFollow = userRepository.findByPseudo(pseudo);
        user = userRepository.findByPseudo(user.getPseudo());
        if (userToFollow == null || userRepository.findByPseudo(pseudo) == null) {
            return redirectHome;
        }
        // Check if the user is already following the user to follow
        if (user.getFollowing().contains(userToFollow)) {
            // User is already following, do nothing or show a message
            return redirectHome;
        }
        // Add the user to the following list
        user.addFollowing(userToFollow);
        userToFollow.addFollowers(user);

        // Save the changes to the database
        userRepository.save(user);
        userRepository.save(userToFollow);
        // Update the session attribute
        session.setAttribute("user", user);
        return redirectHome;
    }

    /**
     * Handles the unfollow user action for a specific user identified by their pseudo.
     * @param model the model to be used in the view
     * @param session the HTTP session
     * @param pseudo the pseudo of the user to unfollow
     * @return the name of the unfollow user view
     */
    @PostMapping("/unfollow/{pseudo}")
    public String unfollowUser(Model model, HttpSession session, @PathVariable String pseudo) {

        if (session.getAttribute("user") == null) {return redirectLogin;}

        User user = (User) session.getAttribute("user");
        User userToFollow = userRepository.findByPseudo(pseudo);
        if (userToFollow == null || userRepository.findByPseudo(pseudo) == null) {
            return redirectHome;
        }
        // Check if the user is not following the user to unfollow
        if (!user.getFollowing().contains(userToFollow)) {
            return redirectHome;
        }
        // Remove the user from the following list
        user.removeFollowing(userToFollow);
        userToFollow.removeFollowers(user);

        // Save the changes to the database
        userRepository.save(user);
        userRepository.save(userToFollow);
        // Update the session attribute
        session.setAttribute("user", user);
        return redirectHome;
    }

    /**
     * search for users by pseudo
     *
     * @param pseudo the pseudo of the user to search for
     * @param model the model to be used in the view
     * @param session the HTTP session
     * @param request the HTTP request
     * @return the list of users found
     */
    @GetMapping("/search/{pseudo}")
    public String searchUser(@PathVariable String pseudo, Model model, HttpSession session, HttpServletRequest request) {

        if (session.getAttribute("user") == null) {return redirectLogin;}

        User user = (User) session.getAttribute("user");
        List<User> users = userRepository.findByPseudoLike(pseudo);
        if(users.contains(user)){
            users.remove(user);
        }

        if(users.contains(userRepository.findByPseudo(utilisateurSupprime))){
            users.remove(userRepository.findByPseudo(utilisateurSupprime));
        }

        List<Integer> nbPost = new ArrayList<>();
        List<Repost> repostsFromUser;
        for(User u : users){
            repostsFromUser = repostRepository.findByUser(u);
            nbPost.add(postRepository.findByUser_IdUserAndCommentFatherIsNull(u.getIdUser()).size() + repostsFromUser.size());
        }

        if(!nbPost.isEmpty()){model.addAttribute("nbPost", nbPost);}

        model.addAttribute("recherche", pseudo);
        model.addAttribute("users", users);
        model.addAttribute(currentUri, request.getRequestURI());
        return "profile/search_profile";
    }

}
