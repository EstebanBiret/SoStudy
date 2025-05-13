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
     * get all communities
     */
    @RequestMapping("")
    public String getAllCommunities() {
        return "community";
    }

    /**
     * formular to create a community
     */
    @GetMapping("/new")
    public String newCommunity() {
        return "community";
    }

    /**
     * create a new community
     */
    @PostMapping("/new")
    public String createCommunity() {
        return "redirect:/community";
    }

    /**
     * formular to edit a community
     */
    @GetMapping("/edit/{communityid}")
    public String editCommunity() {
        return "community";
    }

    /**
     * edit a community
     */
    @PostMapping("/edit/{communityid}")
    public String updateCommunity() {
        return "redirect:/community";
    }

    /**
     * delete a community
     */
    @PostMapping("/delete/{communityid}")
    public String deleteCommunity() {
        return "redirect:/community";
    }

    /**
     * join a community
     */
    @PostMapping("/join/{communityid}")
    public String joinCommunity() {
        return "redirect:/community";
    }

    /**
     * leave a community
     */
    @PostMapping("/leave/{communityid}")
    public String leaveCommunity() {
        return "redirect:/community";
    }

    /**
     * get a community
     */
    @GetMapping("/{communityid}")
    public String getCommunity() {
        return "community";
    }
}
