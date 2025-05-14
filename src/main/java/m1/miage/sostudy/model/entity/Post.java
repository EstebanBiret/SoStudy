package m1.miage.sostudy.model.entity;

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
     * The URL of the media of the post, can be null
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
     * The list of users who reposted the post
     */
    @ManyToMany
    @JoinTable(name = "reposted_posts",
        joinColumns = @JoinColumn(name = "post_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> reposts = new ArrayList<>();

    /**
     * Default constructor
     */
    public Post() {}

    /**
     * Constructor with parameters
     * 
     * @param postPublicationDate the publication date of the post
     * @param postContent the content of the post
     * @param postMediaPath the URL of the media of the post, can be null
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
     * Getter for the media URL of the post
     * @return the media URL of the post
     */
    public String getPostMediaPath() {return postMediaPath;}

    /**
     * Setter for the media URL of the post
     * @param postMediaPath the media URL of the post
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
     * Getter for the list of users who reposted the post
     * @return the list of users who reposted the post
     */
    public List<User> getReposts() {return reposts;}

    /**
     * Add a user to the list of users who reposted the post
     * @param user the user to add
     */
    public void addRepost(User user) {this.reposts.add(user);}

    /**
     * Remove a user from the list of users who reposted the post
     * @param user the user to remove
     */
    public void removeRepost(User user) {this.reposts.remove(user);}

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