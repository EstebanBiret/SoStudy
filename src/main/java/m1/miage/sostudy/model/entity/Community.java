package m1.miage.sostudy.model.entity;

import jakarta.persistence.*;

/**
 * Class representing a community
 */
@Entity
@Table(name = "community")
public class Community {

    /**
     * ID of the community
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idCommunity;

    /**
     * Name of the community
     */
    private String nameCommunity;

    /**
     * Date of creation of the community
     */
    private String dateCreationCommunity;

    /**
     * URL of the community picture
     */
    private String urlCommunityPicture;

    /**
     * Description of the community
     */
    private String descriptionCommunity;

    //ToDo : add the user who created the community

    //ToDo : add the list of users who are members of the community

    //ToDo : add the list of post of this community

    /**
     * Constructor of the Community class
     */
    public Community() {

    }
    /**
     * Constructor of the Community class
     * @param nameCommunity
     * @param dateCreationCommunity
     * @param urlCommunityPicture
     * @param descriptionCommunity
     */
    public Community(String nameCommunity, String dateCreationCommunity, String urlCommunityPicture, String descriptionCommunity) {
        this.nameCommunity = nameCommunity;
        this.dateCreationCommunity = dateCreationCommunity;
        this.urlCommunityPicture = urlCommunityPicture;
        this.descriptionCommunity = descriptionCommunity;
    }

    /**
     * Constructor of the Community class
     * @param idCommunity
     * @param nameCommunity
     * @param dateCreationCommunity
     * @param urlCommunityPicture
     * @param descriptionCommunity
     */
    public Community(int idCommunity, String nameCommunity, String dateCreationCommunity, String urlCommunityPicture, String descriptionCommunity) {
        this.idCommunity = idCommunity;
        this.nameCommunity = nameCommunity;
        this.dateCreationCommunity = dateCreationCommunity;
        this.urlCommunityPicture = urlCommunityPicture;
        this.descriptionCommunity = descriptionCommunity;
        
    }

    /**
     * Getters and Setters
     */
    public int getIdCommunity() {
        return idCommunity;
    }

    /**
     * Getters and Setters
     */
    public String getNameCommunity() {
        return nameCommunity;
    }

    /**
     * Getters and Setters
     */
    public void setNameCommunity(String nameCommunity) {
        this.nameCommunity = nameCommunity;
    }

    /**
     * Getters and Setters
     */
    public String getDateCreationCommunity() {
        return this.dateCreationCommunity;
    }

    /**
     * Getters and Setters
     */
    public void setDateCreationCommunity(String dateCreationCommunity) {
        this.dateCreationCommunity = dateCreationCommunity;
    }

    /**
     * Getters and Setters
     */
    public String getUrlCommunityPicture() {
        return urlCommunityPicture;
    }

    /**
     * Getters and Setters
     */
    public void setUrlCommunityPicture(String urlCommunityPicture) {
        this.urlCommunityPicture = urlCommunityPicture;
    }

    /**
     * Getters and Setters
     */
    public String getDescriptionCommunity() {
        return descriptionCommunity;
    }

    /**
     * Getters and Setters
     */
    public void setDescriptionCommunity(String descriptionCommunity) {
        this.descriptionCommunity = descriptionCommunity;
    }


    /**
     * Override the hashCode method
     * @return the hash code of the community
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + idCommunity;
        result = prime * result + ((nameCommunity == null) ? 0 : nameCommunity.hashCode());
        result = prime * result + ((dateCreationCommunity == null) ? 0 : dateCreationCommunity.hashCode());
        result = prime * result + ((urlCommunityPicture == null) ? 0 : urlCommunityPicture.hashCode());
        result = prime * result + ((descriptionCommunity == null) ? 0 : descriptionCommunity.hashCode());
        return result;
    }

    /**
     * Override the equals method
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
        Community other = (Community) obj;
        if (idCommunity != other.idCommunity)
            return false;
        if (nameCommunity == null) {
            if (other.nameCommunity != null)
                return false;
        } else if (!nameCommunity.equals(other.nameCommunity))
            return false;
        if (dateCreationCommunity == null) {
            if (other.dateCreationCommunity != null)
                return false;
        } else if (!dateCreationCommunity.equals(other.dateCreationCommunity))
            return false;
        if (urlCommunityPicture == null) {
            if (other.urlCommunityPicture != null)
                return false;
        } else if (!urlCommunityPicture.equals(other.urlCommunityPicture))
            return false;
        if (descriptionCommunity == null) {
            if (other.descriptionCommunity != null)
                return false;
        } else if (!descriptionCommunity.equals(other.descriptionCommunity))
            return false;
        return true;
    }
}
