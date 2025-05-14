package m1.miage.sostudy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * UserController handles user-related requests.
 * It provides endpoints for viewing, editing, deleting, and following/unfollowing users.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    /**
     * Displays the user profile page.
     * @return the name of the user profile view
     */
    @GetMapping("/user")
    public String user() {
        return "user";
    }

    /**
     * Displays the user profile page for a specific user identified by their pseudo.
     * @return the name of the user profile view
     */
    @GetMapping("/user/{pseudo}")
    public String userByPseudo() {
        return "user";
    }

    /**
     * Displays the edit user page.
     * @return the name of the edit user view
     */
    @GetMapping("/user/edit")
    public String editUser() {
        return "editUser";
    }

    /**
     * Displays the edit user page for a specific user identified by their pseudo.
     * @return the name of the edit user view
     */
    @PostMapping("/user/edit")
    public String editUserPost() {
        return "redirect:/user";
    }

    /**
     * Displays the delete user page.
     * @return the name of the delete user view
     */
    @PostMapping("/user/delete")
    public String deleteUser() {
        return "redirect:/";
    }

    /**
     * Displays the follow user page for a specific user identified by their pseudo.
     * @return the name of the follow user view
     */
    @PostMapping("/user/follow/{pseudo}")
    public String followUser() {
        return "redirect:/user";
    }

    /**
     * Displays the unfollow user page for a specific user identified by their pseudo.
     * @return the name of the unfollow user view
     */
    @PostMapping("/user/unfollow/{pseudo}")
    public String unfollowUser() {
        return "redirect:/user";
    }


}
