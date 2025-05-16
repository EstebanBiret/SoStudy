package m1.miage.sostudy.model.entity.dto;

import m1.miage.sostudy.model.entity.Repost;
import m1.miage.sostudy.model.entity.Post;

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
}
