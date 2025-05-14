package m1.miage.sostudy.model.entity;

import jakarta.persistence.*;

import java.util.*;

/**
 * Class representing a user in the system.
 * The User class extends the Person class and contains additional attributes and methods specific to users.
 */
@Entity
@Table(name = "users")
public class User extends Person {


    /**
     * Unique identifier for the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idUser;

    /**
     * Biography of the user.
     */
    private String bioUser;

    /**
     * List of the users that the user is following.
     */
    @ManyToMany
    @JoinTable(name = "following",
            joinColumns = @JoinColumn(name = "idUser_following"),
            inverseJoinColumns = @JoinColumn(name = "idUser_followed"))
    private List<User> following = new ArrayList();

    /**
     * List of the users that are following the user.
     */
    @ManyToMany
    @JoinTable(name = "followers",
            joinColumns = @JoinColumn(name = "idUser_followed"),
            inverseJoinColumns = @JoinColumn(name = "idUser_following"))
    private List<User> followers = new ArrayList();

    /**
     * List of the channels that the user is subscribed to.
     */
    @ManyToMany
    @JoinTable(name = "channel_suscribed",
            joinColumns = @JoinColumn(name = "idUser"),
            inverseJoinColumns = @JoinColumn(name = "idChannel"))
    private List<Channel> subscribedChannels = new ArrayList();

    /**
     * List of the channels that the user is creator of.
     */
    @OneToMany
    @JoinColumn(name = "channelId")
    private List<Channel> createdChannels = new ArrayList();

    /**
     * List of the posts that the user has created.
     */
    @OneToMany
    @JoinColumn(name = "postId")
    private List<Post> createdPosts = new ArrayList();

    /**
     * List of the posts that the user has reposted.
     */
    @ManyToMany
    @JoinTable(name = "reposted_posts",
            joinColumns = @JoinColumn(name = "idUser"),
            inverseJoinColumns = @JoinColumn(name = "idChannel"))
    private List<Post> repostedPosts = new ArrayList();

    /**
     * List of the communities that the user is subscribed to.
     */
    @ManyToMany
    @JoinTable(name = "subscribed_communities",
            joinColumns = @JoinColumn(name = "idUser"),
            inverseJoinColumns = @JoinColumn(name = "idCommunity"))
    private List<Community> subscribedCommunities = new ArrayList();

    /**
     * List of the communities that the user is creator of.
     */
    @OneToMany
    @JoinColumn(name = "idCommunity")
    private List<Community> createdCommunities = new ArrayList();

    /**
     * List of the events that the user is subscribed to.
     */
    @ManyToMany
    @JoinTable(name = "subscribed_events",
            joinColumns = @JoinColumn(name = "idUser"),
            inverseJoinColumns = @JoinColumn(name = "idEvent"))
    private List<Event> subscribedEvents = new ArrayList();

    /**
     * List of the events that the user is creator of.
     */
    @OneToMany
    @JoinColumn(name = "idEvent")
    private List<Event> createdEvents = new ArrayList();

    /**
     * List of the messages that the user has sent.
     */
    @OneToMany
    @JoinColumn(name = "messageId")
    private List<Message> sentMessages = new ArrayList();

    /**
     * List of the messages that the user has received.
     */
    public User() {
        super();
    }

    /**
     * Constructor for the User class.
     *
     * @param name                Name of the user.
     * @param firstName           First name of the user.
     * @param email               Email of the user.
     * @param password            Password of the user.
     * @param pseudo              Pseudo of the user.
     * @param birthDate           Birth date of the user.
     * @param urlProfilePicture   URL of the profile picture of the user.
     * @param bioUser             Biography of the user.
     */
    public User(String name, String firstName, String email, String password, String pseudo, String birthDate, String urlProfilePicture, String bioUser) {
        super(name, firstName, email, password,pseudo, birthDate, urlProfilePicture);
        this.bioUser = bioUser;
    }

    /**
     * Getter for the user ID.
     * @return the ID of the user.
     */
    public int getIdUser() {
        return idUser;
    }
    /**
     * Setter for the user ID.
     * @param idUser the new ID of the user.
     */
    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    /**
     * Getter for the biography of the user.
     * @return the biography of the user.
     */
    public String getBioUser() {
        return bioUser;
    }

    /**
     * Setter for the biography of the user.
     * @param bioUser the new biography of the user.
     */
    public void setBioUser(String bioUser) {
        this.bioUser = bioUser;
    }

    /**
     * Getter for the list of users that the user is following.
     * @return the list of users that the user is following.
     */
    public List<User> getFollowing() {
        return following;
    }

    /**
     * Adds a user to the list of users that the user is following.
     * @param user the user to add to the list of users that the user is following.
     */
    public void addFollowing(User user) {
        this.following.add(user);
    }

    /**
     * Removes a user from the list of users that the user is following.
     * @param user the user to remove from the list of users that the user is following.
     */
    public void removeFollowing(User user) {
        this.following.remove(user);
    }


    /**
     * Adds a user to the list of users that are following the user.
     * @param user the user to add to the list of users that are following the user.
     */
    public void addFollowers(User user) {
        this.followers.add(user);
    }

    /**
     * Removes a user from the list of users that are following the user.
     * @param user the user to remove from the list of users that are following the user.
     */
    public void removeFollowers(User user) {
        this.followers.remove(user);
    }

    /**
     * Getter for the list of users that are following the user.
     * @return the list of users that are following the user.
     */
    public List<User> getFollowers() {
        return followers;
    }

    /**
     * Getter for the list of channels that the user is subscribed to.
     * @return the list of channels that the user is subscribed to.
     */
    public List<Channel> getSubscribedChannels() {
        return subscribedChannels;
    }

    /**
     * Adds a channel to the list of channels that the user is subscribed to.
     * @param channel the channel to add to the list of channels that the user is subscribed to.
     */
    public void addSubscribedChannel(Channel channel) {
        this.subscribedChannels.add(channel);
    }

    /**
     * Removes a channel from the list of channels that the user is subscribed to.
     * @param channel the channel to remove from the list of channels that the user is subscribed to.
     */
    public void removeSubscribedChannel(Channel channel) {
        this.subscribedChannels.remove(channel);
    }

    /**
     * Getter for the list of channels that the user is creator of.
     * @return the list of channels that the user is creator of.
     */
    public List<Channel> getCreatedChannels() {
        return createdChannels;
    }

    /**
     * Adds a channel to the list of channels that the user is creator of.
     * @param channel the channel to add to the list of channels that the user is creator of.
     */
    public void addCreatedChannel(Channel channel) {
        this.createdChannels.add(channel);
    }

    /**
     * Removes a channel from the list of channels that the user is creator of.
     * @param channel the channel to remove from the list of channels that the user is creator of.
     */
    public void removeCreatedChannel(Channel channel) {
        this.createdChannels.remove(channel);
    }

    /**
     * Getter for the list of posts that the user has created.
     * @return the list of posts that the user has created.
     */
    public List<Post> getCreatedPosts() {
        return createdPosts;
    }

    /**
     * Adds a post to the list of posts that the user has created.
     * @param post the post to add to the list of posts that the user has created.
     */
    public void addCreatedPost(Post post) {
        this.createdPosts.add(post);
    }

    /**
     * Removes a post from the list of posts that the user has created.
     * @param post the post to remove from the list of posts that the user has created.
     */
    public void removeCreatedPost(Post post) {
        this.createdPosts.remove(post);
    }

    /**
     * Getter for the list of posts that the user has reposted.
     * @return the list of posts that the user has reposted.
     */
    public List<Post> getRepostedPosts() {
        return repostedPosts;
    }

    /**
     * Adds a post to the list of posts that the user has reposted.
     * @param post the post to add to the list of posts that the user has reposted.
     */
    public void addRepostedPost(Post post) {
        this.repostedPosts.add(post);
    }

    /**
     * Removes a post from the list of posts that the user has reposted.
     * @param post the post to remove from the list of posts that the user has reposted.
     */
    public void removeRepostedPost(Post post) {
        this.repostedPosts.remove(post);
    }

    /**
     * Getter for the list of communities that the user is subscribed to.
     * @return the list of communities that the user is subscribed to.
     */
    public List<Community> getSubscribedCommunities() {
        return subscribedCommunities;
    }

    /**
     * Adds a community to the list of communities that the user is subscribed to.
     * @param community the community to add to the list of communities that the user is subscribed to.
     */
    public void addSubscribedCommunity(Community community) {
        this.subscribedCommunities.add(community);
    }

    /**
     * Removes a community from the list of communities that the user is subscribed to.
     * @param community the community to remove from the list of communities that the user is subscribed to.
     */
    public void removeSubscribedCommunity(Community community) {
        this.subscribedCommunities.remove(community);
    }

    /**
     * Getter for the list of communities that the user is creator of.
     * @return the list of communities that the user is creator of.
     */
    public List<Community> getCreatedCommunities() {
        return createdCommunities;
    }

    /**
     * Adds a community to the list of communities that the user is creator of.
     * @param community the community to add to the list of communities that the user is creator of.
     */
    public void addCreatedCommunity(Community community) {
        this.createdCommunities.add(community);
    }

    /**
     * Removes a community from the list of communities that the user is creator of.
     * @param community the community to remove from the list of communities that the user is creator of.
     */
    public void removeCreatedCommunity(Community community) {
        this.createdCommunities.remove(community);
    }

    /**
     * Getter for the list of events that the user is subscribed to.
     * @return the list of events that the user is subscribed to.
     */
    public List<Event> getSubscribedEvents() {
        return subscribedEvents;
    }

    /**
     * Adds an event to the list of events that the user is subscribed to.
     * @param event the event to add to the list of events that the user is subscribed to.
     */
    public void addSubscribedEvent(Event event) {
        this.subscribedEvents.add(event);
    }

    /**
     * Removes an event from the list of events that the user is subscribed to.
     * @param event the event to remove from the list of events that the user is subscribed to.
     */
    public void removeSubscribedEvent(Event event) {
        this.subscribedEvents.remove(event);
    }

    /**
     * Getter for the list of events that the user is creator of.
     * @return the list of events that the user is creator of.
     */
    public List<Event> getCreatedEvents() {
        return createdEvents;
    }

    /**
     * Adds an event to the list of events that the user is creator of.
     * @param event the event to add to the list of events that the user is creator of.
     */
    public void addCreatedEvent(Event event) {
        this.createdEvents.add(event);
    }

    /**
     * Removes an event from the list of events that the user is creator of.
     * @param event the event to remove from the list of events that the user is creator of.
     */
    public void removeCreatedEvent(Event event) {
        this.createdEvents.remove(event);
    }

    /**
     * Getter for the list of messages that the user has sent.
     * @return the list of messages that the user has sent.
     */
    public List<Message> getSentMessages() {
        return sentMessages;
    }

    /**
     * Adds a message to the list of messages that the user has sent.
     * @param message the message to add to the list of messages that the user has sent.
     */
    public void addSentMessage(Message message) {
        this.sentMessages.add(message);
    }

    /**
     * Removes a message from the list of messages that the user has sent.
     * @param message the message to remove from the list of messages that the user has sent.
     */
    public void removeSentMessage(Message message) {
        this.sentMessages.remove(message);
    }

    /**
     * Overrides the equals method to compare two User objects.
     * @param o the object to compare with.
     * @return true if the two objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return idUser == user.idUser;
    }

    /**
     * Overrides the hashCode method to generate a hash code for the User object.
     * @return the hash code of the User object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(idUser);
    }
}
