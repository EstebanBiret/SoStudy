package m1.miage.sostudy.model.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Class testing the Community class
 */
class CommunityTest {

    private static final int ID_COMMUNITY = 1;
    private static final String NAME_COMMUNITY = "nameCommunity";
    private static final String DATE_CREATION_COMMUNITY = "dateCreationCommunity";
    private static final String URL_COMMUNITY_PICTURE = "urlCommunityPicture";
    private static final String DESCRIPTION_COMMUNITY = "descriptionCommunity";
    private static final Community COMMUNITY = new Community(ID_COMMUNITY, NAME_COMMUNITY, DATE_CREATION_COMMUNITY, URL_COMMUNITY_PICTURE, DESCRIPTION_COMMUNITY);

    /**
     * test the equals method
     */
    @Test
    void testEquals() {
        final Community community1 = new Community(NAME_COMMUNITY, DATE_CREATION_COMMUNITY, URL_COMMUNITY_PICTURE, DESCRIPTION_COMMUNITY);
        final Community community2 = new Community(NAME_COMMUNITY, DATE_CREATION_COMMUNITY, URL_COMMUNITY_PICTURE, DESCRIPTION_COMMUNITY);
        Assertions.assertEquals(community1, community2);
    }

    /**
     * test the getDateCreationCommunity method
     */
    @Test
    void testGetDateCreationCommunity() {
        final Community com = new Community(ID_COMMUNITY, NAME_COMMUNITY, DATE_CREATION_COMMUNITY, URL_COMMUNITY_PICTURE, DESCRIPTION_COMMUNITY);
        final String expected = DATE_CREATION_COMMUNITY;
        final String actual = com.getCommunityCreationDate();
        Assertions.assertEquals(expected, actual);
    }

    /**
     * test the getDescriptionCommunity method
     */
    @Test
    void testGetDescriptionCommunity() {
        final String expected = DESCRIPTION_COMMUNITY;
        final String actual = COMMUNITY.getCommunityDescription();
        Assertions.assertEquals(expected,  actual);
    }

    /**
     * test the getIdCommunity method
     */
    @Test
    void testGetIdCommunity() {
        final int expected = ID_COMMUNITY;
        final int actual = COMMUNITY.getCommunityId();
        Assertions.assertEquals(expected,  actual);
    }

    /**
     * test the getNameCommunity method
     */
    @Test
    void testGetNameCommunity() {
        final String expected = NAME_COMMUNITY;
        final String actual = COMMUNITY.getCommunityName();
        Assertions.assertEquals(expected,  actual);
    }

    /**
     * test the getUrlCommunityPicture method
     */
    @Test
    void testGetUrlCommunityPicture() {
        final String expected = URL_COMMUNITY_PICTURE;
        final String actual = COMMUNITY.getCmmunityImagePath();
        Assertions.assertEquals(expected,  actual);
    }

    /**
     * test the setDateCreationCommunity method
     */
    @Test
    void testSetDateCreationCommunity() {
        final String expected = "newDateCreationCommunity";
        COMMUNITY.setCommunityCreationDate(expected);
        final String actual = COMMUNITY.getCommunityCreationDate();
        Assertions.assertEquals(expected,  actual);
    }

    /**
     * test the setDescriptionCommunity method
     */
    @Test
    void testSetDescriptionCommunity() {
        final String expected = "newDescriptionCommunity";
        COMMUNITY.setCommunityDescription(expected);
        final String actual = COMMUNITY.getCommunityDescription();
        Assertions.assertEquals(expected,  actual);
    }

    /**
     * test the setIdCommunity method
     */
    @Test
    void testSetNameCommunity() {
        final String expected = "newNameCommunity";
        COMMUNITY.setCommunityName(expected);
        final String actual = COMMUNITY.getCommunityName();
        Assertions.assertEquals(expected,  actual);
    }

    /**
     * test the setUrlCommunityPicture method
     */
    @Test
    void testSetUrlCommunityPicture() {
        final String expected = "newUrlCommunityPicture";
        COMMUNITY.setCmmunityImagePath(expected);
        final String actual = COMMUNITY.getCmmunityImagePath();
        Assertions.assertEquals(expected,  actual);
    }

    /**
     * test the hashCode method
     */
    @Test
    void testHashCode() {
        final Community community1 = new Community(NAME_COMMUNITY, DATE_CREATION_COMMUNITY, URL_COMMUNITY_PICTURE, DESCRIPTION_COMMUNITY);
        final Community community2 = new Community(NAME_COMMUNITY, DATE_CREATION_COMMUNITY, URL_COMMUNITY_PICTURE, DESCRIPTION_COMMUNITY);
        Assertions.assertEquals(community1.hashCode(), community2.hashCode());
    }
}
