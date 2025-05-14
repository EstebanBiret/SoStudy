package m1.miage.sostudy.model.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

/**
 * Class testing the Admin class
 */
public class AdminTest {
    private static final int ID_ADMIN = 1;
    private static final String NAME_ADMIN = "nameAdmin";
    private static final String FIRST_NAME_ADMIN = "firstNameAdmin";
    private static final String EMAIL_ADMIN = "emailAdmin";
    private static final String PASSWORD_ADMIN = "passwordAdmin";
    private static final String PSEUDO_ADMIN = "pseudoAdmin";
    private static final String BIRTH_DATE_ADMIN = "birthDateAdmin";
    private static final String URL_PROFILE_PICTURE_ADMIN = "urlProfilePictureAdmin";
    private static final Admin ADMIN = new Admin(ID_ADMIN, NAME_ADMIN, FIRST_NAME_ADMIN, EMAIL_ADMIN, PASSWORD_ADMIN, PSEUDO_ADMIN, BIRTH_DATE_ADMIN, URL_PROFILE_PICTURE_ADMIN);
    
    /**
     * test the equals method
     */
    @Test
    void testEquals() {
        final Admin admin1 = new Admin(ID_ADMIN,NAME_ADMIN, FIRST_NAME_ADMIN, EMAIL_ADMIN, PASSWORD_ADMIN, PSEUDO_ADMIN, BIRTH_DATE_ADMIN, URL_PROFILE_PICTURE_ADMIN);
        final Admin admin2 = new Admin(ID_ADMIN, NAME_ADMIN, FIRST_NAME_ADMIN, EMAIL_ADMIN, PASSWORD_ADMIN, PSEUDO_ADMIN, BIRTH_DATE_ADMIN, URL_PROFILE_PICTURE_ADMIN);
        Assertions.assertEquals(admin1, admin2);
    }

    /**
     * test the getIdAdmin method
     */
    @Test
    void testGetIdAdmin() {
        final int expected = ID_ADMIN;
        final int actual = ADMIN.getIdAdmin();
        Assertions.assertEquals(expected, actual);
    }

    /**
     * test the hashCode method
     */
    @Test
    void testHashCode() {
        final Admin admin1 = new Admin(ID_ADMIN, NAME_ADMIN, FIRST_NAME_ADMIN, EMAIL_ADMIN, PASSWORD_ADMIN, PSEUDO_ADMIN, BIRTH_DATE_ADMIN, URL_PROFILE_PICTURE_ADMIN);
        final Admin admin2 = new Admin(ID_ADMIN, NAME_ADMIN, FIRST_NAME_ADMIN, EMAIL_ADMIN, PASSWORD_ADMIN, PSEUDO_ADMIN, BIRTH_DATE_ADMIN, URL_PROFILE_PICTURE_ADMIN);
        Assertions.assertEquals(admin1.hashCode(), admin2.hashCode());
    }

    /**
     * test the seConnecter method
     */
    @Test
    void testSeConnecter() {
        final String email = "emailAdmin";
        final String password = "passwordAdmin";
        final boolean expected = true;
        final boolean actual = ADMIN.seConnecter(email, password);
        Assertions.assertEquals(expected, actual);
    }

    /**
     * test the seDeconnecter method
     */
    @Test
    void testSeDeconnecter() {
        // ToDo : implement the test
        Assert.isTrue(true, "ToDo : implement the test");
    }
}
