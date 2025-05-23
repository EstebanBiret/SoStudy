package utc.miage.sostudy.controller;

import utc.miage.sostudy.model.entity.Post;
import utc.miage.sostudy.model.entity.Reaction;
import utc.miage.sostudy.model.entity.User;
import utc.miage.sostudy.model.entity.UserPostReaction;
import utc.miage.sostudy.model.entity.id.UserPostReactionID;
import utc.miage.sostudy.model.enums.ReactionType;
import utc.miage.sostudy.repository.ReactionRepository;
import utc.miage.sostudy.repository.UserPostReactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import java.util.List;

/**
 * Controller for reactions
 */
@Controller
@RequestMapping("/reaction")
public class ReactionController {

    /**
     * Repository for user post reactions
     */
    @Autowired
    private UserPostReactionRepository userPostReactionRepository;
    
    /**
     * Repository for reactions
     */
    @Autowired
    private ReactionRepository reactionRepository;

    /**
     * Redirect to the home page
     */
    private static final String HOME = "redirect:/";

    /**
     * Adds, modifies or deletes a reaction for a user on a post
     * @param postId the ID of the post
     * @param reactionType the type of reaction
     * @param session the session of the user
     * @return the redirect chain
     */
    @PostMapping("/update/{postId}/{reactionType}")
    public String updateReaction(@PathVariable Integer postId,
                             @PathVariable String reactionType,
                             HttpSession session) {
        // Check if the user is logged in
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/auth/login";
        }

        // Check if the reaction type is valid
        Reaction reaction = reactionRepository.findByReactionType(ReactionType.valueOf(reactionType));
        if (reaction == null) {
            return HOME;
        }

        // Find all reactions for this user and post
        List<UserPostReaction> reactions = userPostReactionRepository.findByPost_PostId(postId);
        UserPostReaction existingReaction = null;
        
        // Find the specific reaction for this user
        for (UserPostReaction r : reactions) {
            if (r.getUser().equals(user)) {
                existingReaction = r;
                break;
            }
        }

        if (existingReaction != null) {
            // If the user clicks on the same reaction they already have
            if (existingReaction.getReaction().getReactionType() == reaction.getReactionType()) {
                // Delete the existing reaction
                userPostReactionRepository.delete(existingReaction);
                return HOME;
            }
            // Otherwise, delete the existing reaction and create a new one
            userPostReactionRepository.delete(existingReaction);
        }
        
        // Create a new reaction
        UserPostReaction newReaction = new UserPostReaction();
        newReaction.setUser(user);
        
        // Create and initialize the Post object
        Post post = new Post();
        post.setPostId(postId);
        newReaction.setPost(post);
        
        newReaction.setReaction(reaction);
        
        // Create the composite ID
        UserPostReactionID reactionId = new UserPostReactionID();
        reactionId.setUserId(user.getIdUser());
        reactionId.setPostId(postId);
        reactionId.setReactionId(reaction.getReactionId());
        newReaction.setId(reactionId);
        
        userPostReactionRepository.save(newReaction);
        return HOME;
    }
    
}