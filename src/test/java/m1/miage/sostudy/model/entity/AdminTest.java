package m1.miage.sostudy.model.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Class testing the Admin class
 */
class AdminTest {
    private static final int ID_ADMIN = 1;
    private static final String NAME_ADMIN = "nameAdmin";
    private static final String FIRST_NAME_ADMIN = "firstNameAdmin";
    private static final String EMAIL_ADMIN = "emailAdmin";
    private static final String PASSWORD_ADMIN = "passwordAdmin";
    private static final String PSEUDO_ADMIN = "pseudoAdmin";
    private static final String BIRTH_DATE_ADMIN = "birthDateAdmin";
    private static final String PERSON_IMAGE_PATH_ADMIN = "personImagePathAdmin";
    private static final Admin ADMIN = new Admin(ID_ADMIN, NAME_ADMIN, FIRST_NAME_ADMIN, EMAIL_ADMIN, PASSWORD_ADMIN, PSEUDO_ADMIN, BIRTH_DATE_ADMIN, PERSON_IMAGE_PATH_ADMIN);
    
    /**
     * test the equals method
     */
    @Test
    void testEquals() {
        final Admin admin1 = new Admin(ID_ADMIN,NAME_ADMIN, FIRST_NAME_ADMIN, EMAIL_ADMIN, PASSWORD_ADMIN, PSEUDO_ADMIN, BIRTH_DATE_ADMIN, PERSON_IMAGE_PATH_ADMIN);
        final Admin admin2 = new Admin(ID_ADMIN, NAME_ADMIN, FIRST_NAME_ADMIN, EMAIL_ADMIN, PASSWORD_ADMIN, PSEUDO_ADMIN, BIRTH_DATE_ADMIN, PERSON_IMAGE_PATH_ADMIN);
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
        final Admin admin1 = new Admin(ID_ADMIN, NAME_ADMIN, FIRST_NAME_ADMIN, EMAIL_ADMIN, PASSWORD_ADMIN, PSEUDO_ADMIN, BIRTH_DATE_ADMIN, PERSON_IMAGE_PATH_ADMIN);
        final Admin admin2 = new Admin(ID_ADMIN, NAME_ADMIN, FIRST_NAME_ADMIN, EMAIL_ADMIN, PASSWORD_ADMIN, PSEUDO_ADMIN, BIRTH_DATE_ADMIN, PERSON_IMAGE_PATH_ADMIN);
        Assertions.assertEquals(admin1.hashCode(), admin2.hashCode());
    }

    /**
     * test the deleteUser method
     */
    @Test
    void testDeleteUser() {
        //ToDo : implement the test
    }

    /**
     * test the deletePost method
     */
    @Test
    void testDeletePost() {
        //ToDo : implement the test
    }

    /**
     * test the sendGlobalMessage method
     */
    @Test
    void testSendGlobalMessage() {
        //ToDo : implement the test
    }
}
