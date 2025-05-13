package m1.miage.sostudy.model.entity;

import jakarta.persistence.*;

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
    private int idChannel;

    /**
     * Name of the channel
     */
    private String channelName;

    /**
     * Description of the channel
     */
    private String urlChannelPicture;

    // TO DO : ADD USER CREATORS AND LIST OF USER IN THE CHANNEL

    /**
     * Empty constructor of the Channel class
     */
    public Channel() {
    }

    /**
     * Constructor of the Channel class
     * @param channelName
     * @param urlChannelPicture
     */
    public Channel(String channelName, String urlChannelPicture) {
        this.channelName = channelName;
        this.urlChannelPicture = urlChannelPicture;
    }

    /**
     * Getter for the id of the channel
     * @return the id of the channel
     */
    public int getIdChannel() {
        return idChannel;
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
     * Getter for the url of the channel picture
     * @return the url of the channel picture
     */
    public String getUrlChannelPicture() {
        return urlChannelPicture;
    }

    /**
     * Setter for the url of the channel picture
     * @param urlChannelPicture the url of the channel picture
     */
    public void setUrlChannelPicture(String urlChannelPicture) {
        this.urlChannelPicture = urlChannelPicture;
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
        return idChannel == channel.idChannel && Objects.equals(channelName, channel.channelName) && Objects.equals(urlChannelPicture, channel.urlChannelPicture);
    }

}
