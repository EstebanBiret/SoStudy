package m1.miage.sostudy.controller;

import m1.miage.sostudy.model.entity.Community;
import m1.miage.sostudy.model.entity.Post;
import m1.miage.sostudy.model.entity.User;
import m1.miage.sostudy.repository.CommunityRepository;
import m1.miage.sostudy.repository.PostRepository;
import m1.miage.sostudy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

/**
 * Controller for the Community entity
 */
@Controller
@RequestMapping("/community")
public class CommunityController {

    /**
     * Community repository
     */
    @Autowired
    private CommunityRepository communityRepository;

    /**
     * User repository
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Post repository
     */
    @Autowired
    private PostRepository postRepository;

    /**
     * Get the name of the month in French
     * @param month the month
     * @return the name of the month in French
     */
    private String getMonthName(int month) {
        String[] months = {
            "janvier", "février", "mars", "avril", "mai", "juin",
            "juillet", "août", "septembre", "octobre", "novembre", "décembre"
        };
        return months[month - 1];
    }

    /**
     * Get all communities
     * @param model the model of the view
     * @param session the session of the user
     * @param request the request of the user
     * @return the name of the view to be rendered
     */
    @GetMapping("")
    public String getAllCommunities(Model model, HttpSession session, HttpServletRequest request) {
        //user not logged in
        if(session.getAttribute("user") == null) {return "redirect:/auth/login";}
        
        User user = (User) session.getAttribute("user");
        List<Community> communities = communityRepository.findAll();
        
        //update number of members and posts
        for (Community community : communities) {
            community.setNumberOfMembers(communityRepository.countUsersInCommunity(community.getCommunityId()));
            community.setNumberOfPosts(communityRepository.countPostsInCommunity(community.getCommunityId()));
            
            //update creation date
            String[] dateParts = community.getCommunityCreationDate().split("-");
            String formattedDate = Integer.parseInt(dateParts[2]) + " " +
                getMonthName(Integer.parseInt(dateParts[1])) + " " +
                dateParts[0];
            community.setCommunityCreationDate(formattedDate);
        }

        model.addAttribute("communities", communities);
        model.addAttribute("user", user);
        model.addAttribute("currentUri", request.getRequestURI());

        return "community/list";
    }

    /**
     * Join a community
     * @param communityId the ID of the community
     * @param session the session of the user
     * @param request the request of the user
     * @return the name of the view to be rendered
     */
    @PostMapping("/join/{communityId}")
    public String joinCommunity(@PathVariable Integer communityId, HttpSession session, HttpServletRequest request) {

        // Check if user is logged in
        if (session.getAttribute("user") == null) {return "redirect:/auth/login";}

        User user = (User) session.getAttribute("user");
        Optional<Community> optionalCommunity = communityRepository.findById(communityId);
        
        if (optionalCommunity.isPresent()) {
            Community community = optionalCommunity.get();
            if (!user.getSubscribedCommunities().contains(community)) {
                user.getSubscribedCommunities().add(community);
                community.getUsers().add(user);
                userRepository.save(user);
                communityRepository.save(community);
            }
        }

        return "redirect:/community";
    }

    /**
     * Leave a community
     * @param communityId the ID of the community
     * @param session the session of the user
     * @param request the request of the user
     * @return the name of the view to be rendered
     */
    @PostMapping("/leave/{communityId}")
    public String leaveCommunity(@PathVariable Integer communityId, HttpSession session, HttpServletRequest request) {
        
        // Check if user is logged in
        if (session.getAttribute("user") == null) {return "redirect:/auth/login";}

        User user = (User) session.getAttribute("user");
        Optional<Community> optionalCommunity = communityRepository.findById(communityId);
        
        if (optionalCommunity.isPresent()) {
            Community community = optionalCommunity.get();
            if (user.getSubscribedCommunities().contains(community)) {
                user.getSubscribedCommunities().remove(community);
                community.getUsers().remove(user);
                userRepository.save(user);
                communityRepository.save(community);
            }
        }

        return "redirect:/community";
    }

    /**
     * Formular to create a community
     * @param model the model of the view
     * @param session the session of the user
     * @return the name of the view to be rendered
     */
    @GetMapping("/new")
    public String newCommunity(Model model, HttpSession session) {

        //user not logged in
        if(session.getAttribute("user") == null) {return "redirect:/auth/login";}

        User user = (User) session.getAttribute("user");

        model.addAttribute("user", user);
        return "community/form_new";
    }

    /**
     * Create a new community
     * @param name the name of the community
     * @param description the description of the community
     * @param model the model of the view
     * @param session the session of the user
     * @return the name of the view to be rendered
     */
    @PostMapping("/new")
    public String createCommunity(@RequestParam String name, @RequestParam String description, Model model, HttpSession session) {

        //user not logged in
        if(session.getAttribute("user") == null) {return "redirect:/auth/login";}

        User user = (User) session.getAttribute("user");

        model.addAttribute("user", user);
        return "community/form_new";
    }

    /**
     * Formular to edit a community
     * @param communityId the ID of the community
     * @param model the model of the view
     * @param session the session of the user
     * @param request the request of the user
     * @return the name of the view to be rendered
     */
    @GetMapping("/edit/{communityId}")
    public String editCommunity(@PathVariable Integer communityId, Model model, HttpSession session, HttpServletRequest request) {
        
        //user not logged in
        if(session.getAttribute("user") == null) {return "redirect:/auth/login";}

        User user = (User) session.getAttribute("user");
        
        model.addAttribute("user", user);
        model.addAttribute("currentUri", request.getRequestURI());
        return "community/form_edit";
    }

    /**
     * Edit a community
     * @param communityId the ID of the community
     * @param model the model of the view
     * @param session the session of the user
     * @param request the request of the user
     * @return the name of the view to be rendered
     */
    @PostMapping("/edit/{communityId}")
    public String updateCommunity(@PathVariable Integer communityId, Model model, HttpSession session, HttpServletRequest request) {
        
        //user not logged in
        if(session.getAttribute("user") == null) {return "redirect:/auth/login";}

        User user = (User) session.getAttribute("user");
        
        model.addAttribute("user", user);
        model.addAttribute("currentUri", request.getRequestURI());
        return "community/form_edit";
    }

    /**
     * Delete a community
     * @param communityId the ID of the community
     * @param model the model of the view
     * @param session the session of the user
     * @param request the request of the user
     * @return the name of the view to be rendered
     */
    @PostMapping("/delete/{communityId}")
    public String deleteCommunity(@PathVariable Integer communityId, Model model, HttpSession session, HttpServletRequest request) {
        
        //user not logged in
        if(session.getAttribute("user") == null) {return "redirect:/auth/login";}

        User user = (User) session.getAttribute("user");
        Optional<Community> optionalCommunity = communityRepository.findById(communityId);

        // check if community exists
        if (optionalCommunity.isPresent()) {
            Community community = optionalCommunity.get();
            
            // check if user is the creator
            if (user.getIdUser() == community.getUserCreator().getIdUser()) {

                // remove column community_id from post
                List<Post> posts = community.getPosts();
                for (Post post : posts) {
                    post.setCommunity(null);
                    postRepository.save(post);
                }

                // remove user from community
                List<User> users = community.getUsers();
                for (User u : users) {
                    u.getSubscribedCommunities().remove(community);
                    userRepository.save(u);
                }
                
                // delete community
                communityRepository.deleteById(communityId);
                communityRepository.flush();

                // Recharger l'utilisateur pour éviter les références obsolètes
                user = userRepository.findById(user.getIdUser()).orElse(null);
                session.setAttribute("user", user);
            }
        }

        return "redirect:/community";
    }

    /**
     * Get a community
     * @param communityId the ID of the community
     * @param model the model of the view
     * @param session the session of the user
     * @param request the request of the user
     * @return the name of the view to be rendered
     */
    @GetMapping("/{communityId}")
    public String getCommunity(@PathVariable Integer communityId, Model model, HttpSession session, HttpServletRequest request) {
        
        //user not logged in
        if(session.getAttribute("user") == null) {return "redirect:/auth/login";}

        User user = (User) session.getAttribute("user");
        
        model.addAttribute("user", user);
        model.addAttribute("currentUri", request.getRequestURI());
        
        Optional<Community> optionalCommunity = communityRepository.findById(communityId);
        if (optionalCommunity.isPresent()) {
            model.addAttribute("community", optionalCommunity.get());
        }
        return "community";
    }
}
