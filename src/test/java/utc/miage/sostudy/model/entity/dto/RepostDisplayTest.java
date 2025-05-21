package utc.miage.sostudy.model.entity.dto;

import org.junit.jupiter.api.Test;

import utc.miage.sostudy.model.entity.Post;
import utc.miage.sostudy.model.entity.Repost;

import org.junit.jupiter.api.Assertions;

/**
 * Class testing the RepostDisplay class
 */
public class RepostDisplayTest {

    /**
     * Test the getOriginalPost method
     */
    @Test
    void testGetOriginalPost() {
        RepostDisplay repostDisplay = new RepostDisplay();
        Post post = new Post();
        repostDisplay.setOriginalPost(post);
        Assertions.assertEquals(post, repostDisplay.getOriginalPost());
    }

    /**
     * Test the getRepost method
     */
    @Test
    void testGetRepost() {
        RepostDisplay repostDisplay = new RepostDisplay();
        Repost repost = new Repost();
        repostDisplay.setRepost(repost);
        Assertions.assertEquals(repost, repostDisplay.getRepost());
    }

    /**
     * Test the equals method
     */
    @Test
    void testEquals() {
        RepostDisplay repostDisplay = new RepostDisplay();
        RepostDisplay repostDisplay2 = new RepostDisplay();
        Assertions.assertEquals(repostDisplay, repostDisplay2);
    }

    /**
     * Test the getOriginalPost method
     */
    @Test
    void testGetOriginalPost2() {
        RepostDisplay repostDisplay = new RepostDisplay();
        Post post = new Post();
        repostDisplay.setOriginalPost(post);
        Assertions.assertEquals(post, repostDisplay.getOriginalPost());
    }

    /**
     * Test the getRepost method
     */
    @Test
    void testGetRepost2() {
        RepostDisplay repostDisplay = new RepostDisplay();
        Repost repost = new Repost();
        repostDisplay.setRepost(repost);
        Assertions.assertEquals(repost, repostDisplay.getRepost());
    }

    /**
     * Test the hashCode method
     */
    @Test
    void testHashCode() {
        RepostDisplay repostDisplay = new RepostDisplay();
        RepostDisplay repostDisplay2 = new RepostDisplay();
        Assertions.assertEquals(repostDisplay.hashCode(), repostDisplay2.hashCode());
    }

    /**
     * Test the setOriginalPost method
     */
    @Test
    void testSetOriginalPost() {
        RepostDisplay repostDisplay = new RepostDisplay();
        Post post = new Post();
        repostDisplay.setOriginalPost(post);
        Assertions.assertEquals(post, repostDisplay.getOriginalPost());
    }

    /**
     * Test the setRepost method
     */
    @Test
    void testSetRepost() {
        RepostDisplay repostDisplay = new RepostDisplay();
        Repost repost = new Repost();
        repostDisplay.setRepost(repost);
        Assertions.assertEquals(repost, repostDisplay.getRepost());
    }

    /**
     * Test of constructor with parameters
     */
    @Test
    void testConstructorWithParameters() {
        RepostDisplay repostDisplay = new RepostDisplay(new Repost(), new Post());
        Assertions.assertNotNull(repostDisplay);
    }
}
