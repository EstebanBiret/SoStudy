package m1.miage.sostudy.model.entity;

import jakarta.persistence.MappedSuperclass;

import java.util.Objects;

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
     * Path of the image of the person
     */
    private String personImagePath;


    /**
     * Constructor of the Person class
     * @param name
     * @param firstName
     * @param email
     * @param password
     * @param pseudo
     * @param birthDate
     * @param personImagePath
     */
    public Person(String name, String firstName, String email, String password, String pseudo, String birthDate, String personImagePath) {
        this.name = name;
        this.firstName = firstName;
        this.email = email;
        this.password = password;
        this.pseudo = pseudo;
        this.birthDate = birthDate;
        this.personImagePath = personImagePath;
    }

    /**
     * Default constructor of the Person class
     */

    public Person() {}

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
     * Getter for the path of the image of the person
     * @return the path of the image of the person
     */
    public String getPersonImagePath() {
        return personImagePath;
    }

    /**
     * Setter for the path of the image of the person
     * @param personImagePath the path of the image of the person
     */
    public void setPersonImagePath(String personImagePath) {
        this.personImagePath = personImagePath;
    }

    /**
     * Method to compare two Person objects
     * @param o the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(name, person.name) && Objects.equals(firstName, person.firstName) && Objects.equals(email, person.email) && Objects.equals(password, person.password) && Objects.equals(pseudo, person.pseudo) && Objects.equals(birthDate, person.birthDate) && Objects.equals(personImagePath, person.personImagePath);
    }

    /**
     * Method to generate a hash code for the Person object
     * @return the hash code of the Person object
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, firstName, email, password, pseudo, birthDate, personImagePath);
    }
}
