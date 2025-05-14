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
    private String publicationDate;

    /**
     * The content of the post
     */
    private String content;

    /**
     * The URL of the media of the post, can be null
     */
    private String mediaURL;

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

    /*TODO 
        ajouter lien vers communauté 0..1
        ajouter lien vers User 1
        ajouter lien vers User 0..*
    */

    /**
     * Default constructor
     */
    public Post() {}

    /**
     * Constructor with parameters
     * 
     * @param publicationDate the publication date of the post
     * @param content the content of the post
     * @param mediaURL the URL of the media of the post, can be null
     */
    public Post(String publicationDate, String content, String mediaURL) {
        this.publicationDate = publicationDate;
        this.content = content;
        this.mediaURL = mediaURL;
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
    public String getPublicationDate() {return publicationDate;}

    /**
     * Setter for the publication date of the post
     * @param publicationDate the publication date of the post
     */
    public void setPublicationDate(String publicationDate) {this.publicationDate = publicationDate;}

    /**
     * Getter for the content of the post
     * @return the content of the post
     */
    public String getContent() {return content;}

    /**
     * Setter for the content of the post
     * @param content the content of the post
     */
    public void setContent(String content) {this.content = content;}

    /**
     * Getter for the media URL of the post
     * @return the media URL of the post
     */
    public String getMediaURL() {return mediaURL;}

    /**
     * Setter for the media URL of the post
     * @param mediaURL the media URL of the post
     */
    public void setMediaURL(String mediaURL) {this.mediaURL = mediaURL;}

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

    /*
     * TODO getters setters pour les nouveaux attributs
     */

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