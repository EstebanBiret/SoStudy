package m1.miage.sostudy.controller;

import m1.miage.sostudy.model.entity.Post;
import m1.miage.sostudy.model.entity.Reaction;
import m1.miage.sostudy.model.entity.User;
import m1.miage.sostudy.model.entity.UserPostReaction;
import m1.miage.sostudy.model.entity.id.UserPostReactionID;
import m1.miage.sostudy.model.enums.ReactionType;
import m1.miage.sostudy.repository.ReactionRepository;
import m1.miage.sostudy.repository.UserPostReactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Controller for reactions
 */
@Controller
@RequestMapping("/reaction")
public class ReactionController {

    @Autowired
    private UserPostReactionRepository userPostReactionRepository;
    
    @Autowired
    private ReactionRepository reactionRepository;

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
            return "redirect:/";
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
                return "redirect:/";
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
        return "redirect:/";
    }
}
