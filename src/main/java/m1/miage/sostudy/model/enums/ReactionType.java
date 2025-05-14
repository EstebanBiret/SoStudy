package m1.miage.sostudy.model.enums;

/**
 * Enum representing the type of reaction
 */
public enum ReactionType {
    LIKE("Like", "src/main/resources/images/reactions/like.png"),
    LOVE("Love", "src/main/resources/images/reactions/love.png"),
    LAUGH("Laugh", "src/main/resources/images/reactions/laugh.png"),
    CRY(" Cry", "src/main/resources/images/reactions/cry.png"),
    ANGRY("Angry", "src/main/resources/images/reactions/angry.png");

    /**
     * The name of the reaction
     */
    private final String name;

    /**
     * The image path of the reaction
     */
    private final String imagePath;

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