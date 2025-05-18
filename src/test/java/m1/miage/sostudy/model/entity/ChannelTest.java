package m1.miage.sostudy.model.entity;

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
    private Channel channel;

    /**
     * Sets up the Channel instance before each test.
     */
    @BeforeEach
    void setUp() {
        channel = new Channel();
    }

    /**
     * Tests the default constructor of the Channel class.
     */
    @Test
    void testEmptyConstructor() {
        assertNotNull(channel);
        assertNull(channel.getChannelName());
        assertNull(channel.getChannelImagePath());
        assertEquals(0, channel.getChannelId());
    }

    /**
     * Tests the parameterized constructor of the Channel class.
     */
    @Test
    void testParameterizedConstructor() {
        Channel channel = new Channel("Gaming", "http://example.com/pic.png");

        assertEquals("Gaming", channel.getChannelName());
        assertEquals("http://example.com/pic.png", channel.getChannelImagePath());
    }

    /**
     * Tests the getIdChannel method.
     */
    @Test
    void testGetAndSetChannelName() {
        channel.setChannelName("Science");
        assertEquals("Science", channel.getChannelName());
    }

    /**
     * Tests the getChannelImagePath method.
     */
    @Test
    void testGetAndSetChannelImagePath() {
        channel.setChannelImagePath("http://example.com/science.png");
        assertEquals("http://example.com/science.png", channel.getChannelImagePath());
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

        channel.addUser(user1);
        channel.addUser(user2);


        assertEquals(2, channel.getUsers().size());
        assertTrue(channel.getUsers().contains(user1));
        assertTrue(channel.getUsers().contains(user2));

        channel.removeUser(user1);
        assertEquals(1, channel.getUsers().size());
        assertFalse(channel.getUsers().contains(user1));
    }

    /**
     * Tests the set and get methods for creator.
     */
    @Test
    void testSetAndGetCreator() {
        User creator = new User();
        channel.setCreator(creator);

        assertEquals(creator, channel.getCreator());
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

        channel.addMessage(message1);
        channel.addMessage(message2);

        List<Message> messages = channel.getMessages();
        assertEquals(2, messages.size());
        assertTrue(messages.contains(message1));
        assertTrue(messages.contains(message2));

        channel.removeMessage(message1);
        assertEquals(1, channel.getMessages().size());
        assertFalse(channel.getMessages().contains(message1));
    }

    /**
     * Tests the hashcode method.
     */
    @Test
    void testHashCode() {
        Channel channel1 = new Channel("Tech", "http://example.com/tech.png");
        Channel channel2 = new Channel("Tech", "http://example.com/tech.png");

        assertEquals(channel1.hashCode(), channel2.hashCode());
    }
}