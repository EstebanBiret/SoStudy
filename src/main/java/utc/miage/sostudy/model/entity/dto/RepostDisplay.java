package utc.miage.sostudy.model.entity.dto;

import utc.miage.sostudy.model.entity.Repost;

import java.util.Objects;

import utc.miage.sostudy.model.entity.Post;

/**
 * Class representing a repost of a post in the social network.
 */
public class RepostDisplay {

    /**
     * The repost
     */
    private Repost repost;

    /**
     * The original post
     */
    private Post originalPost;

    /**
     * Default constructor
     */
    public RepostDisplay() {}

    /**
     * Constructor
     * @param repost the repost
     * @param originalPost the original post
     */
    public RepostDisplay(Repost repost, Post originalPost) {
        this.repost = repost;
        this.originalPost = originalPost;
    }

    /**
     * Gets the repost
     * @return the repost
     */
    public Repost getRepost() {
        return repost;
    }

    /**
     * Gets the original post
     * @return the original post
     */
    public Post getOriginalPost() {
        return originalPost;
    }

    /**
     * Sets the repost
     * @param repost the repost
     */
    public void setRepost(Repost repost) {
        this.repost = repost;
    }

    /**
     * Sets the original post
     * @param originalPost the original post
     */
    public void setOriginalPost(Post originalPost) {
        this.originalPost = originalPost;
    }

    /**
     * Test the equals method
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RepostDisplay that)) return false;
        return Objects.equals(repost, that.repost) &&
        Objects.equals(originalPost, that.originalPost);
    }

    /**
     * Test the hashCode method
     */
    @Override
    public int hashCode() {
        return Objects.hash(repost, originalPost);
    }
}