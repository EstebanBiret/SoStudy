package utc.miage.sostudy.model.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class testing the Message class
 */
class MessageTest {
    /**
     * Object Message to test
     */
    private Message message;

    /**
     * Initialization of the Message object before each test
     */
    @BeforeEach
    void setUp() {
        message = new Message();
    }

    /**
     * Test of the empty constructor
     */
    @Test
    void testEmptyConstructor() {
        assertNotNull(message);
        assertNull(message.getContent());
        assertNull(message.getDateMessage());
        assertNull(message.getChannel());
        assertEquals(0, message.getMessageId()); // int par défaut
    }

    /**
     * Test of the parameterized constructor
     */
    @Test
    void testParameterizedConstructor() {
        Message msg = new Message("Hello world", "2024-01-01");
        assertEquals("Hello world", msg.getContent());
        assertEquals("2024-01-01", msg.getDateMessage());
    }

    /**
     * Test of getIdMessage method
     */
    @Test
    void testGetAndSetIdMessage() {
        assertEquals(0, message.getMessageId());
    }

    /**
     * Test of getContent and setContent methods
     */
    @Test
    void testGetAndSetContent() {
        message.setContent("Test content");
        assertEquals("Test content", message.getContent());
    }

    /**
     * Test of getDateMessage and setDateMessage methods
     */
    @Test
    void testGetAndSetDateMessage() {
        message.setDateMessage("2025-05-13");
        assertEquals("2025-05-13", message.getDateMessage());
    }

    @Test
    void testGetAndSetSender(){
        User sender = new User();
        message.setSender(sender);
        assertEquals(sender, message.getSender());
    }

    /**
     * Test of getChannel and setChannel methods
     */
    @Test
    void testGetAndSetChannel() {
        Channel channel = new Channel("General", "http://example.com/pic.png");
        message.setChannel(channel);
        assertEquals(channel, message.getChannel());
    }

    /**
     * Test of equals method
     */
    @Test
    void testEquals() {
        Message msg1 = new Message("Hello", "2024-01-01");
        Message msg2 = new Message("Hello", "2024-01-01");
        assertEquals(msg1, msg2);
    }

    /**
     * Test of equals method with a different object
     */
    @Test
    void testEqualsWithDifferentObject() {
        Message msg1 = new Message("Hello", "2024-01-01");
        String differentObject = "Not a message";

        assertNotEquals(msg1, differentObject);
    }

    /**
     * Test of hashCode method
     */
    @Test
    void testHashCode() {
        Message msg1 = new Message("Hello", "2024-01-01");
        Message msg2 = new Message("Hello", "2024-01-01");
        assertEquals(msg1.hashCode(), msg2.hashCode());
    }

}