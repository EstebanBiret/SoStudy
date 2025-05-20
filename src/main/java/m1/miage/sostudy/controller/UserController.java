package m1.miage.sostudy.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import m1.miage.sostudy.model.entity.Post;
import m1.miage.sostudy.model.entity.Repost;
import m1.miage.sostudy.model.entity.User;
import m1.miage.sostudy.model.entity.UserPostReaction;
import m1.miage.sostudy.model.entity.dto.RepostDisplay;
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

import static m1.miage.sostudy.controller.AuthController.hashPassword;
import static m1.miage.sostudy.controller.IndexController.*;
import static m1.miage.sostudy.model.entity.User.STUDY_LEVELS;

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
     * Checks if a post media file exists
     * @param mediaPath the path of the media file
     * @return true if the media file exists, false otherwise
     */
    public static boolean postMediaExists(String mediaPath) {
        if (mediaPath == null) return false;
        try {
            return Files.exists(Paths.get("src/main/resources/static/" + mediaPath));
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
            return"redirect:/auth/login";
        }
        model.addAttribute("currentUri", request.getRequestURI());
        return "profile/profile";
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
        if (session.getAttribute("user") == null) {return "redirect:/auth/login";}

        User user = (User) session.getAttribute("user");
        User userProfile = userRepository.findByPseudo(pseudo);

        // user not found
        if (userProfile == null) {return "redirect:/";}

        model.addAttribute("userProfile", userProfile);
        model.addAttribute("currentUri", request.getRequestURI());

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
            return"redirect:/auth/login";
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
        user.setStudyLevel(niveauEtude);
        user.setStudyDomain(studyDomain);
        user.setUniversity(university);

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
     *
     * @param model the model to be used in the view
     * @param session the HTTP session
     * @param pseudo the pseudo of the user to follow
     * @return the name of the follow user view
     */
    @PostMapping("/follow/{pseudo}")
    public String followUser(Model model, HttpSession session, @PathVariable String pseudo) {
        if (session.getAttribute("user") == null) {
            return"redirect:/auth/login";
        }
        User user = (User) session.getAttribute("user");
        User userToFollow = userRepository.findByPseudo(pseudo);
        if (userToFollow == null || userRepository.findByPseudo(pseudo) == null) {
            return "redirect:/";
        }
        // Check if the user is already following the user to follow
        if (user.getFollowing().contains(userToFollow)) {
            // User is already following, do nothing or show a message
            return "redirect:/";
        }
        // Add the user to the following list
        user.addFollowing(userToFollow);
        userToFollow.addFollowers(user);

        // Save the changes to the database
        userRepository.save(user);
        userRepository.save(userToFollow);
        // Update the session attribute
        session.setAttribute("user", user);
        return "redirect:/";
    }

    /**
     * Displays the unfollow user page for a specific user identified by their pseudo.
     * * @param model the model to be used in the view
     * @param session the HTTP session
     * @param pseudo the pseudo of the user to unfollow
     * @return the name of the unfollow user view
     */
    @PostMapping("/unfollow/{pseudo}")
    public String unfollowUser(Model model, HttpSession session, @PathVariable String pseudo) {
        if (session.getAttribute("user") == null) {
            return"redirect:/auth/login";
        }
        User user = (User) session.getAttribute("user");
        User userToFollow = userRepository.findByPseudo(pseudo);
        if (userToFollow == null || userRepository.findByPseudo(pseudo) == null) {
            return "redirect:/";
        }
        // Check if the user is not following the user to unfollow
        if (!user.getFollowing().contains(userToFollow)) {
            // User is not following, do nothing or show a message
            return "redirect:/";
        }
        // Remove the user from the following list
        user.removeFollowing(userToFollow);
        userToFollow.removeFollowers(user);

        // Save the changes to the database
        userRepository.save(user);
        userRepository.save(userToFollow);
        // Update the session attribute
        session.setAttribute("user", user);
        return "redirect:/";
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
        if (session.getAttribute("user") == null) {
            return"redirect:/auth/login";
        }

        User user = (User) session.getAttribute("user");
        List<User> users = userRepository.findByPseudoLike(pseudo);
        if(users.contains(user)){
            users.remove(user);
        }
        List<Integer> nbPost = new ArrayList<>();
        List<Repost> repostsFromUser = new ArrayList<>();
        int nbRepost = 0;
        for(User u : users){
            repostsFromUser = repostRepository.findByUser(u);
            nbPost.add(postRepository.findByUser_IdUserAndCommentFatherIsNull(u.getIdUser()).size() + repostsFromUser.size());
        }

        if(!nbPost.isEmpty()){
          model.addAttribute("nbPost", nbPost);
        }


        model.addAttribute("users", users);
        model.addAttribute("currentUri", request.getRequestURI());
        return "profile/search_profile";
    }

}
