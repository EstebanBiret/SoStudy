package utc.miage.sostudy.model.entity.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import utc.miage.sostudy.model.entity.Post;
import utc.miage.sostudy.model.entity.Repost;

/**
 * Class testing the RepostDisplay class
 */
class RepostDisplayTest {

    /**
     * Test the getOriginalPost method
     */
    @Test
    void testGetOriginalPost() {
        RepostDisplay repostDisplay = new RepostDisplay();
        Post post = new Post();
        repostDisplay.setOriginalPost(post);
        assertEquals(post, repostDisplay.getOriginalPost());
    }

    /**
     * Test the getRepost method
     */
    @Test
    void testGetRepost() {
        RepostDisplay repostDisplay = new RepostDisplay();
        Repost repost = new Repost();
        repostDisplay.setRepost(repost);
        assertEquals(repost, repostDisplay.getRepost());
    }

    /**
     * Test the equals method
     */
    @Test
    void testEquals() {
        RepostDisplay repostDisplay = new RepostDisplay();
        RepostDisplay repostDisplay2 = new RepostDisplay();
        assertEquals(repostDisplay, repostDisplay2);
    }

    /**
     * Test the hashCode method
     */
    @Test
    void testHashCode() {
        RepostDisplay repostDisplay = new RepostDisplay();
        RepostDisplay repostDisplay2 = new RepostDisplay();
        assertEquals(repostDisplay.hashCode(), repostDisplay2.hashCode());
    }

    /**
     * Test of constructor with parameters
     */
    @Test
    void testConstructorWithParameters() {
        RepostDisplay repostDisplay = new RepostDisplay(new Repost(), new Post());
        assertNotNull(repostDisplay);
    }
}
