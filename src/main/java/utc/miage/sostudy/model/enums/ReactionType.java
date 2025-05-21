package utc.miage.sostudy.model.enums;

/**
 * Enum representing the type of reaction
 */
public enum ReactionType {

    /**
     * Like reaction
     */
    LIKE("Like", "/images/reactions/like.png"),
    
    /**
     * Love reaction
     */
    LOVE("Love", "/images/reactions/love.png"),
    
    /**
     * Laugh reaction
     */
    LAUGH("Laugh", "/images/reactions/laugh.png"),
    
    /**
     * Cry reaction
     */
    CRY(" Cry", "/images/reactions/cry.png"),
    
    /**
     * Angry reaction
     */
    ANGRY("Angry", "/images/reactions/angry.png");

    /**
     * The name of the reaction
     */
    private String name;

    /**
     * The image path of the reaction
     */
    private String imagePath;

    /**
     * Constructor with parameters
     * @param name The name of the reaction
     * @param imagePath The image path of the reaction
     */
    ReactionType(String name, String imagePath) {
        this.name = name;
        this.imagePath = imagePath;
    }

    /**
     * Getter for the name of the reaction
     * @return the name of the reaction
     */
    public String getName() {return name;}

    /**
     * Getter for the image path of the reaction
     * @return the image path of the reaction
     */
    public String getImagePath() {return imagePath;}
}