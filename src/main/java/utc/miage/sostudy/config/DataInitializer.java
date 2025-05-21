package utc.miage.sostudy.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import utc.miage.sostudy.model.entity.*;
import utc.miage.sostudy.repository.*;

/**
 * DataInitializer class
 */
@Component
public class DataInitializer implements CommandLineRunner {
    
    /**
     * User repository for database operations
     */
    @Autowired
    private UserRepository userRepository;
    
    /**
     * Run the data initializer
     * @param args the arguments
     * @throws Exception if an error occurs
     */
    @Override
    public void run(String... args) throws Exception {

        //check if deleted user already exists
        if (userRepository.count() > 0) {return;}

        //Create deleted user
        User deletedUser = new User();
        deletedUser.setName("Supprime");
        deletedUser.setFirstName("Utilisateur");
        deletedUser.setPseudo("utilisateurSupprime");
        deletedUser.setBioUser("Ce compte a été supprimé");
        deletedUser.setPersonImagePath("/images/profiles_pictures/defaultProfilePic.jpg");
        userRepository.save(deletedUser);
    }
}