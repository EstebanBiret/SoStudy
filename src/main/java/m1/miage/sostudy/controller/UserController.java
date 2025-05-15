package m1.miage.sostudy.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import m1.miage.sostudy.model.entity.Post;
import m1.miage.sostudy.model.entity.User;
import m1.miage.sostudy.model.entity.UserPostReaction;
import m1.miage.sostudy.model.enums.ReactionType;
import m1.miage.sostudy.repository.UserPostReactionRepository;
import m1.miage.sostudy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
        return "profile";
    }

    /**
     * Displays the user profile page for a specific user identified by their pseudo.
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
        for (Post post : userRepository.findByPseudo(pseudo).getCreatedPosts()) {
            posts.add(post);
        }
        for(Post post2 : userRepository.findByPseudo(pseudo).getRepostedPosts()) {
            posts.add(post2);
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

        List<Post> reposts = userRepository.findByPseudo(pseudo).getRepostedPosts();
        model.addAttribute("reposts", reposts);
        model.addAttribute("posts", posts);

        return "profile";
    }

    /**
     * Displays the edit user page.
     * @return the name of the edit user view
     */
    @GetMapping("/edit")
    public String editUser() {
        return "editUser";
    }

    /**
     * Displays the edit user page for a specific user identified by their pseudo.
     * @return the name of the edit user view
     */
    @PostMapping("/edit")
    public String editUserPost() {
        return "redirect:/user";
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
