package m1.miage.sostudy.model.entity;

import jakarta.persistence.*;
import m1.miage.sostudy.model.enums.ReactionType;

/**
 * Class representing a reaction
 */
@Entity
@Table(name = "reaction")
public class Reaction {

    /**
     * The id of the reaction
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer reactionId;

    /**
     * The type of the reaction
     */
    @Enumerated(EnumType.STRING)
    private ReactionType reactionType;

    /**
     * Default constructor
     */
    public Reaction() {}

    /**
     * Constructor with parameters
     * @param reactionType the type of the reaction
     */
    public Reaction(ReactionType reactionType) {this.reactionType = reactionType;}

    /**
     * Getter for the id of the reaction
     * @return the id of the reaction
     */
    public Integer getReactionId() {return reactionId;}

    /**
     * Setter for the id of the reaction
     * @param reactionId the id of the reaction
     */
    public void setReactionId(Integer reactionId) {this.reactionId = reactionId;}

    /**
     * Getter for the type of the reaction
     * @return the type of the reaction
     */
    public ReactionType getReactionType() {return reactionType;}

    /**
     * Setter for the type of the reaction
     * @param reactionType the type of the reaction
     */
    public void setReactionType(ReactionType reactionType) {this.reactionType = reactionType;}

    /**
     * Redefinition of the equals method
     * @param obj the object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Reaction other = (Reaction) obj;
        if (reactionId == null || other.reactionId == null)
            return false;
        return reactionId.equals(other.reactionId);
    }

    /**
     * Redefinition of the hashCode method
     * @return the hash code of the object
     */
    @Override
    public int hashCode() {return reactionId == null ? 0 : reactionId.hashCode();} 
}