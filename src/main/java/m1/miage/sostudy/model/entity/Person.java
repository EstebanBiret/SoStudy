package m1.miage.sostudy.model.entity;

import jakarta.persistence.MappedSuperclass;

/**
 * Abstract class representing a person
 */
@MappedSuperclass
public abstract class Person {

    /**
     * Name of the person
     */
    private String name;

    /**
     * First name of the person
     */
    private String firstName;

    /**
     * Email address of the person
     */
    private String email;

    /**
     * Password of the person
     */
    private String password;

    /**
     * Pseudo of the person
     */
    private String pseudo;

    /**
     * Date of birth of the person
     */
    private String birthDate;

    /**
     * URL of the profile picture of the person
     */
    private String urlProfilePicture;


    /**
     * Constructor of the Person class
     * @param name
     * @param firstName
     * @param email
     * @param password
     * @param pseudo
     * @param birthDate
     * @param urlProfilePicture
     */
    public Person(String name, String firstName, String email, String password, String pseudo, String birthDate, String urlProfilePicture) {
        this.name = name;
        this.firstName = firstName;
        this.email = email;
        this.password = password;
        this.pseudo = pseudo;
        this.birthDate = birthDate;
        this.urlProfilePicture = urlProfilePicture;
    }


    /**
     * Abstract method to register a person
     * @param email the email address of the person
     * @param password the password of the person
     * @return true if the registration is successful, false otherwise
     */
    public abstract boolean seConnecter(String email, String password);

    /**
     * Method to register a person
     */
    public abstract void seDeconnecter();

    /**
     * Getter for the name of the person
     * @return the name of the person
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the name of the person
     * @param name the name of the person
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the first name of the person
     * @return the first name of the person
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Setter for the first name of the person
     * @param firstName the first name of the person
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Getter for the email address of the person
     * @return the email address of the person
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter for the email address of the person
     * @param email the email address of the person
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter for the password of the person
     * @return the password of the person
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for the password of the person
     * @param password the password of the person
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter for the pseudo of the person
     * @return the pseudo of the person
     */
    public String getPseudo() {
        return pseudo;
    }

    /**
     * Setter for the pseudo of the person
     * @param pseudo the pseudo of the person
     */
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    /**
     * Getter for the date of birth of the person
     * @return the date of birth of the person
     */
    public String getBirthDate() {
        return birthDate;
    }

    /**
     * Setter for the date of birth of the person
     * @param birthDate the date of birth of the person
     */
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * Getter for the URL of the profile picture of the person
     * @return the URL of the profile picture of the person
     */
    public String getUrlProfilePicture() {
        return urlProfilePicture;
    }

    /**
     * Setter for the URL of the profile picture of the person
     * @param urlProfilePicture the URL of the profile picture of the person
     */
    public void setUrlProfilePicture(String urlProfilePicture) {
        this.urlProfilePicture = urlProfilePicture;
    }
}
