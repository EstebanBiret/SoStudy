package m1.miage.sostudy.model.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

/**
 * Test class for the Repost entity.
 */
public class RepostTest {
    
    /**
     * Test the equals method.
     */
    @Test
    void testEquals() {
        Repost repost1 = new Repost();
        Repost repost2 = new Repost();
        repost1.setId(1);
        repost2.setId(1);
        Assertions.assertTrue(repost1.equals(repost2));
    }

    /**
     * Test the getContent method.
     */
    @Test
    void testGetContent() {
        Repost repost = new Repost();
        repost.setRepostContent("test");
        Assertions.assertEquals("test", repost.getRepostContent());
    }

    /**
     * Test the getId method.
     */
    @Test
    void testGetId() {
        Repost repost = new Repost();
        repost.setId(1);
        Assertions.assertEquals(1, repost.getId());
    }

    /**
     * Test the getOriginalPost method.
     */
    @Test
    void testGetOriginalPost() {
        Repost repost = new Repost();
        Post originalPost = new Post();
        repost.setOriginalPost(originalPost);
        Assertions.assertEquals(originalPost, repost.getOriginalPost());
    }

    /**
     * Test the getRepostDate method.
     */
    @Test
    void testGetRepostDate() {
        Repost repost = new Repost();
        repost.setRepostDate("test");
        Assertions.assertEquals("test", repost.getRepostDate());
    }

    /**
     * Test the getUser method.
     */
    @Test
    void testGetUser() {
        Repost repost = new Repost();
        User user = new User();
        repost.setUser(user);
        Assertions.assertEquals(user, repost.getUser());
    }

    /**
     * Test the hashCode method.
     */
    @Test
    void testHashCode() {
        Repost repost = new Repost();
        repost.setId(1);
        Assertions.assertEquals(1, repost.hashCode());
    }

    /**
     * Test the setContent method.
     */
    @Test
    void testSetContent() {
        Repost repost = new Repost();
        repost.setRepostContent("test");
        Assertions.assertEquals("test", repost.getRepostContent());
    }

    /**
     * Test the setId method.
     */
    @Test
    void testSetId() {
        Repost repost = new Repost();
        repost.setId(1);
        Assertions.assertEquals(1, repost.getId());

    }

    /**
     * Test the setOriginalPost method.
     */
    @Test
    void testSetOriginalPost() {
        Repost repost = new Repost();
        Post originalPost = new Post();
        repost.setOriginalPost(originalPost);
        Assertions.assertEquals(originalPost, repost.getOriginalPost());
    }

    /**
     * Test the setRepostDate method.
     */
    @Test
    void testSetRepostDate() {
        Repost repost = new Repost();
        repost.setRepostDate("test");
        Assertions.assertEquals("test", repost.getRepostDate());
    }

    /**
     * Test the setUser method.
     */
    @Test
    void testSetUser() {
        Repost repost = new Repost();
        User user = new User();
        repost.setUser(user);
        Assertions.assertEquals(user, repost.getUser());
    }

    /**
     * Test the constructor.
     */
    @Test
    void testConstructor() {
        Repost repost = new Repost();
        Assertions.assertNotNull(repost);
    }

    /**
     * Test the constructor with parameters.
     */
    @Test
    void testConstructorWithParameters() {
        Repost repost = new Repost(new User(), new Post(), "2025-01-05", "test");
        Assertions.assertNotNull(repost);
    }

}