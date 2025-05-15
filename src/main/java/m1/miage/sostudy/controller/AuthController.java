package m1.miage.sostudy.controller;


import jakarta.servlet.http.HttpSession;
import m1.miage.sostudy.model.entity.User;
import m1.miage.sostudy.repository.UserRepository;
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
     * Path for uploading files.
     */
    private final String UPLOAD_DIR = "./src/main/resources/static/images/profiles_pictures/";

    /**
     * Displays the login page.
     * @return the name of the view to be rendered
     */
    @GetMapping("/login")
    public String login(Model model, HttpSession session) {
        if (session.getAttribute("user") != null) {
            return"redirect:/";
        }
        return "login.html"; // Return the name of the view (e.g., Thymeleaf template)
    }

    /**
     * Authenticates the user.
     *
     * @return a redirect to the list of channels
     */
    @PostMapping("/login")
    public String authenticate(Model model, HttpSession session, @RequestParam("email") String email, @RequestParam("password") String password) {
        boolean loginError = false;
        if (userRepo.findByEmail(email) != null) {
            System.out.println("User found");
            User u = userRepo.findByEmail(email);
            if(checkPassword(password, u.getPassword())) {
                session.setAttribute("user", u);
                return "redirect:/";
            }else{
                loginError = true;
                model.addAttribute("loginError", loginError);
                return "login.html";
            }
        }else {
            loginError = true;
            model.addAttribute("loginError", loginError);
            return "login.html";
        }
    }

    /**
     * Displays the registration page.
     *
     * @return the name of the view to be rendered
     */
    @GetMapping("/register")
    public String register(Model model,HttpSession session) {
        if (session.getAttribute("user") != null) {
            return"redirect:/";
        }
        return "register.html";
    }

    /**
     * Registers a new user.
     *
     * @return a redirect to the login page
     */
    @PostMapping("/register")
    public String registerUser(Model model, @RequestParam("nom") String nom,
                               @RequestParam("prenom") String prenom,
                               @RequestParam("pseudo") String pseudo,
                               @RequestParam("email") String email,
                               @RequestParam("password") String password,
                               @RequestParam("birthdate") String birthdate,
                               @RequestParam("bioUser") String bio,
                               @RequestParam("image") MultipartFile image)throws IOException {

        String fileName = null;
        if (!image.isEmpty()) {
            fileName = "/images/progiles_pictures/" + UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR, fileName);
            Files.createDirectories(filePath.getParent());
            Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        }else{
            fileName = "/images/progiles_pictures/defaultProfilePic.jpg";
            Path filePath = Paths.get(UPLOAD_DIR, fileName);
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
            return "register.html";
        }

        User user = new User(nom, prenom,email,hashPassword(password), pseudo, birthdate,fileName, bio);
        userRepo.save(user);

        return "redirect:/";
    }

    /**
     * Logs out the user.
     *
     * @return a redirect to the login page
     */
    @PostMapping("/logout")
    public String logout() {
        // Logic to log out the user
        return "redirect:/auth/login"; // Redirect to the login page after logout
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
