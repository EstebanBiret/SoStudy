package utc.miage.sostudy.model.entity;

import java.util.Objects;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import utc.miage.sostudy.model.entity.id.UserPostReactionID;

/**
 * Class representing a user post reaction
 */
@Entity
@Table(name = "user_post_reaction")
public class UserPostReaction {

    /**
     * The id of the user post reaction
     */
    @EmbeddedId
    private UserPostReactionID id;
    
    /**
     * The user who reacted to the post
     */
    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    /**
     * The post that was reacted to
     */
    @ManyToOne
    @MapsId("postId")
    @JoinColumn(name = "post_id", insertable = false, updatable = false)
    private Post post;

    /**
     * The reaction that was given
     */
    @ManyToOne
    @MapsId("reactionId")
    @JoinColumn(name = "reaction_id", insertable = false, updatable = false)
    private Reaction reaction;
    
    /**
     * Default constructor
     */
    public UserPostReaction() {}
    
    /**
     * Constructor with parameters
     * @param user the user
     * @param post the post
     * @param reaction the reaction
     */
    public UserPostReaction(User user, Post post, Reaction reaction) {
        this.user = user;
        this.post = post;
        this.reaction = reaction;
        this.id = new UserPostReactionID(user.getIdUser(), post.getPostId(), reaction.getReactionId());
    }
    
    /**
     * Getter for the id
     * @return the id
     */
    public UserPostReactionID getId() {
        return id;
    }

    /**
     * Setter for the id
     * @param id the id
     */
    public void setId(UserPostReactionID id) {
        this.id = id;
    }

    /**
     * Getter for the user
     * @return the user
     */
    public User getUser() {
        return user;
    }
    
    /**
     * Setter for the user
     * @param user the user
     */
    public void setUser(User user) {
        this.user = user;
    }
    
    /**
     * Getter for the post
     * @return the post
     */
    public Post getPost() {
        return post;
    }
    
    /**
     * Setter for the post
     * @param post the post
     */
    public void setPost(Post post) {
        this.post = post;
    }
    
    /**
     * Getter for the reaction
     * @return the reaction
     */
    public Reaction getReaction() {
        return reaction;
    }
    
    /**
     * Setter for the reaction
     * @param reaction the reaction
     */
    public void setReaction(Reaction reaction) {
        this.reaction = reaction;
    }
    
    /**
     * Redefinition of the hashcode method
     * @return the hashcode of the object
     */
    @Override
    public int hashCode() {
        return Objects.hash(user, post, reaction);
    }
    
    /**
     * Redefinition of the equals method
     * @param obj the object to compare
     * @return true if the objects are equal, false otherwise
     */
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserPostReaction other = (UserPostReaction) obj;
        return Objects.equals(user, other.user) && Objects.equals(post, other.post) && Objects.equals(reaction, other.reaction);
    }
    
}