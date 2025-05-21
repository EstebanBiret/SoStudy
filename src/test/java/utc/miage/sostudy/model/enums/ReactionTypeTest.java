package utc.miage.sostudy.model.enums;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Test class for the ReactionType enum
 */
public class ReactionTypeTest {

    /**
     * Test the getName method
     */
    @Test
    void testConstructorWithParameters() {
        // Test LIKE constant
        assertEquals(ReactionType.LIKE.getName(), "Like");
        assertEquals(ReactionType.LIKE.getImagePath(), "/images/reactions/like.png");
        
        // Test LOVE constant
        assertEquals(ReactionType.LOVE.getName(), "Love");
        assertEquals(ReactionType.LOVE.getImagePath(), "/images/reactions/love.png");
        
        // Test LAUGH constant
        assertEquals(ReactionType.LAUGH.getName(), "Laugh");
        assertEquals(ReactionType.LAUGH.getImagePath(), "/images/reactions/laugh.png");
        
        // Test CRY constant
        assertEquals(ReactionType.CRY.getName(), " Cry");
        assertEquals(ReactionType.CRY.getImagePath(), "/images/reactions/cry.png");
        
        // Test ANGRY constant
        assertEquals(ReactionType.ANGRY.getName(), "Angry");
        assertEquals(ReactionType.ANGRY.getImagePath(), "/images/reactions/angry.png");
    }
}