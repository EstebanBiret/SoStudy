package utc.miage.sostudy.model.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Test class for the Event class
 */
class EventTest {

    /**
     * Test the addUser method
     */
    @Test
    void testAddUser() {
        Event event = new Event();
        User user = new User();
        event.addUser(user);
        assertEquals(1, event.getUsers().size());
    }

    /**
     * Test the equals method
     */
    @Test
    void testEquals() {
        Event event1 = new Event();
        Event event2 = new Event();
        assertEquals(event1, event2);
    }

    /**
     * Test the getEventBeginningDate method
     */
    @Test
    void testGetEventBeginningDate() {
        Event event = new Event();
        event.setEventBeginningDate("2022-01-01");
        assertEquals("2022-01-01", event.getEventBeginningDate());
    }

    /**
     * Test the getEventDescription method
     */
    void testGetEventDescription() {
        Event event = new Event();
        event.setEventDescription("Description");
        assertEquals("Description", event.getEventDescription());
    }

    /**
     * Test the getEventEndDate method
     */
    void testGetEventEndDate() {
        Event event = new Event();
        event.setEventEndDate("2022-01-01");
        assertEquals("2022-01-01", event.getEventEndDate());
    }

    /**
     * Test the getEventId method
     */
    void testGetEventId() {
        Event event = new Event(1, "Event", "2022-01-01", "Description", "2022-01-01", "2022-01-01", "Location");
        assertEquals(1, event.getEventId());
    }

    /**
     * Test the getEventImagePath method
     */
    void testGetEventImagePath() {
        Event event = new Event();
        event.setEventImagePath("image.jpg");
        assertEquals("image.jpg", event.getEventImagePath());
    }

    /**
     * Test the getEventLocation method
     */
    void testGetEventLocation() {
        Event event = new Event();
        event.setEventLocation("Location");
        assertEquals("Location", event.getEventLocation());
    }

    /**
     * Test the getEventName method
     */
    void testGetEventName() {
        Event event = new Event();
        event.setEventName("Event");
        assertEquals("Event", event.getEventName());
    }

    /**
     * Test the getEventPublicationDate method
     */
    void testGetEventPublicationDate() {
        Event event = new Event();
        event.setEventPublicationDate("2022-01-01");
        assertEquals("2022-01-01", event.getEventPublicationDate());
    }

    /**
     * Test the getNumberOfMembers method
     */
    void testGetNumberOfMembers() {
        Event event = new Event();
        event.setNumberOfMembers(1);
        assertEquals(1, event.getNumberOfMembers());
    }

    /**
     * Test the getUserCreator method
     */
    void testGetUserCreator() {
        Event event = new Event();
        User user = new User();
        event.setUserCreator(user);
        assertEquals(user, event.getUserCreator());
    }

    /**
     * Test the hashCode method
     */
    void testHashCode() {
        Event event = new Event();
        assertEquals(event.hashCode(), event.hashCode());
    }

    /**
     * Test the removeUser method
     */
    void testRemoveUser() {
        Event event = new Event();
        User user = new User();
        event.addUser(user);
        event.removeUser(user);
        assertEquals(0, event.getUsers().size());
    }

}
