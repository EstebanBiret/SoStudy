package m1.miage.sostudy.model.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a community
 */
@Entity
@Table(name = "community")
public class Community {

    /**
     * ID of the community
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int communityId;

    /**
     * Name of the community
     */
    private String communityName;

    /**
     * Date of creation of the community
     */
    private String communityCreationDate;

    /**
     * Path of the image of the community
     */
    private String communityImagePath;

    /**
     * Description of the community
     */
    private String communityDescription;

    /**
     * User who created the community
     */
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User userCreator;

    /**
     * List of users who are members of the community
     */
    @ManyToMany(mappedBy = "subscribedCommunities",fetch = FetchType.EAGER)
    private List<User> users = new ArrayList<>();

    /**
     * List of posts of the community
     */
    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

    /**
     * Constructor of the Community class
     */
    public Community() {}

    /**
     * Constructor of the Community class
     * @param communityName the name of the community
     * @param communityCreationDate the creation date of the community
     * @param communityImagePath the path of the image of the community
     * @param communityDescription the description of the community
     */
    public Community(String communityName, String communityCreationDate, String communityImagePath, String communityDescription) {
        this.communityName = communityName;
        this.communityCreationDate = communityCreationDate;
        this.communityImagePath = communityImagePath;
        this.communityDescription = communityDescription;
    }

    /**
     * Constructor of the Community class
     * @param communityId the id of the community
     * @param communityName the name of the community
     * @param communityCreationDate the creation date of the community
     * @param communityImagePath the path of the image of the community
     * @param communityDescription the description of the community
     */
    public Community(int communityId, String communityName, String communityCreationDate, String communityImagePath, String communityDescription) {
        this.communityId = communityId;
        this.communityName = communityName;
        this.communityCreationDate = communityCreationDate;
        this.communityImagePath = communityImagePath;
        this.communityDescription = communityDescription;
        
    }

    /**
     * Get the id of the community
     * @return the id of the community
     */
    public int getCommunityId() {
        return communityId;
    }

    /**
     * Get the name of the community
     * @return the name of the community
     */
    public String getCommunityName() {
        return communityName;
    }

    /**
     * Set the name of the community
     * @param nameCommunity the name of the community
     */
    public void setCommunityName(String nameCommunity) {
        this.communityName = nameCommunity;
    }

    /**
     * Get the creation date of the community
     * @return the creation date of the community
     */
    public String getCommunityCreationDate() {
        return this.communityCreationDate;
    }

    /**
     * Set the creation date of the community
     * @param dateCreationCommunity the creation date of the community
     */
    public void setCommunityCreationDate(String dateCreationCommunity) {
        this.communityCreationDate = dateCreationCommunity;
    }

    /**
     * Get the path of the image of the community
     * @return the path of the image of the community
     */
    public String getCommunityImagePath() {
        return communityImagePath;
    }

    /**
     * Set the path of the image of the community
     * @param communityImagePath the path of the image of the community
     */
    public void setCommunityImagePath(String communityImagePath) {
        this.communityImagePath = communityImagePath;
    }

    /**
     * Get the description of the community
     * @return the description of the community
     */
    public String getCommunityDescription() {
        return communityDescription;
    }

    /**
     * Set the description of the community
     * @param descriptionCommunity the description of the community
     */
    public void setCommunityDescription(String descriptionCommunity) {
        this.communityDescription = descriptionCommunity;
    }

    /**
     * Get the user who created the community
     * @return the user who created the community
     */
    public User getUserCreator() {
        return userCreator;
    }

    /**
     * Set the user who created the community
     * @param userCreator the user who created the community
     */
    public void setUserCreator(User userCreator) {
        this.userCreator = userCreator;
    }

    /**
     * Get the list of users who are members of the community
     * @return the list of users who are members of the community
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     * Set the list of users who are members of the community
     * @param usersMembers the list of users who are members of the community
     */
    public void setUsers(List<User> usersMembers) {
        this.users = usersMembers;
    }

    /**
     * Add a user to the list of members of the community
     * @param user the user to add
     */
    public void addUser(User user) {
        this.users.add(user);
    }

    /**
     * Remove a user from the list of members of the community
     * @param user the user to remove
     */
    public void removeUser(User user) {
        this.users.remove(user);
    }

    /**
     * Get the list of posts of the community
     * @return the list of posts of the community
     */
    public List<Post> getPosts() {
        return posts;
    }

    /**
     * Set the list of posts of the community
     * @param posts the list of posts of the community
     */
    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    /**
     * Add a post to the list of posts of the community
     * @param post the post to add
     */
    public void addPost(Post post) {
        this.posts.add(post);
    }

    /**
     * Remove a post from the list of posts of the community
     * @param post the post to remove
     */
    public void removePost(Post post) {
        this.posts.remove(post);
    }

    /**
     * Override the hashCode method
     * @return the hash code of the community
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + communityId;
        result = prime * result + ((communityName == null) ? 0 : communityName.hashCode());
        result = prime * result + ((communityCreationDate == null) ? 0 : communityCreationDate.hashCode());
        result = prime * result + ((communityImagePath == null) ? 0 : communityImagePath.hashCode());
        result = prime * result + ((communityDescription == null) ? 0 : communityDescription.hashCode());
        result = prime * result + ((userCreator == null) ? 0 : userCreator.hashCode());
        result = prime * result + ((users == null) ? 0 : users.hashCode());
        result = prime * result + ((posts == null) ? 0 : posts.hashCode());
        return result;
    }

    /**
     * Override the equals method
     * @param o the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Community community = (Community) o;
        return communityId == community.communityId;
    }
}