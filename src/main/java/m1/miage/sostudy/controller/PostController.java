package m1.miage.sostudy.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;

import m1.miage.sostudy.model.entity.Community;
import m1.miage.sostudy.model.entity.Post;
import m1.miage.sostudy.model.entity.User;
import m1.miage.sostudy.model.entity.UserPostReaction;
import m1.miage.sostudy.repository.PostRepository;
import m1.miage.sostudy.repository.UserPostReactionRepository;
import m1.miage.sostudy.repository.RepostRepository;
import m1.miage.sostudy.repository.CommunityRepository;
import java.util.HashMap;
import java.util.List;

import m1.miage.sostudy.model.enums.ReactionType;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import static m1.miage.sostudy.controller.IndexController.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.HashMap;

/**
 * Controller for the post
 */
@Controller
@RequestMapping("/post")
public class PostController {

    /**
     * The post repository.
     */
    @Autowired
    private PostRepository postRepository;
    
    /**
     * The reaction repository.
     */
    /**
     * The user post reaction repository.
     */
    @Autowired
    private UserPostReactionRepository userPostReactionRepository;
    
    /**
     * The community repository.
     */
    @Autowired
    private CommunityRepository communityRepository;
    
    /**
     * The repost repository.
     */
    @Autowired
    private RepostRepository repostRepository;

    /**
     * Path for uploading files.
     */
    private final String UPLOAD_DIR = "./src/main/resources/static/images/posts_images/";

    /**
     * Redirect to home page if the post id is empty
     */
    @GetMapping("")
    public String getPostEmpty() {return "redirect:/";}

    /**
     * Redirect to home page if the post id is empty
     */
    @GetMapping("/")
    public String getPostEmpty2() {return "redirect:/";}

    /**
     * Get a post by id
     * @param id the id of the post
     * @param request the HttpServletRequest object
     * @param model the Model object
     * @param session the HttpSession object
     * @return the post details page
     */
    @GetMapping("/{id}")
    public String getPost(@PathVariable Integer id, HttpServletRequest request, Model model, HttpSession session) {
        
        //user not logged in
        if(session.getAttribute("user") == null) {return "redirect:/auth/login";}

        //post not found
        if(postRepository.findById(id).isEmpty()) {return "redirect:/";}

        //if the post is a comment, redirect
        if(postRepository.findById(id).get().getCommentFather() != null) {return "redirect:/";}

        model.addAttribute("currentUri", request.getRequestURI());

        // Get the post and the actuel user
        Post post = postRepository.findById(id).get();
        User currentUser = (User) session.getAttribute("user");
        
        // Format post date
        LocalDate postDate = LocalDate.parse(post.getPostPublicationDate());
        post.setFormattedDate(formatPostDate(postDate));
        
        // Get comments for this post (direct comments only)
        List<Post> comments = postRepository.findByCommentFather_PostIdAndCommentFatherIsNull(id);
        
        // Count comments
        int commentsCount = countAllComments(post);
        
        // Count reposts
        long repostCount = repostRepository.countByOriginalPost(post);
        
        // Get reactions
        List<UserPostReaction> reactions = userPostReactionRepository.findByPost_PostId(post.getPostId());
        post.setReactions(reactions);
        
        // Count reactions by type
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
        
        // Store counters in post
        post.setLikeCount(likeCount);
        post.setLoveCount(loveCount);
        post.setLaughCount(laughCount);
        post.setCryCount(cryCount);
        post.setAngryCount(angryCount);
        
        // Check if current user has reposted this post
        boolean hasReposted = repostRepository.findByUser(currentUser).stream().anyMatch(r -> r.getOriginalPost().getPostId().equals(post.getPostId()));

        // Add all data to model
        model.addAttribute("post", post);
        model.addAttribute("comments", comments);
        model.addAttribute("postCommentCounts", Map.of(post.getPostId(), commentsCount));
        model.addAttribute("repostCounts", repostCount);
        model.addAttribute("repostedPostIds", Map.of(post.getPostId(), hasReposted));
        model.addAttribute("commentsCount", commentsCount);
        model.addAttribute("hasReposted", hasReposted);
        
        return "post/details";
    }

    /**
     * Displays the form to publish a new post.
     *
     * @param request the HttpServletRequest object
     * @param model the Model object
     * @param session the HttpSession object
     * @return the name of the view to be rendered
     */
    @GetMapping("/publish")
    public String publishPost(HttpServletRequest request, Model model, HttpSession session) {
        //user not logged in
        if(session.getAttribute("user") == null) {return "redirect:/auth/login";}
        
        User user = (User) session.getAttribute("user");

        //get user communities
        List<Community> userCommunities = communityRepository.findByUsers_IdUser(user.getIdUser());
        model.addAttribute("userCommunities", userCommunities);
        model.addAttribute("currentUri", request.getRequestURI());
        return "post/form_publish";
    }

    /**
     * Publish a new post.
     * @param post the post to publish
     * @param communityId the id of the community
     * @param image the image of the post
     * @param request the HttpServletRequest object
     * @param session the HttpSession object
     * @return the redirect URL
     * @throws IOException if an I/O error occurs
     */
    @PostMapping("/publish")
    public String publishPost(@ModelAttribute Post post,
        @RequestParam(required = false) Integer communityId,
        @RequestParam("postMedia") MultipartFile image,
        HttpServletRequest request, HttpSession session) throws IOException {

        //user not logged in
        if(session.getAttribute("user") == null) {return "redirect:/auth/login";}
        
        User user = (User) session.getAttribute("user");

        //set user and publication date now
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        post.setUser(user);
        post.setPostPublicationDate(LocalDate.now().format(formatter));
        
        // If a community is selected, get it and set it in the post
        if (communityId != null) {
            Community community = communityRepository.findById(communityId).orElse(null);
            if (community != null) {
                post.setCommunity(community);
            }
        }
    
        // if we have attached an image to the post, we save it
        //TODO si j'ai le temps mettre une taille max de l'image sinon erreur 413
        String fileName = null;
        if (!image.isEmpty()) {
            String rawFileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR, rawFileName);
            Files.createDirectories(filePath.getParent());
            Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            fileName = "/images/posts_images/" + rawFileName;
            post.setPostMediaPath(fileName);
        }

        postRepository.save(post);
        return "redirect:/";
    }

    /**
     * Delete a post
     * @param id the id of the post
     * @param request the HttpServletRequest object
     * @param session the HttpSession object
     * @return the home page (feed)
     */
    @PostMapping("/delete/{id}")
    public String deletePost(@PathVariable Integer id, HttpServletRequest request, HttpSession session) {
        //user not logged in
        if(session.getAttribute("user") == null) {return "redirect:/auth/login";}
        
        //post not found
        if(postRepository.findById(id).isEmpty()) {return "redirect:/";}
        
        //delete post
        postRepository.deleteById(id);
        return "redirect:/";
    }
    
}