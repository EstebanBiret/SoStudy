package m1.miage.sostudy.model.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test pour la classe Message
 */
class MessageTest {
    /**
     * Objet Message à tester
     */
    private Message message;

    /**
     * Initialisation de l'objet Message avant chaque test
     */
    @BeforeEach
    void setUp() {
        message = new Message();
    }

    /**
     * Test du constructeur vide
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
     * Test du constructeur paramétré
     */
    @Test
    void testParameterizedConstructor() {
        Message msg = new Message("Hello world", "2024-01-01");
        assertEquals("Hello world", msg.getContent());
        assertEquals("2024-01-01", msg.getDateMessage());
    }

    /**
     * Test de la méthode getIdMessage
     */
    @Test
    void testGetAndSetIdMessage() {
        assertEquals(0, message.getMessageId());
    }

    /**
     * Test de la méthode getContent et setContent
     */
    @Test
    void testGetAndSetContent() {
        message.setContent("Test content");
        assertEquals("Test content", message.getContent());
    }

    /**
     * Test de la méthode getDateMessage et setDateMessage
     */
    @Test
    void testGetAndSetDateMessage() {
        message.setDateMessage("2025-05-13");
        assertEquals("2025-05-13", message.getDateMessage());
    }

    /**
     * Test de la méthode getChannel et setChannel
     */
    @Test
    void testGetAndSetChannel() {
        Channel channel = new Channel("General", "http://example.com/pic.png");
        message.setChannel(channel);
        assertEquals(channel, message.getChannel());
    }

    /**
     * Test de la méthode equals
     */
    @Test
    void testEquals() {
        Message msg1 = new Message("Hello", "2024-01-01");
        Message msg2 = new Message("Hello", "2024-01-01");
        assertEquals(msg1, msg2);
    }

    /**
     * Test de la méthode equals avec un objet différent
     */
    @Test
    void testEqualsWithDifferentObject() {
        Message msg1 = new Message("Hello", "2024-01-01");
        String differentObject = "Not a message";

        assertNotEquals(msg1, differentObject);
    }

}