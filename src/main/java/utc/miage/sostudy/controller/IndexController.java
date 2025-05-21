package utc.miage.sostudy.controller;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import utc.miage.sostudy.model.entity.Post;
import utc.miage.sostudy.model.entity.Repost;
import utc.miage.sostudy.model.entity.User;
import utc.miage.sostudy.model.entity.id.UserPostReactionID;
import utc.miage.sostudy.model.entity.UserPostReaction;
import utc.miage.sostudy.model.entity.dto.RepostDisplay;
import utc.miage.sostudy.model.enums.ReactionType;
import utc.miage.sostudy.repository.PostRepository;
import utc.miage.sostudy.repository.RepostRepository;
import utc.miage.sostudy.repository.UserPostReactionRepository;

import org.springframework.ui.Model;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Controller for handling requests to the index page.
 * This controller is responsible for displaying the index page of the application.
 */
@Controller
@RequestMapping("/")
public class IndexController {

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
     * Autowired RepostRepository
     */
    @Autowired
    private RepostRepository repostRepository;
    
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
     * Formats the date of a repost
     * @param postDate the date of the repost
     * @return the formatted date of the repost
     */
    public static String formatRepostDate(LocalDate postDate) {
        LocalDate today = LocalDate.now();
        if (postDate.isEqual(today)) {
            return "a reposté aujourd'hui";
        }
    
        long daysBetween = ChronoUnit.DAYS.between(postDate, today);
        if (daysBetween == 1) {
            return "a reposté il y a 1 jour";
        } else {
            return "a reposté il y a " + daysBetween + " jours";
        }
    }

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
     * Counts all comments of a post recursively (comments + replies to comment etc.)
     * @param post the post to count comments for
     * @return the total number of comments
     */
    public static int countAllComments(Post post) {
        if (post.getComments() == null) return 0;
        int total = post.getComments().size();
        for (Post comment : post.getComments()) {
            total += countAllComments(comment); //recursive !
        }
        return total;
    }

    /**
     * Displays the index page.
     *
     * @param request the HttpServletRequest object
     * @param model the Model object
     * @param session the HttpSession object
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
        List<Repost> reposts = new ArrayList<>();

        for (User user2 : abonnements) {
            posts.addAll(postRepository.findByUser_IdUserAndCommentFatherIsNull(user2.getIdUser()));
            reposts.addAll(repostRepository.findByUser(user2));
        }

        // Récupérer toutes les réactions de l'utilisateur
        Map<Integer, ReactionType> userReactedPosts = new HashMap<>();
        for (Post post : posts) {
            // Create the composite ID
            UserPostReactionID reactionId = new UserPostReactionID();
            reactionId.setUserId(user.getIdUser());
            reactionId.setPostId(post.getPostId());
            
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

        //if user has following but they have no posts or reposts
        if (posts.isEmpty() && reposts.isEmpty()) {
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

            post.setLikeCount(likeCount);
            post.setLoveCount(loveCount);
            post.setLaughCount(laughCount);
            post.setCryCount(cryCount);
            post.setAngryCount(angryCount);
        }

        // Map of post media presence
        Map<Integer, Boolean> postMediaExistsMap = new HashMap<>();
        for (Post post : posts) {
            postMediaExistsMap.put(post.getPostId(), postMediaExists(post.getPostMediaPath()));
        }

        //handle repost
        Map<Integer, Boolean> repostedPostIds = new HashMap<>();
        for (Post post : posts) {
            boolean hasReposted = repostRepository.findByUser(user)
                .stream()
                .anyMatch(repost -> repost.getOriginalPost().getPostId().equals(post.getPostId()));
            repostedPostIds.put(post.getPostId(), hasReposted);
        }

        List<RepostDisplay> repostDisplays = new ArrayList<>();

        for (Repost repost : reposts) {

            // Format repost date
            LocalDate repostDate = LocalDate.parse(repost.getRepostDate());
            repost.setFormattedDate(formatRepostDate(repostDate));

            Post original = repost.getOriginalPost();
            
            // Format date
            LocalDate postDate = LocalDate.parse(original.getPostPublicationDate());
            original.setFormattedDate(formatPostDate(postDate));

            // Add reactions
            List<UserPostReaction> reactions = userPostReactionRepository.findByPost_PostId(original.getPostId());
            original.setReactions(reactions);

            long likeCount = reactions.stream()
                .filter(r -> r.getReaction().getReactionType() == ReactionType.LIKE).count();
            long loveCount = reactions.stream()
                .filter(r -> r.getReaction().getReactionType() == ReactionType.LOVE).count();
            long laughCount = reactions.stream()
                .filter(r -> r.getReaction().getReactionType() == ReactionType.LAUGH).count();
            long cryCount = reactions.stream()
                .filter(r -> r.getReaction().getReactionType() == ReactionType.CRY).count();
            long angryCount = reactions.stream()
                .filter(r -> r.getReaction().getReactionType() == ReactionType.ANGRY).count();

            original.setLikeCount(likeCount);
            original.setLoveCount(loveCount);
            original.setLaughCount(laughCount);
            original.setCryCount(cryCount);
            original.setAngryCount(angryCount);

            //check if post media exists
            postMediaExistsMap.put(original.getPostId(), postMediaExists(original.getPostMediaPath()));

            repostDisplays.add(new RepostDisplay(repost, original));

        }

        for (RepostDisplay rd : repostDisplays) {
            Post original = rd.getOriginalPost();
            int postId = original.getPostId();
            //if post is not already in map, add it
            repostedPostIds.putIfAbsent(postId,
                repostRepository.findByUser(user)
                    .stream()
                    .anyMatch(repost -> repost.getOriginalPost().getPostId().equals(postId))
            );
        }
        
        // sort posts by date
        posts.sort((post1, post2) -> {
            LocalDate date1 = LocalDate.parse(post1.getPostPublicationDate());
            LocalDate date2 = LocalDate.parse(post2.getPostPublicationDate());
            return date2.compareTo(date1);
        });

        //count comments for each post
        Map<Integer, Integer> postCommentCounts = new HashMap<>();
        // Count comments for normal posts
        for (Post post : posts) {
            postCommentCounts.put(post.getPostId(), countAllComments(post));
        }
        // Count comments for original posts in reposts
        Set<Integer> originalPostIds = new HashSet<>();
        for (RepostDisplay rd : repostDisplays) {
            Post original = rd.getOriginalPost();
            originalPostIds.add(original.getPostId());
        }
        
        // Get original posts of reposts
        List<Post> originalPosts = postRepository.findAllById(originalPostIds);
        for (Post original : originalPosts) {
            postCommentCounts.put(original.getPostId(), countAllComments(original));
        }

        //count reposts for each post
        Map<Integer, Long> repostCounts = new HashMap<>();
        // Count reposts for normal posts
        for (Post post : posts) {
            long repostCount = repostRepository.countByOriginalPost(post);
            repostCounts.put(post.getPostId(), repostCount);
        }
        // Count reposts for original posts in reposts
        for (Post original : originalPosts) {
            long repostCount = repostRepository.countByOriginalPost(original);
            repostCounts.put(original.getPostId(), repostCount);
        }
        
        model.addAttribute("postMediaExistsMap", postMediaExistsMap);
        model.addAttribute("posts", posts);
        model.addAttribute("user", user);
        model.addAttribute("repostedPostIds", repostedPostIds);
        model.addAttribute("following", "posts");
        model.addAttribute("currentUri", request.getRequestURI());
        model.addAttribute("repostDisplays", repostDisplays);
        model.addAttribute("userReactedPosts", userReactedPosts);
        model.addAttribute("repostCounts", repostCounts);
        model.addAttribute("postCommentCounts", postCommentCounts);

        return "index";
    }

}