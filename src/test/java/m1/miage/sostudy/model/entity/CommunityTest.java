package m1.miage.sostudy.model.entity;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

    @Test
    void testEquals() {
        final Community c1 = new Community(NAME_COMMUNITY, DATE_CREATION_COMMUNITY, COMMUNITY_IMAGE_PATH, DESCRIPTION_COMMUNITY);
        final Community c2 = new Community(NAME_COMMUNITY, DATE_CREATION_COMMUNITY, COMMUNITY_IMAGE_PATH, DESCRIPTION_COMMUNITY);
        Assertions.assertEquals(c1, c2);
    }

    @Test
    void testGetDateCreationCommunity() {
        Assertions.assertEquals(DATE_CREATION_COMMUNITY, community.getCommunityCreationDate());
    }

    @Test
    void testGetDescriptionCommunity() {
        Assertions.assertEquals(DESCRIPTION_COMMUNITY, community.getCommunityDescription());
    }

    @Test
    void testGetIdCommunity() {
        Assertions.assertEquals(ID_COMMUNITY, community.getCommunityId());
    }

    @Test
    void testGetNameCommunity() {
        Assertions.assertEquals(NAME_COMMUNITY, community.getCommunityName());
    }

    @Test
    void testGetCommunityImagePath() {
        Assertions.assertEquals(COMMUNITY_IMAGE_PATH, community.getCommunityImagePath());
    }

    @Test
    void testSetDateCreationCommunity() {
        final String newDate = "newDateCreationCommunity";
        community.setCommunityCreationDate(newDate);
        Assertions.assertEquals(newDate, community.getCommunityCreationDate());
    }

    @Test
    void testSetDescriptionCommunity() {
        final String newDesc = "newDescriptionCommunity";
        community.setCommunityDescription(newDesc);
        Assertions.assertEquals(newDesc, community.getCommunityDescription());
    }

    @Test
    void testSetNameCommunity() {
        final String newName = "newNameCommunity";
        community.setCommunityName(newName);
        Assertions.assertEquals(newName, community.getCommunityName());
    }

    @Test
    void testGetUserCreator() {
        final User user = new User("name", "firstName", "email", "password", "login", "dateOfBirth", "personImagePath", "bio");
        community.setUserCreator(user);
        Assertions.assertEquals(user, community.getUserCreator());
    }

    @Test
    void testGetUsersMembers() {
        final User user = new User("name", "firstName", "email", "password", "login", "dateOfBirth", "personImagePath", "bio");
        List<User> users = new ArrayList<>(List.of(user));
        community.setUsersMembers(users);
        Assertions.assertEquals(user, community.getUsersMembers().get(0));
    }

    @Test
    void testSetUserMembers() {
        final List<User> expected = new ArrayList<>();
        expected.add(new User("name", "firstName", "email", "password", "login", "dateOfBirth", "personImagePath", "bio"));
        community.setUsersMembers(expected);
        Assertions.assertEquals(expected, community.getUsersMembers());
    }

    @Test
    void testSetPost() {
        final Post post = new Post("date", "content", "communityImagePath");
        List<Post> posts = new ArrayList<>(List.of(post));
        community.setPosts(posts);
        Assertions.assertEquals(post, community.getPosts().get(0));
    }

    @Test
    void testSetCommunityImagePath() {
        final String newPath = "newCommunityImagePath";
        community.setCommunityImagePath(newPath);
        Assertions.assertEquals(newPath, community.getCommunityImagePath());
    }

    @Test
    void testHashCode() {
        final Community c1 = new Community(NAME_COMMUNITY, DATE_CREATION_COMMUNITY, COMMUNITY_IMAGE_PATH, DESCRIPTION_COMMUNITY);
        final Community c2 = new Community(NAME_COMMUNITY, DATE_CREATION_COMMUNITY, COMMUNITY_IMAGE_PATH, DESCRIPTION_COMMUNITY);
        Assertions.assertEquals(c1.hashCode(), c2.hashCode());
    }

    @Test
    void testAddUserMember() {
        final User user = new User("name", "firstName", "email", "password", "login", "dateOfBirth", "personImagePath", "bio");
        community.setUsersMembers(new ArrayList<>());
        community.addUserMember(user);
        Assertions.assertEquals(user, community.getUsersMembers().get(0));
    }

    @Test
    void testRemoveUserMember() {
        final User user = new User("name", "firstName", "email", "password", "login", "dateOfBirth", "personImagePath", "bio");
        community.setUsersMembers(new ArrayList<>(List.of(user)));
        community.removeUserMember(user);
        Assertions.assertTrue(community.getUsersMembers().isEmpty());
    }

    @Test
    void testAddPost() {
        final Post post = new Post("date", "content", "communityImagePath");
        community.setPosts(new ArrayList<>());
        community.addPost(post);
        Assertions.assertEquals(post, community.getPosts().get(0));
    }

    @Test
    void testRemovePost() {
        final Post post = new Post("date", "content", "communityImagePath");
        community.setPosts(new ArrayList<>(List.of(post)));
        community.removePost(post);
        Assertions.assertTrue(community.getPosts().isEmpty());
    }
}