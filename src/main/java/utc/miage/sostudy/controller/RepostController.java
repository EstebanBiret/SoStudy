package utc.miage.sostudy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import utc.miage.sostudy.model.entity.Post;
import utc.miage.sostudy.model.entity.Repost;
import utc.miage.sostudy.model.entity.User;
import utc.miage.sostudy.repository.PostRepository;
import utc.miage.sostudy.repository.RepostRepository;

/**
 * RepostController handles repost-related requests.
 */
@Controller
@RequestMapping("/repost")
public class RepostController {
    
    /**
     * The post repository.
     */
    @Autowired
    private PostRepository postRepository;
    
    /**
     * The repost repository.
     */
    @Autowired
    private RepostRepository repostRepository;

    /**
     * Redirect to the home page
     */
    private static final String HOME = "redirect:/";
    
    /**
     * Displays the repost page for a specific post identified by its id.
     * @param postId the id of the post
     * @param session the session of the user
     * @param content the content of the repost
     * @return the name of the repost view
     */
    @PostMapping("/{postId}")
    public String repost(@PathVariable Integer postId, HttpSession session, @RequestParam(required = false) String content) {

        //user not logged in
        if (session.getAttribute("user") == null) {return "redirect:/auth/login";}

        //if post does not exist
        if (postRepository.findById(postId).isEmpty()) {return HOME;}

        //if user has already reposted the post
        User user = (User) session.getAttribute("user");
        if (repostRepository.findByUser(user).stream().anyMatch(repost -> repost.getOriginalPost().getPostId().equals(postId))) {return HOME;}

        Post post = postRepository.findById(postId).get();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Repost repost = new Repost(user, post, LocalDate.now().format(formatter), content);
        repostRepository.save(repost);

        return HOME;
    }

    /**
     * Delete a repost
     * @param postId the id of the post
     * @param session the session of the user
     * @return the name of the view to be rendered
     */
    @PostMapping("/delete/{postId}")
    public String deleteRepost(@PathVariable Integer postId, HttpSession session) {

        //user not logged in
        if (session.getAttribute("user") == null) {return "redirect:/auth/login";}

        //if post does not exist
        if (postRepository.findById(postId).isEmpty()) {return HOME;}

        //if user has not reposted the post
        User user = (User) session.getAttribute("user");
        if (repostRepository.findByUser(user).stream().noneMatch(repost -> repost.getOriginalPost().getPostId().equals(postId))) {return HOME;}

        repostRepository.findByUser(user).stream()
            .filter(r -> r.getOriginalPost().getPostId().equals(postId))
            .findFirst()
            .ifPresent(repostRepository::delete);

        return HOME;
    }

}