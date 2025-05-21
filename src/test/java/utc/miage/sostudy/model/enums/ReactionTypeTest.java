package utc.miage.sostudy.model.enums;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Test class for the ReactionType enum
 */
class ReactionTypeTest {

    /**
     * Test the getName method
     */
    @Test
    void testConstructorWithParameters() {
        // Test LIKE constant
        assertEquals("Like", ReactionType.LIKE.getName());
        assertEquals("/images/reactions/like.png", ReactionType.LIKE.getImagePath());
        
        // Test LOVE constant
        assertEquals("Love", ReactionType.LOVE.getName());
        assertEquals("/images/reactions/love.png", ReactionType.LOVE.getImagePath());
        
        // Test LAUGH constant
        assertEquals("Laugh", ReactionType.LAUGH.getName());
        assertEquals("/images/reactions/laugh.png", ReactionType.LAUGH.getImagePath());
        
        // Test CRY constant
        assertEquals("Cry", ReactionType.CRY.getName());
        assertEquals("/images/reactions/cry.png", ReactionType.CRY.getImagePath());
        
        // Test ANGRY constant
        assertEquals("Angry", ReactionType.ANGRY.getName());
        assertEquals("/images/reactions/angry.png", ReactionType.ANGRY.getImagePath());
    }
}