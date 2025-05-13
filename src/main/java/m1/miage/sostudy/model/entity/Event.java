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
    private int idEvent;


    /**
     * Subject of the event
     */
    private String subjectEvent;

    /**
     * Date of publication of the event
     */
    private String datePublicationEvent;

    /**
     * Content of the event
     */
    private String contentEvent;

    /**
     * Date of start of the event
     */
    private String dateStartEvent;

    /**
     * Date of end of the event
     */
    private String dateEndEvent;

    /**
     * Location of the event
     */
    private String locationEvent;

    //ToDo : add the user who created the event

    //ToDo : add the list of users who are interested in the event


    /**
     * Constructor of the Event class
     */
    public Event() {
    }

    /**
     * Constructor of the Event class
     * @param subjectEvent
     * @param datePublicationEvent
     * @param contentEvent
     * @param dateStartEvent
     * @param dateEndEvent
     * @param locationEvent
     */
    public Event(String subjectEvent, String datePublicationEvent, String contentEvent, String dateStartEvent, String dateEndEvent, String locationEvent) {
        this.subjectEvent = subjectEvent;
        this.datePublicationEvent = datePublicationEvent;
        this.contentEvent = contentEvent;
        this.dateStartEvent = dateStartEvent;
        this.dateEndEvent = dateEndEvent;
        this.locationEvent = locationEvent;
    }

    /**
     * Constructor of the Event class
     * @param idEvent
     * @param subjectEvent
     * @param datePublicationEvent
     * @param contentEvent
     * @param dateStartEvent
     * @param dateEndEvent
     * @param locationEvent
     */
    public Event(int idEvent, String subjectEvent, String datePublicationEvent, String contentEvent, String dateStartEvent, String dateEndEvent, String locationEvent) {
        this.idEvent = idEvent;
        this.subjectEvent = subjectEvent;
        this.datePublicationEvent = datePublicationEvent;
        this.contentEvent = contentEvent;
        this.dateStartEvent = dateStartEvent;
        this.dateEndEvent = dateEndEvent;
        this.locationEvent = locationEvent;
    }

    /**
     * Getters and Setters
     */
    public int getIdEvent() {
        return idEvent;
    }

    /**
     * Getters and Setters
     */
    public String getSubjectEvent() {
        return subjectEvent;
    }

    /**
     * Getters and Setters
     */
    public void setSubjectEvent(String subjectEvent) {
        this.subjectEvent = subjectEvent;
    }

    /**
     * Getters and Setters
     */
    public String getDatePublicationEvent() {
        return datePublicationEvent;
    }

    /**
     * Getters and Setters
     */
    public void setDatePublicationEvent(String datePublicationEvent) {
        this.datePublicationEvent = datePublicationEvent;
    }

    /**
     * Getters and Setters
     */
    public String getContentEvent() {
        return contentEvent;
    }

    /**
     * Getters and Setters
     */
    public void setContentEvent(String contentEvent) {
        this.contentEvent = contentEvent;
    }

    /**
     * Getters and Setters
     */
    public String getDateStartEvent() {
        return dateStartEvent;
    }

    /**
     * Getters and Setters
     */
    public void setDateStartEvent(String dateStartEvent) {
        this.dateStartEvent = dateStartEvent;
    }

    /**
     * Getters and Setters
     */
    public String getDateEndEvent() {
        return dateEndEvent;
    }

    /**
     * Getters and Setters
     */
    public void setDateEndEvent(String dateEndEvent) {
        this.dateEndEvent = dateEndEvent;
    }

    /**
     * Getters and Setters
     */
    public String getLocationEvent() {
        return locationEvent;
    }

    /**
     * Getters and Setters
     */
    public void setLocationEvent(String locationEvent) {
        this.locationEvent = locationEvent;
    }

    /**
     * Override the hashCode method
     * @return the hash code of the event
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + idEvent;
        result = prime * result + ((subjectEvent == null) ? 0 : subjectEvent.hashCode());
        result = prime * result + ((datePublicationEvent == null) ? 0 : datePublicationEvent.hashCode());
        result = prime * result + ((contentEvent == null) ? 0 : contentEvent.hashCode());
        result = prime * result + ((dateStartEvent == null) ? 0 : dateStartEvent.hashCode());
        result = prime * result + ((dateEndEvent == null) ? 0 : dateEndEvent.hashCode());
        result = prime * result + ((locationEvent == null) ? 0 : locationEvent.hashCode());
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
        if (idEvent != other.idEvent)
            return false;
        if (subjectEvent == null) {
            if (other.subjectEvent != null)
                return false;
        } else if (!subjectEvent.equals(other.subjectEvent))
            return false;
        if (datePublicationEvent == null) {
            if (other.datePublicationEvent != null)
                return false;
        } else if (!datePublicationEvent.equals(other.datePublicationEvent))
            return false;
        if (contentEvent == null) {
            if (other.contentEvent != null)
                return false;
        } else if (!contentEvent.equals(other.contentEvent))
            return false;
        if (dateStartEvent == null) {
            if (other.dateStartEvent != null)
                return false;
        } else if (!dateStartEvent.equals(other.dateStartEvent))
            return false;
        if (dateEndEvent == null) {
            if (other.dateEndEvent != null)
                return false;
        } else if (!dateEndEvent.equals(other.dateEndEvent))
            return false;
        if (locationEvent == null) {
            if (other.locationEvent != null)
                return false;
        } else if (!locationEvent.equals(other.locationEvent))
            return false;
        return true;
    }

    
}
