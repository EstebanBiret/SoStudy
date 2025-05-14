package m1.miage.sostudy.model.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


/**
 * Class testing the Event class
 */
class EventTest {

    private static final int ID_EVENT = 1;
    private static final String SUBJECT_EVENT = "subjectEvent";
    private static final String DATE_PUBLICATION_EVENT = "datePublicationEvent";
    private static final String CONTENT_EVENT = "contentEvent";
    private static final String DATE_START_EVENT = "dateStartEvent";
    private static final String DATE_END_EVENT = "dateEndEvent";
    private static final String LOCATION_EVENT = "locationEvent";
    private static final Event EVENT = new Event(ID_EVENT, SUBJECT_EVENT, DATE_PUBLICATION_EVENT, CONTENT_EVENT, DATE_START_EVENT, DATE_END_EVENT, LOCATION_EVENT);

    /**
     * test the getEventContent method
     */
    @Test
    void testGetContentEvent() {
        final String expected = CONTENT_EVENT;
        final String actual = EVENT.getEventContent();
        Assertions.assertEquals(expected, actual);
    }

    /**
     * test the getEventEndDate method
     */
    @Test
    void testGetDateEndEvent() {
        final Event ev = new Event(ID_EVENT, SUBJECT_EVENT, DATE_PUBLICATION_EVENT, CONTENT_EVENT, DATE_START_EVENT, DATE_END_EVENT, LOCATION_EVENT);
        final String expected = DATE_END_EVENT;
        final String actual = ev.getEventEndDate();
        Assertions.assertEquals(expected, actual);
    }

    /**
     * test the getEventPublicationDate method
     */
    @Test
    void testGetDatePublicationEvent() {
        final Event ev = new Event(SUBJECT_EVENT, DATE_PUBLICATION_EVENT, CONTENT_EVENT, DATE_START_EVENT, DATE_END_EVENT, LOCATION_EVENT);
        final String expected = DATE_PUBLICATION_EVENT;
        final String actual = ev.getEventPublicationDate();
        Assertions.assertEquals(expected, actual);
    }


    /**
     * test the getEventBeginningDate method
     */
    @Test
    void testGetDateStartEvent() {
        final Event ev = new Event(ID_EVENT, SUBJECT_EVENT, DATE_PUBLICATION_EVENT, CONTENT_EVENT, DATE_START_EVENT, DATE_END_EVENT, LOCATION_EVENT);
        final String expected = DATE_START_EVENT;
        final String actual = ev.getEventBeginningDate();
        Assertions.assertEquals(expected, actual);
    }

    /**
     * test the getEventId method
     */
    @Test
    void testGetIdEvent() {
        final int expected = ID_EVENT;
        final int actual = EVENT.getEventId();
        Assertions.assertEquals(expected, actual);
    }

    /**
     * test the getEventPlace method
     */
    @Test
    void testGetLocationEvent() {
        final Event ev = new Event(ID_EVENT, SUBJECT_EVENT, DATE_PUBLICATION_EVENT, CONTENT_EVENT, DATE_START_EVENT, DATE_END_EVENT, LOCATION_EVENT);
        final String expected = LOCATION_EVENT;
        final String actual = ev.getEventPlace();
        Assertions.assertEquals(expected, actual);
    }

    /**
     * test the getEventName method
     */
    @Test
    void testGetSubjectEvent() {
        final String expected = SUBJECT_EVENT;
        final String actual = EVENT.getEventName();
        Assertions.assertEquals(expected, actual);
    }

    /**
     * test the setEventContent method
     */
    @Test
    void testSetContentEvent() {
        final String expected = "newContentEvent";
        EVENT.setEventContent(expected);
        final String actual = EVENT.getEventContent();
        Assertions.assertEquals(expected, actual);
    }

    /**
     * test the setEventEndDate method
     */
    @Test
    void testSetDateEndEvent() {
        final String expected = "newDateEndEvent";
        EVENT.setEventEndDate(expected);
        final String actual = EVENT.getEventEndDate();
        Assertions.assertEquals(expected, actual);
    }

    /**
     * test the setEventPublicationDate method
     */
    @Test
    void testSetDatePublicationEvent() {
        final String expected = "newDatePublicationEvent";
        EVENT.setEventPublicationDate(expected);
        final String actual = EVENT.getEventPublicationDate();
        Assertions.assertEquals(expected, actual);
    }

    /**
     * test the setEventBeginningDate method
     */
    @Test
    void testSetDateStartEvent() {
        final String expected = "newDateStartEvent";
        EVENT.setEventBeginningDate(expected);
        final String actual = EVENT.getEventBeginningDate();
        Assertions.assertEquals(expected, actual);
    }

    /**
     * test the setIdEvent method
     */
    @Test
    void testSetLocationEvent() {
        final String expected = "newLocationEvent";
        EVENT.setEventPlace(expected);
        final String actual = EVENT.getEventPlace();
        Assertions.assertEquals(expected, actual);
    }

    /**
     * test the setEventName method
     */
    @Test
    void testSetSubjectEvent() {
        final String expected = "newSubjectEvent";
        EVENT.setEventName(expected);
        final String actual = EVENT.getEventName();
        Assertions.assertEquals(expected, actual);
    }

    /**
     * test the equals method
     */
    @Test
    void testEquals() {
        final Event event1 = new Event(ID_EVENT, SUBJECT_EVENT, DATE_PUBLICATION_EVENT, CONTENT_EVENT, DATE_START_EVENT, DATE_END_EVENT, LOCATION_EVENT);
        final Event event2 = new Event(ID_EVENT, SUBJECT_EVENT, DATE_PUBLICATION_EVENT, CONTENT_EVENT, DATE_START_EVENT, DATE_END_EVENT, LOCATION_EVENT);
        Assertions.assertEquals(event1, event2);
    }

    /**
     * test the hashCode method
     */
    @Test
    void testHashCode() {
        final Event event1 = new Event(ID_EVENT, SUBJECT_EVENT, DATE_PUBLICATION_EVENT, CONTENT_EVENT, DATE_START_EVENT, DATE_END_EVENT, LOCATION_EVENT);
        final Event event2 = new Event(ID_EVENT, SUBJECT_EVENT, DATE_PUBLICATION_EVENT, CONTENT_EVENT, DATE_START_EVENT, DATE_END_EVENT, LOCATION_EVENT);
        Assertions.assertEquals(event1.hashCode(), event2.hashCode());
    }
}
