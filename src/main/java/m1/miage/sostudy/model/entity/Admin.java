package m1.miage.sostudy.model.entity;

import jakarta.persistence.*;

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
     * Method to connect the admin
     * @param email the email address of the person
     * @param password the password of the person
     * @return
     */
    @Override
    public boolean seConnecter(String email, String password) {
        if(this.getEmail().equals(email) && this.getPassword().equals(password)) {
            return true;
        }
        return false;
    }

    /**
     * Method to disconnect the admin
     */
    @Override
    public void seDeconnecter() {
        //ToDo : implement the method
    }

    /**
     * Constructor of the Admin class
     * @param name
     * @param firstName
     * @param email
     * @param password
     * @param pseudo
     * @param birthDate
     * @param urlProfilePicture
     * @param idAdmin
     */
    public Admin(int idAdmin, String name, String firstName, String email, String password, String pseudo, String birthDate, String urlProfilePicture) {
        super(name, firstName, email, password, pseudo, birthDate, urlProfilePicture);
        this.idAdmin = idAdmin;
    }

    /**
     * Getter for the idAdmin
     * @return the idAdmin
     */
    public int getIdAdmin() {
        return idAdmin;
    }

    /**
     * @Override
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
     * @Override
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
