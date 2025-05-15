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
    public String formatPostDate(LocalDate postDate) {
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
        model.addAttribute("currentUri", request.getRequestURI());

        //TODO retirer quand flavien aura mis en place login logout register ------
        User user = userRepository.findById(1).get();
        session.setAttribute("user", user);

        List<User> abonnements = user.getFollowers();
        List<Post> posts = new ArrayList<>();
        
        for (User user2 : abonnements) {
            posts.addAll(postRepository.findByUser_IdUser(user2.getIdUser()));
        }
        
        // Formatte les dates des posts
        for (Post post : posts) {
            LocalDate date = LocalDate.parse(post.getPostPublicationDate());
            post.setFormattedDate(formatPostDate(date));
        }

        // Ajouter les réactions pour chaque post
        for (Post post : posts) {
            post.setReactions(userPostReactionRepository.findByPost_PostId(post.getPostId()));
        }

        // Tri des posts pour avoir les plus récents en premier
        posts.sort((post1, post2) -> {
            LocalDate date1 = LocalDate.parse(post1.getPostPublicationDate());
            LocalDate date2 = LocalDate.parse(post2.getPostPublicationDate());
            return date2.compareTo(date1); 
        });
        
        model.addAttribute("posts", posts);
        model.addAttribute("user", user);

        //--------------------

        //if(session.getAttribute("user") == null) {return "redirect:/auth/login";}
        return "index";
    }

}