package m1.miage.sostudy.controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import m1.miage.sostudy.model.entity.Post;
import m1.miage.sostudy.model.entity.User;
import m1.miage.sostudy.model.entity.UserPostReaction;
import m1.miage.sostudy.model.enums.ReactionType;
import m1.miage.sostudy.repository.PostRepository;
import m1.miage.sostudy.repository.UserPostReactionRepository;
import m1.miage.sostudy.repository.UserRepository;

import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Controller for handling requests to the index page.
 * This controller is responsible for displaying the index page of the application.
 */
@Controller
@RequestMapping("/")
public class IndexController {
    
    /**
     * Autowired UserRepository
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Autowired UserPostReactionRepository
     */
    @Autowired
    private UserPostReactionRepository userPostReactionRepository;

    /**
     * Autowired PostRepository
     */
    @Autowired
    private PostRepository postRepository;
    
    /**
     * Formats the date of a post
     * @param postDate the date of the post
     * @return the formatted date of the post
     */
    public static String formatPostDate(LocalDate postDate) {
        LocalDate today = LocalDate.now();
        if (postDate.isEqual(today)) {
            return "a posté aujourd'hui";
        }
    
        long daysBetween = ChronoUnit.DAYS.between(postDate, today);
        if (daysBetween == 1) {
            return "a posté il y a 1 jour";
        } else {
            return "a posté il y a " + daysBetween + " jours";
        }
    }

    /**
     * Displays the index page.
     *
     * @param request the HttpServletRequest object
     * @param model the Model object
     * @return the name of the view to be rendered
     */
    @GetMapping("/")
    public String index(HttpServletRequest request, Model model, HttpSession session) {

        //user not logged in
        if(session.getAttribute("user") == null) {return "redirect:/auth/login";}

        User user = (User) session.getAttribute("user");

        List<User> abonnements = user.getFollowing();

        //user has no following
        if(abonnements.isEmpty()) {
            model.addAttribute("posts", new ArrayList<>());
            model.addAttribute("user", user);
            model.addAttribute("currentUri", request.getRequestURI());
            model.addAttribute("following", "empty");
            return "index";
        }
        
        List<Post> posts = new ArrayList<>();
        
        for (User user2 : abonnements) {
            posts.addAll(postRepository.findByUser_IdUser(user2.getIdUser()));
        }

        //if user has following but they have no posts
        if (posts.isEmpty()) {
            model.addAttribute("posts", posts);
            model.addAttribute("user", user);
            model.addAttribute("currentUri", request.getRequestURI());
            model.addAttribute("following", "no_posts");
            return "index";
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
        
        model.addAttribute("posts", posts);
        model.addAttribute("user", user);
        model.addAttribute("currentUri", request.getRequestURI());

        return "index";
    }

}