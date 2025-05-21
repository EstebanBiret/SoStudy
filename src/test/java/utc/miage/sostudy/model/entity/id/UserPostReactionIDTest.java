package utc.miage.sostudy.model.entity.id;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the UserPostReactionID class
 */
class UserPostReactionIDTest {

    /**
     * Test the equals method
     */
    @Test
    void testEquals() {
        UserPostReactionID id1 = new UserPostReactionID(1, 1, 1);
        UserPostReactionID id2 = new UserPostReactionID(1, 1, 1);
        UserPostReactionID id3 = new UserPostReactionID(1, 1, 2);
        UserPostReactionID id4 = new UserPostReactionID(1, 2, 1);
        UserPostReactionID id5 = new UserPostReactionID(2, 1, 1);
        
        assertEquals(id1, id2);
        assertNotEquals(id1, id3);
        assertNotEquals(id1, id4);
        assertNotEquals(id1, id5);
    }

    /**
     * Test the getPostId method
     */
    @Test
    void testGetPostId() {
        UserPostReactionID id = new UserPostReactionID(1, 1, 1);
        assertEquals(1, id.getPostId());
    }

    /**
     * Test the getReactionId method
     */
    @Test
    void testGetReactionId() {
        UserPostReactionID id = new UserPostReactionID(1, 1, 1);
        assertEquals(1, id.getReactionId());

    }

    /**
     * Test the getUserId method
     */
    @Test
    void testGetUserId() {
        UserPostReactionID id = new UserPostReactionID(1, 1, 1);
        assertEquals(1, id.getUserId());
    }

    /**
     * Test the hashCode method
     */
    @Test
    void testHashCode() {
        UserPostReactionID id1 = new UserPostReactionID(1, 1, 1);
        UserPostReactionID id2 = new UserPostReactionID(1, 1, 1);
        assertEquals(id1.hashCode(), id2.hashCode());
    }

    /**
     * Test the setPostId method
     */
    @Test
    void testSetPostId() {
        UserPostReactionID id = new UserPostReactionID(1, 1, 1);
        id.setPostId(2);
        assertEquals(2, id.getPostId());
    }

    /**
     * Test the setReactionId method
     */
    @Test
    void testSetReactionId() {
        UserPostReactionID id = new UserPostReactionID(1, 1, 1);
        id.setReactionId(2);
        assertEquals(2, id.getReactionId());

    }

    /**
     * Test the setUserId method
     */
    @Test
    void testSetUserId() {
        UserPostReactionID id = new UserPostReactionID(1, 1, 1);
        id.setUserId(2);
        assertEquals(2, id.getUserId());
    }
}