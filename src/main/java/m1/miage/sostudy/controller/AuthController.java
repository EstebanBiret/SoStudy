package m1.miage.sostudy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.mindrot.jbcrypt.BCrypt;


/**
 * Controller for managing authentication.
 */
@Controller
@RequestMapping("/auth")
public class AuthController {

    /**
     * Displays the login page.
     *
     * @return the name of the view to be rendered
     */
    @GetMapping("/login")
    public String login() {
        // Logic to display the login page
        return "login"; // Return the name of the view (e.g., Thymeleaf template)
    }

    /**
     * Authenticates the user.
     *
     * @return a redirect to the list of channels
     */
    @PostMapping("/login")
    public String authenticate() {
        // Logic to authenticate the user
        return "redirect:/channels/"; // Redirect to the list of channels after successful login
    }

    /**
     * Displays the registration page.
     *
     * @return the name of the view to be rendered
     */
    @GetMapping("/register")
    public String register() {
        // Logic to display the registration page
        return "register"; // Return the name of the view (e.g., Thymeleaf template)
    }

    /**
     * Registers a new user.
     *
     * @return a redirect to the login page
     */
    @PostMapping("/register")
    public String registerUser() {
        // Logic to register the user
        return "redirect:/auth/login"; // Redirect to the login page after successful registration
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
    public String hashPassword(String password) {
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
}
