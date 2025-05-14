package m1.miage.sostudy.model.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Test class for the Post entity
 */
public class PostTest {

    /**
     * Test the constructor with parameters
     */
    @Test
    void testConstructorWithParameters() {
        String date = "2024-01-01";
        String content = "Hello world";
        String mediaPath = "http://example.com/media.png";

        Post post = new Post(date, content, mediaPath);

        assertEquals(date, post.getPostPublicationDate());
        assertEquals(content, post.getPostContent());
        assertEquals(mediaPath, post.getPostMediaPath());
    }

    /**
     * Test the equals method
     */
    @Test
    void testEquals() {
        Post post1 = new Post();
        Post post2 = new Post();
        post1.setPostId(1);
        post2.setPostId(1);
        assertEquals(post1, post2);
    }

    /**
     * Test the equals method with null
     */
    @Test
    void testEqualsWithNull() {
        Post post = new Post();
        assertNotEquals(null, post);
    }

    /**
     * Test the equals method with different class
     */
    @Test
    void testEqualsWithDifferentClass() {
        Post post = new Post();
        assertNotEquals("some string", post);
    }
    
    /**
     * Test the equals method with different id
     */
    @Test
    void testEqualsWithDifferentId() {
        Post post1 = new Post();
        post1.setPostId(1);
        Post post2 = new Post();
        post2.setPostId(2);
        assertNotEquals(post1, post2);
    }

    /**
     * Test the equals method with null id and non null id
     */
    @Test
    void testEqualsWithNullIdAndNonNullId() {
        Post post1 = new Post();
        post1.setPostId(null);
        Post post2 = new Post();
        post2.setPostId(1);
        assertNotEquals(post1, post2);
    }

    /**
     * Test the equals method with both null ids
     */
    @Test
    void testEqualsWithBothNullIds() {
        Post post1 = new Post();
        Post post2 = new Post();
        assertNotEquals(post1, post2);
    }

    /**
     * Test the hashCode method
     */
    @Test
    void testHashCode() {
        Post post = new Post();
        post.setPostId(1);
        assertEquals(post.hashCode(), 1);
    }

    /**
     * Test the getCommentFather method
     */
    @Test
    void testGetCommentFather() {
        Post post = new Post();
        Post commentFather = new Post();
        post.setCommentFather(commentFather);
        assertEquals(post.getCommentFather(), commentFather);
    }

    /**
     * Test the getComments method
     */
    @Test
    void testGetComments() {
        Post post = new Post();
        List<Post> comments = new ArrayList<>();
        post.setComments(comments);
        assertEquals(post.getComments(), comments);
    }

    /**
     * Test the getContent method
     */
    @Test
    void testGetContent() {
        Post post = new Post();
        post.setPostContent("content");
        assertEquals(post.getPostContent(), "content");
    }

    /**
     * Test the getMediaPath method
     */
    @Test
    void testGetMediaPath() {
        Post post = new Post();
        post.setPostMediaPath("mediaPath");
        assertEquals(post.getPostMediaPath(), "mediaPath");
    }

    /**
     * Test the getPostId method
     */
    @Test
    void testGetPostId() {
        Post post = new Post();
        post.setPostId(1);
        assertEquals(post.getPostId(), 1);
    }

    /**
     * Test the getPublicationDate method
     */
    @Test
    void testGetPublicationDate() {
        Post post = new Post();
        post.setPostPublicationDate("publicationDate");
        assertEquals(post.getPostPublicationDate(), "publicationDate");
    }

    /**
     * Test the setCommentFather method
     */
    @Test
    void testSetCommentFather() {
        Post post = new Post();
        Post commentFather = new Post();
        post.setCommentFather(commentFather);
        assertEquals(post.getCommentFather(), commentFather);
    }

    /**
     * Test the setComments method
     */
    @Test
    void testSetComments() {
        Post post = new Post();
        List<Post> comments = new ArrayList<>();
        post.setComments(comments);
        assertEquals(post.getComments(), comments);
    }

    /**
     * Test the setContent method
     */
    @Test
    void testSetContent() {
        Post post = new Post();
        post.setPostContent("content");
        assertEquals(post.getPostContent(), "content");
    }

    /**
     * Test the setMediaPath method
     */
    @Test
    void testSetMediaPath() {
        Post post = new Post();
        post.setPostMediaPath("mediaPath");
        assertEquals(post.getPostMediaPath(), "mediaPath");
    }

    /**
     * Test the setPublicationDate method
     */
    @Test
    void testSetPublicationDate() {
        Post post = new Post();
        post.setPostPublicationDate("publicationDate");
        assertEquals(post.getPostPublicationDate(), "publicationDate");
    }

    /**
     * Test the setCommunity method
     */
    @Test
    void testSetCommunity() {
        Post post = new Post();
        Community community = new Community();
        post.setCommunity(community);
        assertEquals(post.getCommunity(), community);
    }

    /**
     * Test the setUser method
     */
    @Test
    void testSetUser() {
        Post post = new Post();
        User user = new User();
        post.setUser(user);
        assertEquals(post.getUser(), user);
    }

    /**
     * Test the addRepost method
     */
    @Test
    void testAddRepost() {
        Post post = new Post();
        User user = new User();
        post.addRepost(user);
        assertEquals(post.getReposts().size(), 1);
    }

    /**
     * Test the removeRepost method
     */
    @Test
    void testRemoveRepost() {
        Post post = new Post();
        User user = new User();
        post.addRepost(user);
        post.removeRepost(user);
        assertEquals(post.getReposts().size(), 0);
    }
}