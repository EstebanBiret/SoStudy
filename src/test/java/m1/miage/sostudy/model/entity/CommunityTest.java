package m1.miage.sostudy.model.entity;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the Community class.
 */
class CommunityTest {

    private static final int ID_COMMUNITY = 1;
    private static final String NAME_COMMUNITY = "nameCommunity";
    private static final String DATE_CREATION_COMMUNITY = "dateCreationCommunity";
    private static final String COMMUNITY_IMAGE_PATH = "communityImagePath";
    private static final String DESCRIPTION_COMMUNITY = "descriptionCommunity";

    private Community community;

    @BeforeEach
    void setUp() {
        community = new Community(ID_COMMUNITY, NAME_COMMUNITY, DATE_CREATION_COMMUNITY, COMMUNITY_IMAGE_PATH, DESCRIPTION_COMMUNITY);
    }

    /**
     * Tests the equals method.
     */
    @Test
    void testEquals() {
        Community community2 = new Community(ID_COMMUNITY, NAME_COMMUNITY, DATE_CREATION_COMMUNITY, COMMUNITY_IMAGE_PATH, DESCRIPTION_COMMUNITY);
        Assertions.assertEquals(community, community2);
    }

    /**
     * Tests the getCommunityCreationDate method.
     */
    @Test
    void testGetDateCreationCommunity() {
        Assertions.assertEquals(DATE_CREATION_COMMUNITY, community.getCommunityCreationDate());
    }

    /**
     * Tests the getCommunityDescription method.
     */
    void testGetDescriptionCommunity() {
        Assertions.assertEquals(DESCRIPTION_COMMUNITY, community.getCommunityDescription());
    }

    /**
     * Tests the getCommunityId method.
     */
    @Test
    void testGetIdCommunity() {
        Assertions.assertEquals(ID_COMMUNITY, community.getCommunityId());
    }

    /**
     * Tests the getCommunityName method.
     */
    @Test
    void testGetNameCommunity() {
        Assertions.assertEquals(NAME_COMMUNITY, community.getCommunityName());
    }

    /**
     * Tests the getCommunityImagePath method.
     */
    @Test
    void testGetCommunityImagePath() {
        Assertions.assertEquals(COMMUNITY_IMAGE_PATH, community.getCommunityImagePath());
    }

    /**
     * Tests the setDateCreationCommunity method.
     */
    @Test
    void testSetDateCreationCommunity() {
        final String newDate = "newDateCreationCommunity";
        community.setCommunityCreationDate(newDate);
        Assertions.assertEquals(newDate, community.getCommunityCreationDate());
    }

    /**
     * Tests the setCommunityDescription method.
     */
    @Test
    void testSetDescriptionCommunity() {
        final String newDesc = "newDescriptionCommunity";
        community.setCommunityDescription(newDesc);
        Assertions.assertEquals(newDesc, community.getCommunityDescription());
    }

    /**
     * Tests the setCommunityName method.
     */
    @Test
    void testSetNameCommunity() {
        final String newName = "newNameCommunity";
        community.setCommunityName(newName);
        Assertions.assertEquals(newName, community.getCommunityName());
    }

    /**
     * Tests the getUserCreator method.
     */
    @Test
    void testGetUserCreator() {
        final User user = new User("name", "firstName", "email", "password", "login", "dateOfBirth", "personImagePath", "bio");
        community.setUserCreator(user);
        Assertions.assertEquals(user, community.getUserCreator());
    }

    /**
     * Tests the getUsersMembers method.
     */
    @Test
    void testGetUsersMembers() {
        final User user = new User("name", "firstName", "email", "password", "login", "dateOfBirth", "personImagePath", "bio");
        List<User> users = new ArrayList<>(List.of(user));
        community.setUsersMembers(users);
        Assertions.assertEquals(user, community.getUsersMembers().get(0));
    }

    /**
     * Tests the setUserMembers method.
     */
    @Test
    void testSetUserMembers() {
        final List<User> expected = new ArrayList<>();
        expected.add(new User("name", "firstName", "email", "password", "login", "dateOfBirth", "personImagePath", "bio"));
        community.setUsersMembers(expected);
        Assertions.assertEquals(expected, community.getUsersMembers());
    }

    /**
     * Tests the setPost method.
     */
    @Test
    void testSetPost() {
        final Post post = new Post("date", "content", "communityImagePath");
        List<Post> posts = new ArrayList<>(List.of(post));
        community.setPosts(posts);
        Assertions.assertEquals(post, community.getPosts().get(0));
    }

    /**
     * Tests the setCommunityImagePath method.
     */
    @Test
    void testSetCommunityImagePath() {
        final String newPath = "newCommunityImagePath";
        community.setCommunityImagePath(newPath);
        Assertions.assertEquals(newPath, community.getCommunityImagePath());
    }

    /**
     * Tests the hashCode method.
     */
    @Test
    void testHashCode() {
        final Community c1 = new Community(NAME_COMMUNITY, DATE_CREATION_COMMUNITY, COMMUNITY_IMAGE_PATH, DESCRIPTION_COMMUNITY);
        final Community c2 = new Community(NAME_COMMUNITY, DATE_CREATION_COMMUNITY, COMMUNITY_IMAGE_PATH, DESCRIPTION_COMMUNITY);
        Assertions.assertEquals(c1.hashCode(), c2.hashCode());
    }

    /**
     * Tests the addUserMember method.
     */
    @Test
    void testAddUserMember() {
        final User user = new User("name", "firstName", "email", "password", "login", "dateOfBirth", "personImagePath", "bio");
        community.setUsersMembers(new ArrayList<>());
        community.addUserMember(user);
        Assertions.assertEquals(user, community.getUsersMembers().get(0));
    }

    /**
     * Tests the removeUserMember method.
     */
    @Test
    void testRemoveUserMember() {
        final User user = new User("name", "firstName", "email", "password", "login", "dateOfBirth", "personImagePath", "bio");
        community.setUsersMembers(new ArrayList<>(List.of(user)));
        community.removeUserMember(user);
        Assertions.assertTrue(community.getUsersMembers().isEmpty());
    }

    /**
     * Tests the addPost method.
     */
    @Test
    void testAddPost() {
        final Post post = new Post("date", "content", "communityImagePath");
        community.setPosts(new ArrayList<>());
        community.addPost(post);
        Assertions.assertEquals(post, community.getPosts().get(0));
    }

    /**
     * Tests the removePost method.
     */
    @Test
    void testRemovePost() {
        final Post post = new Post("date", "content", "communityImagePath");
        community.setPosts(new ArrayList<>(List.of(post)));
        community.removePost(post);
        Assertions.assertTrue(community.getPosts().isEmpty());
    }
}