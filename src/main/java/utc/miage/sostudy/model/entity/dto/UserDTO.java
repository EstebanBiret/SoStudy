package utc.miage.sostudy.model.entity.dto;

import utc.miage.sostudy.model.entity.User;

/**
 * Data Transfer Object for User.
 * This class is used to transfer user data between different layers of the application.
 * JSON serialization/deserialization is handled by the framework.
 */
public class UserDTO {

    /**
     * The id of the user.
     */
    private int idUser;

    /**
     * The pseudo of the user.
     */
    private String pseudo;

    /**
     * The path of the profile picture of the user.
     */
    private String pathProfilePicture;

    /**
     * Default constructor.
     */
    public UserDTO() {

    }

    /**
     * Constructor with parameters.
     * @param user the user object
     */
    public UserDTO(User user){
        this.idUser = user.getIdUser();
        this.pseudo = user.getPseudo();
        this.pathProfilePicture = user.getPersonImagePath();
    }

    /**
     * Constructor with parameters.
     * @param idUser the id of the user
     * @param pseudo the pseudo of the user
     * @param pathProfilePicture the path of the profile picture of the user
     */
    public UserDTO(int idUser, String pseudo, String pathProfilePicture) {
        this.idUser = idUser;
        this.pseudo = pseudo;
        this.pathProfilePicture = pathProfilePicture;
    }


    /**
     * Getter for the id of the user.
     * @return the id of the user
     */
    public int getIdUser() {
        return idUser;
    }

    /**
     * Setter for the id of the user.
     * @param idUser the id of the user
     */
    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    /**
     * Getter for the pseudo of the user.
     * @return the pseudo of the user
     */
    public String getPseudo() {
        return pseudo;
    }

    /**
     * Setter for the pseudo of the user.
     * @param pseudo the pseudo of the user
     */
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    /**
     * Getter for the path of the profile picture of the user.
     * @return the path of the profile picture of the user
     */
    public String getPathProfilePicture() {
        return pathProfilePicture;
    }

    /**
     * Setter for the path of the profile picture of the user.
     * @param pathProfilePicture the path of the profile picture of the user
     */
    public void setPathProfilePicture(String pathProfilePicture) {
        this.pathProfilePicture = pathProfilePicture;
    }
}
