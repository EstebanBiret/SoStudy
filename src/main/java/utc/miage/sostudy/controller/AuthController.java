package utc.miage.sostudy.controller;


import jakarta.servlet.http.HttpSession;
import utc.miage.sostudy.model.entity.User;
import utc.miage.sostudy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import static utc.miage.sostudy.model.entity.User.STUDY_LEVELS;


/**
 * Controller for managing authentication.
 */
@Controller
@RequestMapping("/auth")
public class AuthController {

    /**
     * User repository for database operations.
     */
    @Autowired
    private UserRepository userRepo;

    /**
     * Redirect to home page
     */
    private static final String HOME = "redirect:/";

    /**
     * Redirect to login page
     */
    private static final String LOGIN = "auth/login";

    /**
     * Path for uploading files.
     */
    private String uploadDir = "./src/main/resources/static/images/profiles_pictures/";

    /**
     * Default profile picture path.
     */
    private String defaultProfilePic = "/images/profiles_pictures/defaultProfilePic.jpg";

    /**
     * Default path.
     * @param model the model to be used in the view
     * @param session the HTTP session
     * @return a redirect to the login page
     * */
    @GetMapping("")
    public String index(Model model, HttpSession session) {
        return HOME + LOGIN;
    }

    /**
     * Default path.
     * @param model the model to be used in the view
     * @param session the HTTP session
     * @return a redirect to the login page
     * */
    @GetMapping("/")
    public String indexSecond(Model model, HttpSession session) {
        return HOME + LOGIN;
    }

    /**
     * Displays the login page.
     * @param model the model to be used in the view
     * @param session the HTTP session
     * @return the name of the view to be rendered
     */
    @GetMapping("/login")
    public String login(Model model, HttpSession session) {
        if (session.getAttribute("user") != null) {
            return HOME;
        }
        return LOGIN;
    }

    /**
     * Authenticates the user.
     * @param model the model to be used in the view
     * @param session the HTTP session
     * @param email the email of the user
     * @param password the password of the user
     * @return a redirect to the list of channels
     */
    @PostMapping("/login")
    public String authenticate(Model model, HttpSession session, @RequestParam("email") String email, @RequestParam("password") String password) {
        boolean loginError = false;
        if (userRepo.findByEmail(email) != null) {
            User u = userRepo.findByEmail(email);
            if(checkPassword(password, u.getPassword())) {
                session.setAttribute("user", u);
                return HOME;
            }else{
                loginError = true;
                model.addAttribute("loginError", loginError);
                return LOGIN;
            }
        }else {
            loginError = true;
            model.addAttribute("loginError", loginError);
            return LOGIN;
        }
    }

    /**
     * Displays the registration page.
     * @param model the model to be used in the view
     * @param session the HTTP session
     * @return the name of the view to be rendered
     */
    @GetMapping("/register")
    public String register(Model model,HttpSession session) {
        if (session.getAttribute("user") != null) {
            return HOME;
        }
        model.addAttribute("niveauxEtude", STUDY_LEVELS);
        return "auth/register";
    }

    /**
     * Registers a new user.
     * @param model the model to be used in the view
     * @param session the HTTP session
     * @param nom the name of the user
     * @param prenom the first name of the user
     * @param pseudo the pseudo of the user
     * @param email the email address of the user
     * @param password the password of the user
     * @param birthdate the date of birth of the user
     * @param bioUser the bio of the user
     * @param image the image of the user
     * @param niveauEtude the study level of the user
     * @param studyDomain the study domain of the user
     * @param university the university of the user
     * @return a redirect to the login page
     * @throws IOException if an I/O error occurs
     */
    @PostMapping("/register")
    public String registerUser(Model model, HttpSession session, @RequestParam String nom, @RequestParam String prenom,
@RequestParam String pseudo,
@RequestParam String email,
@RequestParam String password,
@RequestParam String birthdate,
@RequestParam String bioUser,
@RequestParam MultipartFile image, @RequestParam String niveauEtude, @RequestParam String studyDomain, @RequestParam String university) throws IOException {

        String fileName = null;
        if (!image.isEmpty()) {
            String rawFileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, rawFileName);
            Files.createDirectories(filePath.getParent());
            Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            fileName = "/images/profiles_pictures/" + rawFileName;
        } else {
            fileName = defaultProfilePic;
        }

        boolean error = false;
        // Check if the email and pseudo already exist
        if (checkEmailExists(email)) {
            model.addAttribute("emailError", true);
            error = true;
        }
        if (checkPseudoExists(pseudo)) {
            model.addAttribute("pseudoError", true);
            error = true;
        }
        if(error){
            return "auth/register";
        }

        User user = new User(nom, prenom,email,hashPassword(password), pseudo, birthdate,fileName, bioUser, niveauEtude, studyDomain, university);
        userRepo.save(user);

        session.setAttribute("user", user);
        
        // Add a small delay using a temporary redirect
        return "redirect:/auth/temporary-redirect/" + user.getPseudo();
    }

    /**
     * Temporary redirect to ensure the image is properly saved
     * @param pseudo the pseudo of the user
     * @return redirect to user profile
     * @throws InterruptedException if the thread is interrupted
     */
    @GetMapping("/temporary-redirect/{pseudo}")
    public String temporaryRedirect(@PathVariable String pseudo) throws InterruptedException {
        // Wait for 1 second to ensure the image is properly saved
        Thread.sleep(1000);
        return "redirect:/user/" + pseudo;
    }

    /**
     * Logs out the user.
     * @param model the model to be used in the view
     * @param session the HTTP session
     * @return a redirect to the login page
     */
    @PostMapping("/logout")
    public String logout(Model model, HttpSession session) {
        session.removeAttribute("user");
        return HOME + LOGIN;
    }

    /**
     * Hashes the password using BCrypt.
     *
     * @param password the password to hash
     * @return the hashed password
     */
    public static String hashPassword(String password) {
        // Hash the password using BCrypt
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    /**
     * Checks if the provided password matches the hashed password.
     *
     * @param password the password to check
     * @param hashed   the hashed password
     * @return true if the password matches, false otherwise
     */
    public boolean checkPassword(String password, String hashed) {
        // Check if the password matches the hashed password
        return BCrypt.checkpw(password, hashed);
    }

    /**
     * Check if the email already exists in the database.
     * @param email the email to check
     * @return true if the email exists, false otherwise
     */
    public boolean checkEmailExists(String email) {
        // Check if the email already exists in the database
        return userRepo.findByEmail(email) != null;
    }

    /**
     * Check if the pseudo already exists in the database.
     * @param pseudo the pseudo to check
     * @return true if the pseudo exists, false otherwise
     */
    public boolean checkPseudoExists(String pseudo) {
        // Check if the pseudo already exists in the database
        return userRepo.findByPseudo(pseudo) != null;
    }

    /**
     * Check if the email is already taken.
     * @param email the email to check
     * @return true if the email is taken, false otherwise
     */
    @GetMapping("/check-email")
    @ResponseBody
    public boolean checkEmail(@RequestParam("email") String email) {
        return !checkEmailExists(email); // true si email est libre
    }


    /**
     * Check if the pseudo is already taken.
     * @param pseudo the pseudo to check
     * @return true if the pseudo is taken, false otherwise
     */
    @GetMapping("/check-pseudo")
    @ResponseBody
    public boolean checkPseudo(@RequestParam("pseudo") String pseudo) {
        return !checkPseudoExists(pseudo); // true si pseudo est libre
    }


}
