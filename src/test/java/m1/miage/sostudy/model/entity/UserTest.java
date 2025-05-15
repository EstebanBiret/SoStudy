package m1.miage.sostudy.model.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the User entity.
 */
class UserTest {
    private User user;

    /**
     * Set up a User instance before each test.
     */
    @BeforeEach
    void setUp() {
        user = new User("Doe", "John", "john.doe@example.com", "password", "jdoe", "1990-01-01", "http://example.com/image.jpg", "Bio");
    }

    /**
     * Test the constructor and getters of the User class.
     */
    @Test
    void testUserConstructorAndGetters() {
        assertEquals("Bio", user.getBioUser());
        assertNotNull(user.getFollowing());
        assertNotNull(user.getFollowers());
        assertNotNull(user.getSubscribedChannels());
        assertNotNull(user.getCreatedChannels());
        assertNotNull(user.getCreatedPosts());
        assertNotNull(user.getSubscribedCommunities());
        assertNotNull(user.getCreatedCommunities());
        assertNotNull(user.getSubscribedEvents());
        assertNotNull(user.getCreatedEvents());
        assertNotNull(user.getSentMessages());
    }

    /**
     * Test the setters of the User class.
     */
    @Test
    void testSetBioUser() {
        user.setBioUser("New bio");
        assertEquals("New bio", user.getBioUser());
    }

    /**
     * Test the add and remove methods for followers, following, channels, posts, communities, events, and messages.
     */
    @Test
    void testAddAndRemoveFollower() {
        User follower = new User();
        user.addFollowers(follower);
        assertTrue(user.getFollowers().contains(follower));

        user.removeFollowers(follower);
        assertFalse(user.getFollowers().contains(follower));
    }

    /**
     * Test the add and remove methods for following.
     */
    @Test
    void testAddAndRemoveFollowing() {
        User followee = new User();
        user.addFollowing(followee);
        assertTrue(user.getFollowing().contains(followee));

        user.removeFollowing(followee);
        assertFalse(user.getFollowing().contains(followee));
    }

    /**
     * Test the add and remove methods for subscribed channels, created channels, created posts, reposted posts, subscribed communities, created communities, subscribed events, created events, and sent messages.
     */
    @Test
    void testSubscribedChannels() {
        Channel channel = new Channel();
        user.addSubscribedChannel(channel);
        assertTrue(user.getSubscribedChannels().contains(channel));

        user.removeSubscribedChannel(channel);
        assertFalse(user.getSubscribedChannels().contains(channel));
    }

    /**
     * Test the add and remove methods for created channels.
     */
    @Test
    void testCreatedChannels() {
        Channel channel = new Channel();
        user.addCreatedChannel(channel);
        assertTrue(user.getCreatedChannels().contains(channel));

        user.removeCreatedChannel(channel);
        assertFalse(user.getCreatedChannels().contains(channel));
    }

    /**
     * Test the add and remove methods for created posts.
     */
    @Test
    void testCreatedPosts() {
        Post post = new Post();
        user.addCreatedPost(post);
        assertTrue(user.getCreatedPosts().contains(post));

        user.removeCreatedPost(post);
        assertFalse(user.getCreatedPosts().contains(post));
    }

    /**
     * Test the add and remove methods for subscribed communities.
     */
    @Test
    void testSubscribedCommunities() {
        Community community = new Community();
        user.addSubscribedCommunity(community);
        assertTrue(user.getSubscribedCommunities().contains(community));

        user.removeSubscribedCommunity(community);
        assertFalse(user.getSubscribedCommunities().contains(community));
    }

    /**
     * Test the add and remove methods for created communities.
     */
    @Test
    void testCreatedCommunities() {
        Community community = new Community();
        user.addCreatedCommunity(community);
        assertTrue(user.getCreatedCommunities().contains(community));

        user.removeCreatedCommunity(community);
        assertFalse(user.getCreatedCommunities().contains(community));
    }

    /**
     * Test the add and remove methods for subscribed events.
     */
    @Test
    void testSubscribedEvents() {
        Event event = new Event();
        user.addSubscribedEvent(event);
        assertTrue(user.getSubscribedEvents().contains(event));

        user.removeSubscribedEvent(event);
        assertFalse(user.getSubscribedEvents().contains(event));
    }

    /**
     * Test the add and remove methods for created events.
     */
    @Test
    void testCreatedEvents() {
        Event event = new Event();
        user.addCreatedEvent(event);
        assertTrue(user.getCreatedEvents().contains(event));

        user.removeCreatedEvent(event);
        assertFalse(user.getCreatedEvents().contains(event));
    }

    /**
     * Test the add and remove methods for sent messages.
     */
    @Test
    void testSentMessages() {
        Message message = new Message();
        user.addSentMessage(message);
        assertTrue(user.getSentMessages().contains(message));

        user.removeSentMessage(message);
        assertFalse(user.getSentMessages().contains(message));
    }

    /**
     * Test the equals and hashCode methods of the User class.
     */
    @Test
    void testEqualsAndHashCode() {
        User otherUser = new User("Doe", "John", "john.doe@example.com", "password", "jdoe", "1990-01-01", "http://example.com/image.jpg", "Bio");
        otherUser.setIdUser(user.getIdUser()+1);
        assertNotEquals(user, otherUser);
    }

    /**
     * Test hashcode method of the User class.
     */
    @Test
    void testHashCode() {
        User otherUser = new User("Doe", "John", "john.doe@example.com", "password", "jdoe", "1990-01-01", "http://example.com/image.jpg", "Bio");
        otherUser.setIdUser(user.getIdUser()+1);
        assertNotEquals(user.hashCode(), otherUser.hashCode());
    }
}