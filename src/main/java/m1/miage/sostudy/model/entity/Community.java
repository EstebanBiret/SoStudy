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
    private int communityId;

    /**
     * Name of the community
     */
    private String communityName;

    /**
     * Date of creation of the community
     */
    private String communityCreationDate;

    /**
     * URL of the community picture
     */
    private String cmmunityImagePath;

    /**
     * Description of the community
     */
    private String communityDescription;

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
     * @param communityName
     * @param communityCreationDate
     * @param cmmunityImagePath
     * @param communityDescription
     */
    public Community(String communityName, String communityCreationDate, String cmmunityImagePath, String communityDescription) {
        this.communityName = communityName;
        this.communityCreationDate = communityCreationDate;
        this.cmmunityImagePath = cmmunityImagePath;
        this.communityDescription = communityDescription;
    }

    /**
     * Constructor of the Community class
     * @param communityId
     * @param communityName
     * @param communityCreationDate
     * @param cmmunityImagePath
     * @param communityDescription
     */
    public Community(int communityId, String communityName, String communityCreationDate, String cmmunityImagePath, String communityDescription) {
        this.communityId = communityId;
        this.communityName = communityName;
        this.communityCreationDate = communityCreationDate;
        this.cmmunityImagePath = cmmunityImagePath;
        this.communityDescription = communityDescription;
        
    }

    /**
     * Getters and Setters
     */
    public int getCommunityId() {
        return communityId;
    }

    /**
     * Getters and Setters
     */
    public String getCommunityName() {
        return communityName;
    }

    /**
     * Getters and Setters
     */
    public void setCommunityName(String nameCommunity) {
        this.communityName = nameCommunity;
    }

    /**
     * Getters and Setters
     */
    public String getCommunityCreationDate() {
        return this.communityCreationDate;
    }

    /**
     * Getters and Setters
     */
    public void setCommunityCreationDate(String dateCreationCommunity) {
        this.communityCreationDate = dateCreationCommunity;
    }

    /**
     * Getters and Setters
     */
    public String getCmmunityImagePath() {
        return cmmunityImagePath;
    }

    /**
     * Getters and Setters
     */
    public void setCmmunityImagePath(String urlCommunityPicture) {
        this.cmmunityImagePath = urlCommunityPicture;
    }

    /**
     * Getters and Setters
     */
    public String getCommunityDescription() {
        return communityDescription;
    }

    /**
     * Getters and Setters
     */
    public void setCommunityDescription(String descriptionCommunity) {
        this.communityDescription = descriptionCommunity;
    }


    /**
     * Override the hashCode method
     * @return the hash code of the community
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + communityId;
        result = prime * result + ((communityName == null) ? 0 : communityName.hashCode());
        result = prime * result + ((communityCreationDate == null) ? 0 : communityCreationDate.hashCode());
        result = prime * result + ((cmmunityImagePath == null) ? 0 : cmmunityImagePath.hashCode());
        result = prime * result + ((communityDescription == null) ? 0 : communityDescription.hashCode());
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
        if (communityId != other.communityId)
            return false;
        if (communityName == null) {
            if (other.communityName != null)
                return false;
        } else if (!communityName.equals(other.communityName))
            return false;
        if (communityCreationDate == null) {
            if (other.communityCreationDate != null)
                return false;
        } else if (!communityCreationDate.equals(other.communityCreationDate))
            return false;
        if (cmmunityImagePath == null) {
            if (other.cmmunityImagePath != null)
                return false;
        } else if (!cmmunityImagePath.equals(other.cmmunityImagePath))
            return false;
        if (communityDescription == null) {
            if (other.communityDescription != null)
                return false;
        } else if (!communityDescription.equals(other.communityDescription))
            return false;
        return true;
    }
}
