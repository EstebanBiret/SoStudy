package m1.miage.sostudy.model.entity;

import jakarta.persistence.*;

//TODO voir si on supprime car admin = user avec le pseudo admin

/**
 * class representing an admin
 */
@Entity
@Table(name = "admin")
public class Admin extends Person {

    /**
     * ID of the admin
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idAdmin;

    /**
     * Constructor of the Admin class
     */
    public Admin() {
        super();
    }

    /**
     * Constructor of the Admin class
     * @param name name of the admin
     * @param firstName first name of the admin
     * @param email email address of the admin
     * @param password password of the admin
     * @param pseudo pseudo of the admin
     * @param birthDate birthdate of the admin
     * @param personImagePath path of the image of the admin
     * @param idAdmin id of the admin
     */
    public Admin(int idAdmin, String name, String firstName, String email, String password, String pseudo, String birthDate, String personImagePath) {
        super(name, firstName, email, password, pseudo, birthDate, personImagePath);
        this.idAdmin = idAdmin;
    }

    /**
     * Method to delete a user
     * @param idUser the id of the user to delete
     * @return the user if the deletion is successful, null otherwise
     */
    public User deleteUser(int idUser) {
        //ToDo : implement the method
        return null;
    }

    /**
     * Method to delete a post
     * @param idPost the id of the post to delete
     * @return true if the deletion is successful, false otherwise
     */
    public boolean deletePost(int idPost) {
        //ToDo : implement the method
        return false;
    }


    /**
     * Method to send a message to all users
     * @param message the message to send
     * @return true if the message is sent, false otherwise
     */
    public boolean sendGlobalMessage(Message message) {
        //ToDo : implement the method
        return false;
    }

    /**
     * Getter for the idAdmin
     * @return the idAdmin
     */
    public int getIdAdmin() {
        return idAdmin;
    }

    /**
     * Method hashCode
     * @return the hashCode of the admin
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + idAdmin;
        return result;
    }

    /**
     * Method equals
     * @param obj the object to compare with
     * @return true if the two objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Admin other = (Admin) obj;
        if (idAdmin != other.idAdmin)
            return false;
        return true;
    }

}