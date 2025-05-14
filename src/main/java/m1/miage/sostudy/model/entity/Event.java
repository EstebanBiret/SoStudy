package m1.miage.sostudy.model.entity;


import jakarta.persistence.*;

/**
 * class representing an event
 */
@Entity
@Table(name = "event")
public class Event {

    /**
     * ID of the event
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int eventId;


    /**
     * Name of the event
     */
    private String eventName;

    /**
     * Content of the event
     */
    private String eventContent;

    /**
     * Date of publication of the event
     */
    private String eventPublicationDate;

    /**
     * Date of start of the event
     */
    private String eventBeginningDate;

    /**
     * Date of end of the event
     */
    private String eventEndDate;

    /**
     * Location of the event
     */
    private String eventPlace;

    //ToDo : add the user who created the event

    //ToDo : add the list of users who are interested in the event


    /**
     * Constructor of the Event class
     */
    public Event() {
    }

    /**
     * Constructor of the Event class
     * @param eventName
     * @param eventPublicationDate
     * @param eventContent
     * @param eventBeginningDate
     * @param eventEndDate
     * @param eventPlace
     */
    public Event(String eventName, String eventPublicationDate, String eventContent, String eventBeginningDate, String eventEndDate, String eventPlace) {
        this.eventName = eventName;
        this.eventPublicationDate = eventPublicationDate;
        this.eventContent = eventContent;
        this.eventBeginningDate = eventBeginningDate;
        this.eventEndDate = eventEndDate;
        this.eventPlace = eventPlace;
    }

    /**
     * Constructor of the Event class
     * @param eventId
     * @param eventName
     * @param eventPublicationDate
     * @param eventContent
     * @param eventBeginningDate
     * @param eventEndDate
     * @param eventPlace
     */
    public Event(int eventId, String eventName, String eventPublicationDate, String eventContent, String eventBeginningDate, String eventEndDate, String eventPlace) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventPublicationDate = eventPublicationDate;
        this.eventContent = eventContent;
        this.eventBeginningDate = eventBeginningDate;
        this.eventEndDate = eventEndDate;
        this.eventPlace = eventPlace;
    }

    /**
     * Getters and Setters
     */
    public int getEventId() {
        return eventId;
    }

    /**
     * Getters and Setters
     */
    public String getEventName() {
        return eventName;
    }

    /**
     * Getters and Setters
     */
    public void setEventName(String subjectEvent) {
        this.eventName = subjectEvent;
    }

    /**
     * Getters and Setters
     */
    public String getEventPublicationDate() {
        return eventPublicationDate;
    }

    /**
     * Getters and Setters
     */
    public void setEventPublicationDate(String datePublicationEvent) {
        this.eventPublicationDate = datePublicationEvent;
    }

    /**
     * Getters and Setters
     */
    public String getEventContent() {
        return eventContent;
    }

    /**
     * Getters and Setters
     */
    public void setEventContent(String contentEvent) {
        this.eventContent = contentEvent;
    }

    /**
     * Getters and Setters
     */
    public String getEventBeginningDate() {
        return eventBeginningDate;
    }

    /**
     * Getters and Setters
     */
    public void setEventBeginningDate(String dateStartEvent) {
        this.eventBeginningDate = dateStartEvent;
    }

    /**
     * Getters and Setters
     */
    public String getEventEndDate() {
        return eventEndDate;
    }

    /**
     * Getters and Setters
     */
    public void setEventEndDate(String dateEndEvent) {
        this.eventEndDate = dateEndEvent;
    }

    /**
     * Getters and Setters
     */
    public String getEventPlace() {
        return eventPlace;
    }

    /**
     * Getters and Setters
     */
    public void setEventPlace(String locationEvent) {
        this.eventPlace = locationEvent;
    }

    /**
     * Override the hashCode method
     * @return the hash code of the event
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + eventId;
        result = prime * result + ((eventName == null) ? 0 : eventName.hashCode());
        result = prime * result + ((eventPublicationDate == null) ? 0 : eventPublicationDate.hashCode());
        result = prime * result + ((eventContent == null) ? 0 : eventContent.hashCode());
        result = prime * result + ((eventBeginningDate == null) ? 0 : eventBeginningDate.hashCode());
        result = prime * result + ((eventEndDate == null) ? 0 : eventEndDate.hashCode());
        result = prime * result + ((eventPlace == null) ? 0 : eventPlace.hashCode());
        return result;
    }

    /**
     * Override the equals method
     * @param obj
     * @return true if the event is equal to the object
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Event other = (Event) obj;
        if (eventId != other.eventId)
            return false;
        if (eventName == null) {
            if (other.eventName != null)
                return false;
        } else if (!eventName.equals(other.eventName))
            return false;
        if (eventPublicationDate == null) {
            if (other.eventPublicationDate != null)
                return false;
        } else if (!eventPublicationDate.equals(other.eventPublicationDate))
            return false;
        if (eventContent == null) {
            if (other.eventContent != null)
                return false;
        } else if (!eventContent.equals(other.eventContent))
            return false;
        if (eventBeginningDate == null) {
            if (other.eventBeginningDate != null)
                return false;
        } else if (!eventBeginningDate.equals(other.eventBeginningDate))
            return false;
        if (eventEndDate == null) {
            if (other.eventEndDate != null)
                return false;
        } else if (!eventEndDate.equals(other.eventEndDate))
            return false;
        if (eventPlace == null) {
            if (other.eventPlace != null)
                return false;
        } else if (!eventPlace.equals(other.eventPlace))
            return false;
        return true;
    }

    
}
