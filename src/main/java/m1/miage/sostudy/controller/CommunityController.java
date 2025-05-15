package m1.miage.sostudy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for the Community entity
 */
@Controller
@RequestMapping("/community")
public class CommunityController {

    /**
     * Get all communities
     * @return the name of the view to be rendered
     */
    @RequestMapping("")
    public String getAllCommunities() {
        return "community";
    }

    /**
     * Formular to create a community
     * @return the name of the view to be rendered
     */
    @GetMapping("/new")
    public String newCommunity() {
        return "community";
    }

    /**
     * Create a new community
     * @return the name of the view to be rendered
     */
    @PostMapping("/new")
    public String createCommunity() {
        return "redirect:/community";
    }

    /**
     * Formular to edit a community
     * @return the name of the view to be rendered
     */
    @GetMapping("/edit/{communityid}")
    public String editCommunity() {
        return "community";
    }

    /**
     * Edit a community
     * @return the name of the view to be rendered
     */
    @PostMapping("/edit/{communityid}")
    public String updateCommunity() {
        return "redirect:/community";
    }

    /**
     * Delete a community
     * @return the name of the view to be rendered
     */
    @PostMapping("/delete/{communityid}")
    public String deleteCommunity() {
        return "redirect:/community";
    }

    /**
     * Join a community
     * @return the name of the view to be rendered
     */
    @PostMapping("/join/{communityid}")
    public String joinCommunity() {
        return "redirect:/community";
    }

    /**
     * Leave a community
     * @return the name of the view to be rendered
     */
    @PostMapping("/leave/{communityid}")
    public String leaveCommunity() {
        return "redirect:/community";
    }

    /**
     * Get a community
     * @return the name of the view to be rendered
     */
    @GetMapping("/{communityid}")
    public String getCommunity() {
        return "community";
    }
}
