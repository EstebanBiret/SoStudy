package utc.miage.sostudy.model.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

/**
 * Class representing a post
 */
@Entity
@Table(name = "post")
public class Post {

    /**
     * The id of the post
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer postId;
    
    /**
     * The publication date of the post
     */
    private String postPublicationDate;

    /**
     * The content of the post
     */
    private String postContent;

    /**
     * Path of the image of the post, can be null
     */
    private String postMediaPath;

    /**
     * The post that is commented (the actual post object is a comment), can be null
     */
    @ManyToOne
    @JoinColumn(name = "comment_father_id")
    private Post commentFather;

    /**
     * The list of comments of the post
     */
    @OneToMany(mappedBy = "commentFather", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> comments = new ArrayList<>();

    /**
     * The community to which the post belongs
     */
    @ManyToOne
    @JoinColumn(name = "community_id")
    private Community community;

    /**
     * The user who created the post
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * The list of reactions to the post
     */
    @Transient
    private List<UserPostReaction> reactions = new ArrayList<>();

    /**
     * The formatted date of the post
     */
    @Transient
    private String formattedDate;

    /**
     * Counters for each type of reaction
     */
    @Transient
    private long likeCount = 0;
    
    @Transient
    private long loveCount = 0;
    
    @Transient
    private long laughCount = 0;
    
    @Transient
    private long cryCount = 0;
    
    @Transient
    private long angryCount = 0;

    /**
     * Default constructor
     */
    public Post() {}

    /**
     * Constructor with parameters
     * 
     * @param postPublicationDate the publication date of the post
     * @param postContent the content of the post
     * @param postMediaPath the path of the image of the post, can be null
     */
    public Post(String postPublicationDate, String postContent, String postMediaPath) {
        this.postPublicationDate = postPublicationDate;
        this.postContent = postContent;
        this.postMediaPath = postMediaPath;
    }

    /**
     * Getter for the id of the post
     * @return the id of the post
     */
    public Integer getPostId() {return postId;}

    /**
     * Setter for the id of the post
     * @param postId the id of the post
     */
    public void setPostId(Integer postId) {this.postId = postId;}

    /**
     * Getter for the publication date of the post
     * @return the publication date of the post
     */
    public String getPostPublicationDate() {return postPublicationDate;}

    /**
     * Setter for the publication date of the post
     * @param postPublicationDate the publication date of the post
     */
    public void setPostPublicationDate(String postPublicationDate) {this.postPublicationDate = postPublicationDate;}

    /**
     * Getter for the content of the post
     * @return the content of the post
     */
    public String getPostContent() {return postContent;}

    /**
     * Setter for the content of the post
     * @param postContent the content of the post
     */
    public void setPostContent(String postContent) {this.postContent = postContent;}

    /**
     * Getter for the media path of the post
     * @return the media path of the post
     */
    public String getPostMediaPath() {return postMediaPath;}

    /**
     * Setter for the media path of the post
     * @param postMediaPath the media path of the post
     */
    public void setPostMediaPath(String postMediaPath) {this.postMediaPath = postMediaPath;}

    /**
     * Getter for the comment father of the post
     * @return the comment father of the post
     */
    public Post getCommentFather() {return commentFather;}

    /**
     * Setter for the comment father of the post
     * @param commentFather the comment father of the post
     */
    public void setCommentFather(Post commentFather) {this.commentFather = commentFather;}

    /**
     * Getter for the list of comments of the post
     * @return the list of comments of the post
     */
    public List<Post> getComments() {return comments;}

    /**
     * Setter for the list of comments of the post
     * @param comments the list of comments of the post
     */
    public void setComments(List<Post> comments) {this.comments = comments;}

    /**
     * Getter for the community of the post
     * @return the community of the post
     */
    public Community getCommunity() {return community;}

    /**
     * Setter for the community of the post
     * @param community the community of the post
     */
    public void setCommunity(Community community) {this.community = community;}

    /**
     * Getter for the user of the post
     * @return the user of the post
     */
    public User getUser() {return user;}

    /**
     * Setter for the user of the post
     * @param user the user of the post
     */
    public void setUser(User user) {this.user = user;}

    /**
     * Getter for the list of reactions to the post
     * @return the list of reactions to the post
     */
    public List<UserPostReaction> getReactions() {return reactions;}

    /**
     * Setter for the list of reactions to the post
     * @param reactions the list of reactions to the post
     */
    public void setReactions(List<UserPostReaction> reactions) {this.reactions = reactions;}

    /**
     * Gets the count of like reactions
     *
     * @return the count of like reactions
     */
    public long getLikeCount() {
        return likeCount;
    }

    /**
     * Sets the count of like reactions
     *
     * @param likeCount the count of like reactions
     */
    public void setLikeCount(long likeCount) {
        this.likeCount = likeCount;
    }

    /**
     * Gets the count of love reactions
     *
     * @return the count of love reactions
     */
    public long getLoveCount() {
        return loveCount;
    }

    /**
     * Sets the count of love reactions
     *
     * @param loveCount the count of love reactions
     */
    public void setLoveCount(long loveCount) {
        this.loveCount = loveCount;
    }

    /**
     * Gets the count of laugh reactions
     *
     * @return the count of laugh reactions
     */
    public long getLaughCount() {
        return laughCount;
    }

    /**
     * Sets the count of laugh reactions
     *
     * @param laughCount the count of laugh reactions
     */
    public void setLaughCount(long laughCount) {
        this.laughCount = laughCount;
    }

    /**
     * Gets the count of cry reactions
     *
     * @return the count of cry reactions
     */
    public long getCryCount() {
        return cryCount;
    }

    /**
     * Sets the count of cry reactions
     *
     * @param cryCount the count of cry reactions
     */
    public void setCryCount(long cryCount) {
        this.cryCount = cryCount;
    }

    /**
     * Gets the count of angry reactions
     *
     * @return the count of angry reactions
     */
    public long getAngryCount() {
        return angryCount;
    }

    /**
     * Sets the count of angry reactions
     *
     * @param angryCount the count of angry reactions
     */
    public void setAngryCount(long angryCount) {
        this.angryCount = angryCount;
    }

    /**
     * Gets the formatted date of the post
     * @return the formatted date of the post
     */
    public String getFormattedDate() {
        return formattedDate;
    }

    /**
     * Sets the formatted date of the post
     * @param formatPostDate the formatted date of the post
     */
    public void setFormattedDate(String formatPostDate) {
        this.formattedDate = formatPostDate;
    }

    /**
     * Redefinition of the equals method
     * @param obj the object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Post other = (Post) obj;
        if (postId == null || other.postId == null)
            return false;
        return postId.equals(other.postId);
    }

    /**
     * Redefinition of the hashCode method
     * @return the hash code of the object
     */
    @Override
    public int hashCode() {return postId == null ? 0 : postId.hashCode();}
}