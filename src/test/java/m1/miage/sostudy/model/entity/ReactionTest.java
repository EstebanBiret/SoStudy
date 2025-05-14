package m1.miage.sostudy.model.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import m1.miage.sostudy.model.enums.ReactionType;

/**
 * Test class for the Reaction entity
 */
public class ReactionTest {

    /**
     * Test the constructor with parameters
     */
    @Test
    void testConstructorWithParameters() {
        Reaction reaction = new Reaction(ReactionType.LIKE);
        assertEquals(ReactionType.LIKE, reaction.getReactionType());
    }

    /**
     * Test the getReactionId method
     */
    @Test
    void testGetReactionId() {
        Reaction reaction = new Reaction();
        reaction.setReactionId(1);
        assertEquals(1, reaction.getReactionId());
    }

    /**
     * Test the getReactionType method
     */
    @Test
    void testGetReactionType() {
        Reaction reaction = new Reaction();
        reaction.setReactionType(ReactionType.LIKE);
        assertEquals(ReactionType.LIKE, reaction.getReactionType());

    }

    /**
     * Test the setReactionId method
     */
    @Test
    void testSetReactionId() {
        Reaction reaction = new Reaction();
        reaction.setReactionId(1);
        assertEquals(1, reaction.getReactionId());
    }

    /**
     * Test the setReactionType method
     */
    @Test
    void testSetReactionType() {
        Reaction reaction = new Reaction();
        reaction.setReactionType(ReactionType.LIKE);
        assertEquals(ReactionType.LIKE, reaction.getReactionType());

    }

    /**
     * Test the equals method
     */
    @Test
    void testEquals() {
        Reaction reaction1 = new Reaction();
        reaction1.setReactionId(1);
        Reaction reaction2 = new Reaction();
        reaction2.setReactionId(1);
        assertEquals(reaction1, reaction2);
    }

    /**
     * Test the equals method with null
     */
    @Test
    void testEqualsWithNull() {
        Reaction reaction = new Reaction();
        assertNotEquals(null, reaction);
    }

    /**
     * Test the equals method with different class
     */
    @Test
    void testEqualsWithDifferentClass() {
        Reaction reaction = new Reaction();
        assertNotEquals("some string", reaction);
    }
    
    /**
     * Test the equals method with different id
     */
    @Test
    void testEqualsWithDifferentId() {
        Reaction reaction1 = new Reaction();
        reaction1.setReactionId(1);
        Reaction reaction2 = new Reaction();
        reaction2.setReactionId(2);
        assertNotEquals(reaction1, reaction2);
    }

    /**
     * Test the equals method with null id and non null id
     */
    @Test
    void testEqualsWithNullIdAndNonNullId() {
        Reaction reaction1 = new Reaction();
        reaction1.setReactionId(null);
        Reaction reaction2 = new Reaction();
        reaction2.setReactionId(1);
        assertNotEquals(reaction1, reaction2);
    }

    /**
     * Test the hashCode method
     */
    @Test
    void testHashCode() {
        Reaction reaction = new Reaction();
        reaction.setReactionId(1);
        assertEquals(1, reaction.hashCode());
    }
}