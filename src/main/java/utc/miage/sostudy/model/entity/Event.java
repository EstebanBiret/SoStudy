package utc.miage.sostudy.model.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
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
     * Description of the event
     */
    private String eventDescription;

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
    private String eventLocation;

    /**
     * Path of the image of the event
     */
    private String eventImagePath;

    /**
     * User who created the event
     */
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User userCreator;

    /**
     * List of users who are interested in the event
     */
    @ManyToMany(mappedBy = "subscribedEvents",fetch = FetchType.EAGER)
    private List<User> users = new ArrayList<>();

    /**
     * Number of members interested in the event
     */
    @Transient
    private Integer numberOfMembers;

    /**
     * Constructor of the Event class
     */
    public Event() {}

    /**
     * Constructor of the Event class
     * @param eventName the name of the event
     * @param eventPublicationDate the date of publication of the event
     * @param eventDescription the description of the event
     * @param eventBeginningDate the date of start of the event
     * @param eventEndDate the date of end of the event
     * @param eventLocation the location of the event
     */
    public Event(String eventName, String eventPublicationDate, String eventDescription, String eventBeginningDate, String eventEndDate, String eventLocation) {
        this.eventName = eventName;
        this.eventPublicationDate = eventPublicationDate;
        this.eventDescription = eventDescription;
        this.eventBeginningDate = eventBeginningDate;
        this.eventEndDate = eventEndDate;
        this.eventLocation = eventLocation;
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
    public Event(int eventId, String eventName, String eventPublicationDate, String eventDescription, String eventBeginningDate, String eventEndDate, String eventLocation) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventPublicationDate = eventPublicationDate;
        this.eventDescription = eventDescription;
        this.eventBeginningDate = eventBeginningDate;
        this.eventEndDate = eventEndDate;
        this.eventLocation = eventLocation;
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
     * Get the description of the event
     * @return the description of the event
     */
    public String getEventDescription() {
        return eventDescription;
    }

    /**
     * Set the description of the event
     * @param eventDescription the description of the event
     */
    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
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
    public String getEventLocation() {
        return eventLocation;
    }

    /**
     * Set the location of the event
     * @param eventLocation the location of the event
     */
    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
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
     * Get the path of the image of the event
     * @return the path of the image of the event
     */
    public String getEventImagePath() {
        return eventImagePath;
    }

    /**
     * Set the path of the image of the event
     * @param eventImagePath the path of the image of the event
     */
    public void setEventImagePath(String eventImagePath) {
        this.eventImagePath = eventImagePath;
    }

    /**
     * Get the number of members interested in the event
     * @return the number of members interested in the event
     */
    public Integer getNumberOfMembers() {
        return numberOfMembers;
    }

    /**
     * Set the number of members interested in the event
     * @param numberOfMembers the number of members interested in the event
     */
    public void setNumberOfMembers(Integer numberOfMembers) {
        this.numberOfMembers = numberOfMembers;
    }

    /**
     * Get the list of users interested in the event
     * @return the list of users interested in the event
     */
    public java.util.List<User> getUsers() {
        return users;
    }

    /**
     * Set the list of users interested in the event
     * @param users the list of users interested in the event
     */
    public void setUsers(java.util.List<User> users) {
        this.users = users;
    }

    /**
     * add a user to the list of users interested in the event
     * @param user the user to add to the list of users interested in the event
     */
    public void addUser(User user) {
        if (this.users == null) {
            this.users = new java.util.ArrayList<>();
        }
        this.users.add(user);
    }

    /**
     * remove a user from the list of users interested in the event
     * @param user the user to remove from the list of users interested in the event
     */
    public void removeUser(User user) {
        if (this.users != null) {
            this.users.remove(user);
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
        result = prime * result + ((eventDescription == null) ? 0 : eventDescription.hashCode());
        result = prime * result + ((eventBeginningDate == null) ? 0 : eventBeginningDate.hashCode());
        result = prime * result + ((eventEndDate == null) ? 0 : eventEndDate.hashCode());
        result = prime * result + ((eventLocation == null) ? 0 : eventLocation.hashCode());
        return result;
    }

    /**
     * Override the equals method
     * @param o the object to compare with
     * @return true if the events are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
    if (o == null || getClass() != o.getClass()) {
        return false;
    }
    Event other = (Event) o;
    return eventId == other.eventId &&
           Objects.equals(eventName, other.eventName) &&
           Objects.equals(eventPublicationDate, other.eventPublicationDate) &&
           Objects.equals(eventDescription, other.eventDescription) &&
           Objects.equals(eventBeginningDate, other.eventBeginningDate) &&
           Objects.equals(eventEndDate, other.eventEndDate) &&
           Objects.equals(eventLocation, other.eventLocation);
    }
    
}