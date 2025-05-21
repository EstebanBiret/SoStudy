package utc.miage.sostudy.model.entity;

import jakarta.persistence.*;

/**
 * Class representing a repost of a post in the social network.
 */
@Entity
@Table(name = "repost")
public class Repost {

    /**
     * Unique identifier of the repost.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /**
     * User who made the repost.
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Original post that was reposted.
     */
    @ManyToOne
    @JoinColumn(name = "original_post_id")
    private Post originalPost;

    /**
     * Date of the repost.
     */
    private String repostDate;

    /**
     * Text content added by the user when reposting
     */
    @Column
    private String repostContent;

    /**
     * The formatted date of the repost
     */
    @Transient
    private String formattedDate;

    /**
     * Default constructor
     */
    public Repost() {}

    /**
     * Constructor with parameters
     *
     * @param user the user who made the repost
     * @param originalPost the original post that was reposted
     * @param repostDate the date of the repost
     * @param repostContent the text content added by the user when reposting
     */
    public Repost(User user, Post originalPost, String repostDate, String repostContent) {
        this.user = user;
        this.originalPost = originalPost;
        this.repostDate = repostDate;
        this.repostContent = repostContent;
    }

    /**
     * Gets the id of the repost
     * @return the id of the repost
     */
    public Integer getId() {return id;}

    /**
     * Sets the id of the repost
     * @param id the id of the repost
     */
    public void setId(Integer id) {this.id = id;}

    /**
     * Gets the user who made the repost
     * @return the user who made the repost
     */
    public User getUser() {return user;}

    /**
     * Sets the user who made the repost
     * @param user the user who made the repost
     */
    public void setUser(User user) {this.user = user;}

    /**
     * Gets the original post that was reposted
     * @return the original post that was reposted
     */
    public Post getOriginalPost() {return originalPost;}

    /**
     * Sets the original post that was reposted
     * @param originalPost the original post that was reposted
     */
    public void setOriginalPost(Post originalPost) {this.originalPost = originalPost;}

    /**
     * Gets the date of the repost
     * @return the date of the repost
     */
    public String getRepostDate() {return repostDate;}

    /**
     * Sets the date of the repost
     * @param repostDate the date of the repost
     */
    public void setRepostDate(String repostDate) {this.repostDate = repostDate;}

    /**
     * Gets the content of the repost
     * @return the content of the repost
     */
    public String getRepostContent() {return repostContent;}

    /**
     * Sets the content of the repost
     * @param content the content of the repost
     */
    public void setRepostContent(String content) {this.repostContent = content;}

    /**
     * Gets the formatted date of the repost
     * @return the formatted date of the repost
     */
    public String getFormattedDate() {return formattedDate;}

    /**
     * Sets the formatted date of the repost
     * @param formattedDate the formatted date of the repost
     */
    public void setFormattedDate(String formattedDate) {this.formattedDate = formattedDate;}

    /**
     * Override of the equals method.
     * @param o the object to compare with.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Repost repost = (Repost) o;

        return id != null && id.equals(repost.id);
    }

    /**
     * Override of the hashCode method.
     * @return the hash code of the object.
     */
    @Override
    public int hashCode() {
        return (id != null) ? id.hashCode() : 0;
    }

}