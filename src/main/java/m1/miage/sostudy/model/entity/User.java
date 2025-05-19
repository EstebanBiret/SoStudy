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
     * static list of all study levels.
     */
    public static final List<String> STUDY_LEVELS = Arrays.asList("BTS", "CPGE1", "CPGE2","BUT1", "BUT2", "BUT3", "L1", "L2", "L3", "M1", "M2", "LP", "Doctorat" );

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
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "following",
            joinColumns = @JoinColumn(name = "id_user_following"),
            inverseJoinColumns = @JoinColumn(name = "id_user_followed"))
    private List<User> following = new ArrayList<>();

    /**
     * List of the users that are following the user.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "followers",
            joinColumns = @JoinColumn(name = "id_user_followed"),
            inverseJoinColumns = @JoinColumn(name = "id_user_following"))
    private List<User> followers = new ArrayList<>();

    /**
     * List of the channels that the user is subscribed to.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "subscribed_channels",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "channel_id"))
    private List<Channel> subscribedChannels = new ArrayList<>();

    /**
     * List of the channels that the user is creator of.
     */
    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL)
    private List<Channel> createdChannels = new ArrayList<>();

    /**
     * List of the posts that the user has created.
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> createdPosts = new ArrayList<>();

    /**
     * List of the communities that the user is subscribed to.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "subscribed_communities",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_community"))
    private List<Community> subscribedCommunities = new ArrayList<>();

    /**
     * List of the communities that the user is creator of.
     */
    @OneToMany(mappedBy = "userCreator", cascade = CascadeType.ALL)
    private List<Community> createdCommunities = new ArrayList<>();

    /**
     * List of the events that the user is subscribed to.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "subscribed_events",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_event"))
    private List<Event> subscribedEvents = new ArrayList<>();

    /**
     * List of the events that the user is creator of.
     */
    @OneToMany(mappedBy = "userCreator", cascade = CascadeType.ALL)
    private List<Event> createdEvents = new ArrayList<>();

    /**
     * List of the messages that the user has sent.
     */
    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    private List<Message> sentMessages = new ArrayList<>();

    /**
     * study level of the user.
     */
    @Column(name = "study_level")
    private String studyLevel;

    /**
     * study domain of the user.
     */
    @Column(name = "study_domain")
    private String studyDomain;

    /**
     * university of the user.
     */
    @Column(name = "university")
    private String university;

    /**
     * Default constructor for the User class.
     */
    public User() {super();}

    /**
     * Constructor for the User class.
     *
     * @param name                Name of the user.
     * @param firstName           First name of the user.
     * @param email               Email of the user.
     * @param password            Password of the user.
     * @param pseudo              Pseudo of the user.
     * @param birthDate           Birth date of the user.
     * @param personImagePath   Path of the image of the profile picture of the user.
     * @param bioUser             Biography of the user.
     * @param studyLevel          Study level of the user.
     * @param studyDomain         Study domain of the user.
     * @param university          University of the user.
     */
    public User(String name, String firstName, String email, String password, String pseudo, String birthDate, String personImagePath, String bioUser, String studyLevel, String studyDomain, String university) {
        super(name, firstName, email, password,pseudo, birthDate, personImagePath);
        this.bioUser = bioUser;
        this.studyLevel = studyLevel;
        this.studyDomain = studyDomain;
        this.university = university;
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
     * Getter for the study level of the user.
     * @return the study level of the user.
     */
    public String getStudyLevel() {
        return studyLevel;
    }

    /**
     * Setter for the study level of the user.
     * @param studyLevel the new study level of the user.
     */
    public void setStudyLevel(String studyLevel) {
        if (STUDY_LEVELS.contains(studyLevel)) {
            this.studyLevel = studyLevel;
        }
    }

    /**
     * Getter for the study domain of the user.
     * @return the study domain of the user.
     */
    public String getStudyDomain() {
        return studyDomain;
    }

    /**
     * Setter for the study domain of the user.
     * @param studyDomain the new study domain of the user.
     */
    public void setStudyDomain(String studyDomain) {
        this.studyDomain = studyDomain;
    }

    /**
     * Getter for the university of the user.
     * @return the university of the user.
     */
    public String getUniversity() {
        return university;
    }

    /**
     * Setter for the university of the user.
     * @param university the new university of the user.
     */
    public void setUniversity(String university) {
        this.university = university;
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