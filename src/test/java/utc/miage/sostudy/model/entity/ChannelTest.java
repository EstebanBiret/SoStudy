package utc.miage.sostudy.model.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Channel class.
 */
class ChannelTest {
    /**
     * The Channel instance to be tested.
     */
    private Channel c;

    /**
     * Sets up the Channel instance before each test.
     */
    @BeforeEach
    void setUp() {
        c = new Channel();
    }

    /**
     * Tests the default constructor of the Channel class.
     */
    @Test
    void testEmptyConstructor() {
        assertNotNull(c);
        assertNull(c.getChannelName());
        assertNull(c.getChannelImagePath());
        assertEquals(0, c.getChannelId());
    }

    /**
     * Tests the parameterized constructor of the Channel class.
     */
    @Test
    void testParameterizedConstructor() {
        c = new Channel("Gaming", "http://example.com/pic.png");

        assertEquals("Gaming", c.getChannelName());
        assertEquals("http://example.com/pic.png", c.getChannelImagePath());
    }

    /**
     * Tests the getIdChannel method.
     */
    @Test
    void testGetAndSetChannelName() {
        c.setChannelName("Science");
        assertEquals("Science", c.getChannelName());
    }

    /**
     * Tests the getChannelImagePath method.
     */
    @Test
    void testGetAndSetChannelImagePath() {
        c.setChannelImagePath("http://example.com/science.png");
        assertEquals("http://example.com/science.png", c.getChannelImagePath());
    }

    /**
     * Tests the equals method.
     */
    @Test
    void testEquals() {
        Channel channel1 = new Channel("Tech", "http://example.com/tech.png");
        Channel channel2 = new Channel("Tech", "http://example.com/tech.png");

        assertEquals(channel1, channel2);
    }

    /**
     * Tests the equals method with different object.
     */
    @Test
    void testEqualsWithDifferentObject() {
        Channel channel1 = new Channel("Tech", "http://example.com/tech.png");
        int differentObject = 0;

        assertNotEquals(channel1, differentObject);
    }

    /**
     * Tests the equals method with null.
     */
    @Test
    void testEqualsWithDifferentName(){
        Channel channel1 = new Channel("Tech", "http://example.com/tech.png");
        Channel channel2 = new Channel("Science", "http://example.com/tech.png");

        assertNotEquals(channel1, channel2);
    }

    /**
     * Tests the add and remove methods for users.
     */
    @Test
    void testAddAndRemoveUser() {
        User user1 = new User();
        User user2 = new User();

        user1.setIdUser(1);
        user2.setIdUser(2);

        c.addUser(user1);
        c.addUser(user2);


        assertEquals(2, c.getUsers().size());
        assertTrue(c.getUsers().contains(user1));
        assertTrue(c.getUsers().contains(user2));

        c.removeUser(user1);
        assertEquals(1, c.getUsers().size());
        assertFalse(c.getUsers().contains(user1));
    }

    /**
     * Tests the set and get methods for creator.
     */
    @Test
    void testSetAndGetCreator() {
        User creator = new User();
        c.setCreator(creator);

        assertEquals(creator, c.getCreator());
    }

    /**
     * Tests the add and remove methods for messages.
     */
    @Test
    void testAddAndRemoveMessage() {
        Message message1 = new Message();
        Message message2 = new Message();

        message1.setMessageId(1);
        message2.setMessageId(2);

        c.addMessage(message1);
        c.addMessage(message2);

        List<Message> messages = c.getMessages();
        assertEquals(2, messages.size());
        assertTrue(messages.contains(message1));
        assertTrue(messages.contains(message2));

        c.removeMessage(message1);
        assertEquals(1, c.getMessages().size());
        assertFalse(c.getMessages().contains(message1));
    }

    /**
     * Tests the hashcode method.
     */
    @Test
    void testHashCode() {
        Channel c1 = new Channel("Tech", "http://example.com/tech.png");
        Channel c2 = new Channel("Tech", "http://example.com/tech.png");

        assertEquals(c1.hashCode(), c2.hashCode());
    }
}