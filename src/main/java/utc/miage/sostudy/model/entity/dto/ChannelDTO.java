package utc.miage.sostudy.model.entity.dto;

import utc.miage.sostudy.model.entity.Channel;

import java.util.List;

/**
 * Data Transfer Object for Channel.
 * This class is used to transfer channel data between different layers of the application.
 * JSON serialization/deserialization is handled by the framework.
 */
public class ChannelDTO {

    /**
     * The id of the channel.
     */
    private int channelId;

    /**
     * The name of the channel.
     */
    private String channelName;

    /**
     * The list of users in the channel.
     */
    private List<UserDTO> users;

    /**
     * Creator of the channel.
     */
    private UserDTO creator;

    /**
     * Default constructor.
     */
    public ChannelDTO() {

    }
    /**
     * Constructor with parameters.
     * @param channelId the id of the channel
     * @param channelName the name of the channel
     * @param users the list of users in the channel
     */
    public ChannelDTO(int channelId, String channelName, List<UserDTO> users, UserDTO creator) {
        this.channelId = channelId;
        this.channelName = channelName;
        this.users = users;
    }

    public ChannelDTO(Channel chan){
        this.channelId = chan.getChannelId();
        this.channelName = chan.getChannelName();
        this.users = chan.getUsers().stream().map(UserDTO::new).toList();
        this.creator = new UserDTO(chan.getCreator());
    }

    /**
     * Getter for the id of the channel.
     * @return the id of the channel
     */
    public int getChannelId() {
        return channelId;
    }

    /**
     * Setter for the id of the channel.
     * @param channelId the id of the channel
     */
    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    /**
     * Getter for the name of the channel.
     * @return the name of the channel
     */
    public String getChannelName() {
        return channelName;
    }

    /**
     * Setter for the name of the channel.
     * @param channelName the name of the channel
     */
    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    /**
     * Getter for the list of users in the channel.
     * @return the list of users in the channel
     */
    public List<UserDTO> getUsers() {
        return users;
    }

    /**
     * Setter for the list of users in the channel.
     * @param users the list of users in the channel
     */
    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }

    /**
     * Getter for the creator of the channel.
     * @return the creator of the channel
     */
    public UserDTO getCreator() {
        return creator;
    }
    /**
     * Setter for the creator of the channel.
     * @param creator the creator of the channel
     */
    public void setCreator(UserDTO creator) {
        this.creator = creator;
    }
}
