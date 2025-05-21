package utc.miage.sostudy.model.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import utc.miage.sostudy.model.entity.id.UserPostReactionID;

/**
 * Test class for the UserPostReaction class
 */
class UserPostReactionTest {

    /**
     * Test the constructor with parameters
     */
    @Test
    void testConstructorWithParameters() {
        User user = new User();
        user.setIdUser(1);

        Post post = new Post();
        post.setPostId(2);

        Reaction reaction = new Reaction();
        reaction.setReactionId(3);

        UserPostReaction upr = new UserPostReaction(user, post, reaction);

        assertEquals(user, upr.getUser());
        assertEquals(post, upr.getPost());
        assertEquals(reaction, upr.getReaction());

        assertEquals(1, upr.getId().getUserId());
        assertEquals(2, upr.getId().getPostId());
        assertEquals(3, upr.getId().getReactionId());
    }

    /**
     * Test the equals method
     */
    @Test
    void testEquals() {
        UserPostReaction reaction1 = new UserPostReaction();
        UserPostReaction reaction2 = new UserPostReaction();
        assertEquals(reaction1, reaction2);
    }

    /**
     * Test the getId method
     */
    @Test
    void testGetId() {
        UserPostReaction reaction = new UserPostReaction();
        assertEquals(reaction.getId(), reaction.getId());
    }

    /**
     * Test the getPost method
     */
    @Test
    void testGetPost() {
        UserPostReaction reaction = new UserPostReaction();
        assertEquals(reaction.getPost(), reaction.getPost());

    }

    /**
     * Test the getReaction method
     */
    @Test
    void testGetReaction() {
        UserPostReaction reaction = new UserPostReaction();
        assertEquals(reaction.getReaction(), reaction.getReaction());
    }

    /**
     * Test the getUser method
     */
    @Test
    void testGetUser() {
        UserPostReaction reaction = new UserPostReaction();
        assertEquals(reaction.getUser(), reaction.getUser());

    }

    /**
     * Test the hashCode method
     */
    @Test
    void testHashCode() {
        UserPostReaction reaction1 = new UserPostReaction();
        UserPostReaction reaction2 = new UserPostReaction();
        assertEquals(reaction1.hashCode(), reaction2.hashCode());
    }

    /**
     * Test the setId method
     */
    @Test
    void testSetId() {
        UserPostReaction reaction = new UserPostReaction();
        reaction.setId(new UserPostReactionID(1, 1, 1));
        assertEquals(reaction.getId(), new UserPostReactionID(1, 1, 1));
    }

    /**
     * Test the setPost method
     */
    @Test
    void testSetPost() {
        UserPostReaction reaction = new UserPostReaction();
        Post post = new Post();
        reaction.setPost(post);
        assertEquals(post, reaction.getPost());
    }

    /**
     * Test the setReaction method
     */
    @Test
    void testSetReaction() {
        UserPostReaction reaction = new UserPostReaction();
        Reaction r = new Reaction();
        reaction.setReaction(r);
        assertEquals(r, reaction.getReaction());
    }

    /**
     * Test the setUser method
     */
    @Test
    void testSetUser() {
        UserPostReaction reaction = new UserPostReaction();
        reaction.setUser(new User());
        assertEquals(reaction.getUser(), new User());
    }
}