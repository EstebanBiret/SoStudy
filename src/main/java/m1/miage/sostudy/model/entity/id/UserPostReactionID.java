package m1.miage.sostudy.model.entity.id;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

/**
 * Class representing the id of a user post reaction
 */
@Embeddable
public class UserPostReactionID implements Serializable{
    
    /**
     * The id of the user
     */
    private Integer userId;
    
    /**
     * The id of the post
     */
    private Integer postId;
    
    /**
     * The id of the reaction
     */
    private Integer reactionId;

    /**
     * Default constructor
     */
    public UserPostReactionID() {}

    /**
     * Constructor with parameters
     * @param userId the id of the user
     * @param postId the id of the post
     * @param reactionId the id of the reaction
     */
    public UserPostReactionID(Integer userId, Integer postId, Integer reactionId) {
        this.userId = userId;
        this.postId = postId;
        this.reactionId = reactionId;
    }

    /**
     * Getter for the id of the user
     * @return the id of the user
     */
    public Integer getUserId() {return userId;}

    /**
     * Getter for the id of the post
     * @return the id of the post
     */
    public Integer getPostId() {return postId;}

    /**
     * Getter for the id of the reaction
     * @return the id of the reaction
     */
    public Integer getReactionId() {return reactionId;}

    /**
     * Setter for the id of the user
     * @param userId the id of the user
     */
    public void setUserId(Integer userId) {this.userId = userId;}

    /**
     * Setter for the id of the post
     * @param postId the id of the post
     */
    public void setPostId(Integer postId) {this.postId = postId;}

    /**
     * Setter for the id of the reaction
     * @param reactionId the id of the reaction
     */
    public void setReactionId(Integer reactionId) {this.reactionId = reactionId;}

    /**
     * Redefinition of the equals method
     * @param o the object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserPostReactionID)) return false;
        UserPostReactionID that = (UserPostReactionID) o;
        return userId.equals(that.userId) && postId.equals(that.postId) && reactionId.equals(that.reactionId);
    }

    /**
     * Redefinition of the hashcode method
     * @return the hashcode of the object
     */
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (postId != null ? postId.hashCode() : 0);
        result = 31 * result + (reactionId != null ? reactionId.hashCode() : 0);
        return result;
    }

}