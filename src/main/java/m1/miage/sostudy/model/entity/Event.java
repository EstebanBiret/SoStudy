package m1.miage.sostudy.model.entity;


import jakarta.persistence.*;
import java.util.Objects;

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

    /**
     * User who created the event
     */
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User userCreator;

    /**
     * List of users who are interested in the event
     */
    @ManyToMany
    @JoinTable(name = "event_users", joinColumns = @JoinColumn(name = "id_event"), inverseJoinColumns = @JoinColumn(name = "id_user"))
    private java.util.List<User> usersInterested;

    /**
     * Constructor of the Event class
     */
    public Event() {
    }

    /**
     * Constructor of the Event class
     * @param eventName the name of the event
     * @param eventPublicationDate the date of publication of the event
     * @param eventContent the content of the event
     * @param eventBeginningDate the date of start of the event
     * @param eventEndDate the date of end of the event
     * @param eventPlace the location of the event
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
     * @param eventId the id of the event
     * @param eventName the name of the event
     * @param eventPublicationDate the date of publication of the event
     * @param eventContent the content of the event
     * @param eventBeginningDate the date of start of the event
     * @param eventEndDate the date of end of the event
     * @param eventPlace the location of the event
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
     * Get the id of the event
     * @return the id of the event
     */
    public int getEventId() {
        return eventId;
    }

    /**
     * Get the name of the event
     * @return the name of the event
     */
    public String getEventName() {
        return eventName;
    }

    /**
     * Set the name of the event
     * @param eventName the name of the event
     */
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    /**
     * Get the date of publication of the event
     * @return the date of publication of the event
     */
    public String getEventPublicationDate() {
        return eventPublicationDate;
    }

    /**
     * Set the date of publication of the event
     * @param eventPublicationDate the date of publication of the event
     */
    public void setEventPublicationDate(String eventPublicationDate) {
        this.eventPublicationDate = eventPublicationDate;
    }

    /**
     * Get the content of the event
     * @return the content of the event
     */
    public String getEventContent() {
        return eventContent;
    }

    /**
     * Set the content of the event
     * @param eventContent the content of the event
     */
    public void setEventContent(String eventContent) {
        this.eventContent = eventContent;
    }

    /**
     * Get the date of start of the event
     * @return the date of start of the event
     */
    public String getEventBeginningDate() {
        return eventBeginningDate;
    }

    /**
     * Set the date of start of the event
     * @param eventBeginningDate the date of start of the event
     */
    public void setEventBeginningDate(String eventBeginningDate) {
        this.eventBeginningDate = eventBeginningDate;
    }

    /**
     * Get the date of end of the event
     * @return the date of end of the event
     */
    public String getEventEndDate() {
        return eventEndDate;
    }

    /**
     * Set the date of end of the event
     * @param eventEndDate the date of end of the event
     */
    public void setEventEndDate(String eventEndDate) {
        this.eventEndDate = eventEndDate;
    }

    /**
     * Get the location of the event
     * @return the location of the event
     */
    public String getEventPlace() {
        return eventPlace;
    }

    /**
     * Set the location of the event
     * @param eventPlace the location of the event
     */
    public void setEventPlace(String eventPlace) {
        this.eventPlace = eventPlace;
    }

    /**
     * Get the user who created the event
     * @return the user who created the event
     */
    public User getUserCreator() {
        return userCreator;
    }

    /**
     * Set the user who created the event
     * @param userCreator the user who created the event
     */
    public void setUserCreator(User userCreator) {
        this.userCreator = userCreator;
    }

    /**
     * Get the list of users interested in the event
     * @return the list of users interested in the event
     */
    public java.util.List<User> getUsersInterested() {
        return usersInterested;
    }

    /**
     * Set the list of users interested in the event
     * @param usersInterested the list of users interested in the event
     */
    public void setUsersInterested(java.util.List<User> usersInterested) {
        this.usersInterested = usersInterested;
    }

    /**
     * add a user to the list of users interested in the event
     * @param user the user to add to the list of users interested in the event
     */
    public void addUserInterested(User user) {
        if (this.usersInterested == null) {
            this.usersInterested = new java.util.ArrayList<>();
        }
        this.usersInterested.add(user);
    }

    /**
     * remove a user from the list of users interested in the event
         */
    public void removeUserInterested(User user) {
        if (this.usersInterested != null) {
            this.usersInterested.remove(user);
        }
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
     * @return true if the events are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
    if (obj == null || getClass() != obj.getClass()) {
        return false;
    }
    Event other = (Event) obj;
    return eventId == other.eventId &&
           Objects.equals(eventName, other.eventName) &&
           Objects.equals(eventPublicationDate, other.eventPublicationDate) &&
           Objects.equals(eventContent, other.eventContent) &&
           Objects.equals(eventBeginningDate, other.eventBeginningDate) &&
           Objects.equals(eventEndDate, other.eventEndDate) &&
           Objects.equals(eventPlace, other.eventPlace);
    }
    
}