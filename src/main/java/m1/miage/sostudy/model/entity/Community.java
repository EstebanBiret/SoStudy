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
    @ManyToMany
    @JoinTable(name = "community_users", joinColumns = @JoinColumn(name = "id_community"), inverseJoinColumns = @JoinColumn(name = "id_user"))
    private List<User> usersMembers = new ArrayList<>();

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
     * @param communityName
     * @param communityCreationDate
     * @param communityImagePath
     * @param communityDescription
     */
    public Community(String communityName, String communityCreationDate, String communityImagePath, String communityDescription) {
        this.communityName = communityName;
        this.communityCreationDate = communityCreationDate;
        this.communityImagePath = communityImagePath;
        this.communityDescription = communityDescription;
    }

    /**
     * Constructor of the Community class
     * @param communityId
     * @param communityName
     * @param communityCreationDate
     * @param communityImagePath
     * @param communityDescription
     */
    public Community(int communityId, String communityName, String communityCreationDate, String communityImagePath, String communityDescription) {
        this.communityId = communityId;
        this.communityName = communityName;
        this.communityCreationDate = communityCreationDate;
        this.communityImagePath = communityImagePath;
        this.communityDescription = communityDescription;
        
    }

    /**
     * Getters and Setters
     */
    public int getCommunityId() {
        return communityId;
    }

    /**
     * Getters and Setters
     */
    public String getCommunityName() {
        return communityName;
    }

    /**
     * Getters and Setters
     */
    public void setCommunityName(String nameCommunity) {
        this.communityName = nameCommunity;
    }

    /**
     * Getters and Setters
     */
    public String getCommunityCreationDate() {
        return this.communityCreationDate;
    }

    /**
     * Getters and Setters
     */
    public void setCommunityCreationDate(String dateCreationCommunity) {
        this.communityCreationDate = dateCreationCommunity;
    }

    /**
     * Getters and Setters
     */
    public String getCommunityImagePath() {
        return communityImagePath;
    }

    /**
     * Getters and Setters
     */
    public void setCommunityImagePath(String communityImagePath) {
        this.communityImagePath = communityImagePath;
    }

    /**
     * Getters and Setters
     */
    public String getCommunityDescription() {
        return communityDescription;
    }

    /**
     * Getters and Setters
     */
    public void setCommunityDescription(String descriptionCommunity) {
        this.communityDescription = descriptionCommunity;
    }

    /**
     * Getters and Setters
     */
    public User getUserCreator() {
        return userCreator;
    }

    /**
     * Getters and Setters
     */
    public void setUserCreator(User userCreator) {
        this.userCreator = userCreator;
    }

    /**
     * Getters and Setters
     */
    public List<User> getUsersMembers() {
        return usersMembers;
    }

    /**
     * Getters and Setters
     */
    public void setUsersMembers(List<User> usersMembers) {
        this.usersMembers = usersMembers;
    }

    /**
     * Add a user to the list of members of the community
     * @param user the user to add
     */
    public void addUserMember(User user) {
        this.usersMembers.add(user);
    }

    /**
     * Remove a user from the list of members of the community
     * @param user the user to remove
     */
    public void removeUserMember(User user) {
        this.usersMembers.remove(user);
    }

    /**
     * Getters and Setters
     */
    public List<Post> getPosts() {
        return posts;
    }

    /**
     * Getters and Setters
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
        result = prime * result + ((usersMembers == null) ? 0 : usersMembers.hashCode());
        result = prime * result + ((posts == null) ? 0 : posts.hashCode());
        return result;
    }

    /**
     * Override the equals method
     * @param obj the object to compare with
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