package utc.miage.sostudy.model.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the Repost entity.
 */
class RepostTest {
    
    /**
     * Test the equals method.
     */
    @Test
    void testEquals() {
        Repost repost1 = new Repost();
        Repost repost2 = new Repost();
        repost1.setId(1);
        repost2.setId(1);
        assertEquals(repost1.equals(repost2), true);
    }

    /**
     * Test the getContent method.
     */
    @Test
    void testGetContent() {
        Repost repost = new Repost();
        repost.setRepostContent("test");
        assertEquals("test", repost.getRepostContent());
    }

    /**
     * Test the getId method.
     */
    @Test
    void testGetId() {
        Repost repost = new Repost();
        repost.setId(1);
        assertEquals(1, repost.getId());
    }

    /**
     * Test the getOriginalPost method.
     */
    @Test
    void testGetOriginalPost() {
        Repost repost = new Repost();
        Post originalPost = new Post();
        repost.setOriginalPost(originalPost);
        assertEquals(originalPost, repost.getOriginalPost());
    }

    /**
     * Test the getRepostDate method.
     */
    @Test
    void testGetRepostDate() {
        Repost repost = new Repost();
        repost.setRepostDate("test");
        assertEquals("test", repost.getRepostDate());
    }

    /**
     * Test the getUser method.
     */
    @Test
    void testGetUser() {
        Repost repost = new Repost();
        User user = new User();
        repost.setUser(user);
        assertEquals(user, repost.getUser());
    }

    /**
     * Test the hashCode method.
     */
    @Test
    void testHashCode() {
        Repost repost = new Repost();
        repost.setId(1);
        assertEquals(1, repost.hashCode());
    }

    /**
     * Test the constructor.
     */
    @Test
    void testConstructor() {
        Repost repost = new Repost();
        assertNotNull(repost);
    }

    /**
     * Test the constructor with parameters.
     */
    @Test
    void testConstructorWithParameters() {
        Repost repost = new Repost(new User(), new Post(), "2025-01-05", "test");
        assertNotNull(repost);
    }

    /**
     * Test of getFormattedDate method.
     */
    @Test
    void testGetFormattedDate() {
        Repost repost = new Repost();
        repost.setFormattedDate("2025-01-05");
        assertEquals("2025-01-05", repost.getFormattedDate());
    }

}