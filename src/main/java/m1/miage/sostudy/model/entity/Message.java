package m1.miage.sostudy.model.entity;

import jakarta.persistence.*;

/**
 * Message entity class representing a message in the system.
 */
@Entity
@Table(name = "message")
public class Message {

    /**
     * Unique identifier for the message.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idMessage;

    /**
     * Content of the message.
     */
    private String content;

    /**
     * Date when the message was sent.
     */
    private String dateMessage;

    /**
     * Channel associated with the message.
     */
    @ManyToOne
    @JoinColumn(name = "idChannel")
    private Channel channel;

    /**
     * Empty constructor for the Message class.
     */
    public Message() {
    }
    /**
     * Constructor for the Message class.
     *
     * @param content      Content of the message.
     * @param dateMessage  Date when the message was sent.
     */
    public Message(String content, String dateMessage) {
        this.content = content;
        this.dateMessage = dateMessage;
    }

    /**
     * Getter for the message ID.
     * @return the ID of the message.
     */
    public int getIdMessage() {
        return idMessage;
    }

    /**
     * Getter for the channel associated with the message.
     * @return the channel associated with the message.
     */
    public Channel getChannel() {
        return channel;
    }

    /**
     * Setter for the channel associated with the message.
     * @param channel the new channel associated with the message.
     */
    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    /**
     * Getter for the content of the message.
     * @return the content of the message.
     */
    public String getContent() {
        return content;
    }

    /**
     * Setter for the content of the message.
     * @param content the new content of the message.
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Getter for the date when the message was sent.
     * @return the date when the message was sent.
     */
    public String getDateMessage() {
        return dateMessage;
    }

    /**
     * Setter for the date when the message was sent.
     * @param dateMessage the new date when the message was sent.
     */
    public void setDateMessage(String dateMessage) {
        this.dateMessage = dateMessage;
    }

    /**
     * Override of the equals method.
     * @param o the object to compare with.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;
        Message message = (Message) o;
        return idMessage == message.idMessage && content.equals(message.content) && dateMessage.equals(message.dateMessage);
    }
}
