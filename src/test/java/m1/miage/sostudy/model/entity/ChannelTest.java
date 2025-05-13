package m1.miage.sostudy.model.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        assertNull(channel.getUrlChannelPicture());
        assertEquals(0, channel.getIdChannel());
    }

    /**
     * Tests the parameterized constructor of the Channel class.
     */
    @Test
    void testParameterizedConstructor() {
        Channel channel = new Channel("Gaming", "http://example.com/pic.png");

        assertEquals("Gaming", channel.getChannelName());
        assertEquals("http://example.com/pic.png", channel.getUrlChannelPicture());
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
     * Tests the getUrlChannelPicture method.
     */
    @Test
    void testGetAndSetUrlChannelPicture() {
        channel.setUrlChannelPicture("http://example.com/science.png");
        assertEquals("http://example.com/science.png", channel.getUrlChannelPicture());
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


}