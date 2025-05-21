package utc.miage.sostudy.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * Class representing a channel
 */
@Entity
@Table(name = "channel")
public class Channel {

    /**
     * Unique identifier of the channel
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int channelId;

    /**
     * Name of the channel
     */
    private String channelName;

    /**
     * Path of the image of the channel
     */
    private String channelImagePath;

    /**
     * List of users in the channel
     */
    @ManyToMany(mappedBy = "subscribedChannels",fetch = FetchType.EAGER)
    @JsonIgnore
    private List<User> users = new ArrayList<>();

    /**
     * Creator of the channel
     */
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "idUser")
    private User creator;


    /**
     * List of messages in the channel
     */
    @OneToMany(mappedBy = "channel", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Message> messages = new ArrayList<>();

    /**
     * Empty constructor of the Channel class
     */
    public Channel() {
    }

    /**
     * Constructor of the Channel class
     * @param channelName the name of the channel
     * @param channelImagePath the image path of the channel
     */
    public Channel(String channelName, String channelImagePath) {
        this.channelName = channelName;
        this.channelImagePath = channelImagePath;
    }

    /**
     * Getter for the id of the channel
     * @return the id of the channel
     */
    public int getChannelId() {
        return channelId;
    }

    /**
     * Getter for the name of the channel
     * @return the name of the channel
     */
    public String getChannelName() {
        return channelName;
    }

    /**
     * Setter for the name of the channel
     * @param channelName the name of the channel
     */
    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    /**
     * Getter for the path of the image of the channel
     * @return the path of the image of the channel
     */
    public String getChannelImagePath() {
        return channelImagePath;
    }

    /**
     * Setter for the path of the image of the channel
     * @param channelImagePath the path of the image of the channel
     */
    public void setChannelImagePath(String channelImagePath) {
        this.channelImagePath = channelImagePath;
    }

    /**
     * Getter for the list of users in the channel
     * @return the list of users in the channel
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     * Method to add a user to the channel
     * @param user the user to add
     */
    public void addUser(User user) {
        this.users.add(user);
    }

    /**
     * Method to remove a user from the channel
     * @param user the user to remove
     */
    public void removeUser(User user) {
        this.users.remove(user);
    }

    /**
     * Getter for the creator of the channel
     * @return the creator of the channel
     */
    public User getCreator() {
        return creator;
    }

    /**
     * Setter for the creator of the channel
     * @param creator the creator of the channel
     */
    public void setCreator(User creator) {
        this.creator = creator;
    }

    /**
     * Getter for the list of messages in the channel
     * @return the list of messages in the channel
     */
    public List<Message> getMessages() {
        return messages;
    }

    /**
     * Method to add a message to the channel
     * @param message the message to add
     */
    public void addMessage(Message message) {
        this.messages.add(message);
    }

    /**
     * Method to remove a message from the channel
     * @param message the message to remove
     */
    public void removeMessage(Message message) {
        this.messages.remove(message);
    }

    /**
     * Override of the equals method
     * @param o the object to compare with
     * @return true if the two channels are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Channel channel = (Channel) o;
        return channelId == channel.channelId && Objects.equals(channelName, channel.channelName) && Objects.equals(channelImagePath, channel.channelImagePath) && Objects.equals(users, channel.users) && Objects.equals(creator, channel.creator) && Objects.equals(messages, channel.messages);
    }

    /**
     * Override of the hashCode method
     * @return the hash code of the channel
     */
    @Override
    public int hashCode() {
        return Objects.hash(channelId, channelName, channelImagePath, users, creator, messages);
    }
}
