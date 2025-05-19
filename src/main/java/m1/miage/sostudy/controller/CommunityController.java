package m1.miage.sostudy.controller;

import java.util.List;
import m1.miage.sostudy.model.entity.Community;
import m1.miage.sostudy.model.entity.User;
import m1.miage.sostudy.repository.CommunityRepository;
import m1.miage.sostudy.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 * Controller for the Community entity
 */
@Controller
@RequestMapping("/community")
public class CommunityController {

    @Autowired
    private CommunityRepository communityRepository;
    @Autowired
    private PostRepository postRepository;

    /**
     * Get all communities
     * @return the name of the view to be rendered
     */
    @GetMapping("")
    public String getAllCommunities(Model model, HttpServletRequest request, HttpSession session) {
        //user not logged in
        if(session.getAttribute("user") == null) {return "redirect:/auth/login";}

        User user = (User) session.getAttribute("user");
        List<Community> communities = communityRepository.findAll();
        
        // Pour chaque communauté, ajouter le nombre de membres et de posts
        for (Community community : communities) {
            community.setNumberOfMembers(communityRepository.countUsersInCommunity(community.getCommunityId()));
            community.setNumberOfPosts(communityRepository.countPostsInCommunity(community.getCommunityId()));
            
            // Formater la date de création
            String[] dateParts = community.getCommunityCreationDate().split("-");
            String formattedDate = Integer.parseInt(dateParts[2]) + " " +
                getMonthName(Integer.parseInt(dateParts[1])) + " " +
                dateParts[0];
            community.setCommunityCreationDate(formattedDate);
        }

        model.addAttribute("communities", communities);
        model.addAttribute("currentUri", request.getRequestURI());
        model.addAttribute("user", user);
        return "community/list";
    }

    private String getMonthName(int month) {
        String[] months = {
            "janvier", "février", "mars", "avril", "mai", "juin",
            "juillet", "août", "septembre", "octobre", "novembre", "décembre"
        };
        return months[month - 1];
    }

    /**
     * Formular to create a community
     * @return the name of the view to be rendered
     */
    @GetMapping("/new")
    public String newCommunity(Model model, HttpServletRequest request, HttpSession session) {

        //user not logged in
        if(session.getAttribute("user") == null) {return "redirect:/auth/login";}

        User user = (User) session.getAttribute("user");

        model.addAttribute("currentUri", request.getRequestURI());
        model.addAttribute("user", user);
        return "community/form_new";
    }

    /**
     * Create a new community
     * @return the name of the view to be rendered
     */
    @PostMapping("/new")
    public String createCommunity(@RequestParam String name, @RequestParam String description, Model model, HttpServletRequest request, HttpSession session) {

        //user not logged in
        if(session.getAttribute("user") == null) {return "redirect:/auth/login";}

        User user = (User) session.getAttribute("user");

        model.addAttribute("currentUri", request.getRequestURI());
        model.addAttribute("user", user);
        return "community/form_new";
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
