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
     * test the getContentEvent method
     */
    @Test
    void testGetContentEvent() {
        final String expected = CONTENT_EVENT;
        final String actual = EVENT.getContentEvent();
        Assertions.assertEquals(expected, actual);
    }

    /**
     * test the getDateEndEvent method
     */
    @Test
    void testGetDateEndEvent() {
        final Event ev = new Event(ID_EVENT, SUBJECT_EVENT, DATE_PUBLICATION_EVENT, CONTENT_EVENT, DATE_START_EVENT, DATE_END_EVENT, LOCATION_EVENT);
        final String expected = DATE_END_EVENT;
        final String actual = ev.getDateEndEvent();
        Assertions.assertEquals(expected, actual);
    }

    /**
     * test the getDatePublicationEvent method
     */
    @Test
    void testGetDatePublicationEvent() {
        final Event ev = new Event(SUBJECT_EVENT, DATE_PUBLICATION_EVENT, CONTENT_EVENT, DATE_START_EVENT, DATE_END_EVENT, LOCATION_EVENT);
        final String expected = DATE_PUBLICATION_EVENT;
        final String actual = ev.getDatePublicationEvent();
        Assertions.assertEquals(expected, actual);
    }


    /**
     * test the getDateStartEvent method
     */
    @Test
    void testGetDateStartEvent() {
        final Event ev = new Event(ID_EVENT, SUBJECT_EVENT, DATE_PUBLICATION_EVENT, CONTENT_EVENT, DATE_START_EVENT, DATE_END_EVENT, LOCATION_EVENT);
        final String expected = DATE_START_EVENT;
        final String actual = ev.getDateStartEvent();
        Assertions.assertEquals(expected, actual);
    }

    /**
     * test the getIdEvent method
     */
    @Test
    void testGetIdEvent() {
        final int expected = ID_EVENT;
        final int actual = EVENT.getIdEvent();
        Assertions.assertEquals(expected, actual);
    }

    /**
     * test the getLocationEvent method
     */
    @Test
    void testGetLocationEvent() {
        final Event ev = new Event(ID_EVENT, SUBJECT_EVENT, DATE_PUBLICATION_EVENT, CONTENT_EVENT, DATE_START_EVENT, DATE_END_EVENT, LOCATION_EVENT);
        final String expected = LOCATION_EVENT;
        final String actual = ev.getLocationEvent();
        Assertions.assertEquals(expected, actual);
    }

    /**
     * test the getSubjectEvent method
     */
    @Test
    void testGetSubjectEvent() {
        final String expected = SUBJECT_EVENT;
        final String actual = EVENT.getSubjectEvent();
        Assertions.assertEquals(expected, actual);
    }

    /**
     * test the setContentEvent method
     */
    @Test
    void testSetContentEvent() {
        final String expected = "newContentEvent";
        EVENT.setContentEvent(expected);
        final String actual = EVENT.getContentEvent();
        Assertions.assertEquals(expected, actual);
    }

    /**
     * test the setDateEndEvent method
     */
    @Test
    void testSetDateEndEvent() {
        final String expected = "newDateEndEvent";
        EVENT.setDateEndEvent(expected);
        final String actual = EVENT.getDateEndEvent();
        Assertions.assertEquals(expected, actual);
    }

    /**
     * test the setDatePublicationEvent method
     */
    @Test
    void testSetDatePublicationEvent() {
        final String expected = "newDatePublicationEvent";
        EVENT.setDatePublicationEvent(expected);
        final String actual = EVENT.getDatePublicationEvent();
        Assertions.assertEquals(expected, actual);
    }

    /**
     * test the setDateStartEvent method
     */
    @Test
    void testSetDateStartEvent() {
        final String expected = "newDateStartEvent";
        EVENT.setDateStartEvent(expected);
        final String actual = EVENT.getDateStartEvent();
        Assertions.assertEquals(expected, actual);
    }

    /**
     * test the setIdEvent method
     */
    @Test
    void testSetLocationEvent() {
        final String expected = "newLocationEvent";
        EVENT.setLocationEvent(expected);
        final String actual = EVENT.getLocationEvent();
        Assertions.assertEquals(expected, actual);
    }

    /**
     * test the setSubjectEvent method
     */
    @Test
    void testSetSubjectEvent() {
        final String expected = "newSubjectEvent";
        EVENT.setSubjectEvent(expected);
        final String actual = EVENT.getSubjectEvent();
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
