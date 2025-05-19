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
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
     * Path for uploading files.
     */
    private final String UPLOAD_DIR = "./src/main/resources/static/images/community/";

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
     * Create a new community
     * @param communityName the name of the community
     * @param communityDescription the description of the community
     * @param communityImage the image of the community
     * @param session the session of the user
     * @return the name of the view to be rendered
     * @throws IOException if an I/O error occurs
     */
    @PostMapping("/new")
    public String createCommunity(@RequestParam String communityName, @RequestParam String communityDescription, @RequestParam MultipartFile communityImage, HttpSession session) throws IOException {

        //user not logged in
        if(session.getAttribute("user") == null) {return "redirect:/auth/login";}

        User sessionUser = (User) session.getAttribute("user");
        User user = userRepository.findById(sessionUser.getIdUser()).orElseThrow();

        Community community = new Community();
        community.setCommunityName(communityName);
        community.setCommunityDescription(communityDescription);

        //image
        String fileName = null;
        if (!communityImage.isEmpty()) {
            String rawFileName = UUID.randomUUID().toString() + "_" + communityImage.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR, rawFileName);
            Files.createDirectories(filePath.getParent());
            Files.copy(communityImage.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            fileName = "/images/community/" + rawFileName;
            community.setCommunityImagePath(fileName);
        }
        else {
            fileName = "/images/community/defaultCommunity.png";
            community.setCommunityImagePath(fileName);
        }

        //set creation date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        community.setCommunityCreationDate(LocalDate.now().format(formatter));

        community.setUserCreator(user);
        community.setNumberOfMembers(1);
        community.setNumberOfPosts(0);
        community.getUsers().add(user);
        communityRepository.save(community);

        user.getSubscribedCommunities().add(community);
        user.addCreatedCommunity(community);
        userRepository.save(user);

        // Add a small delay using a temporary redirect
        return "redirect:/community/temporary-redirect";
    }

    /**
     * Temporary redirect to ensure the image is properly saved
     * @return redirect to community list
     * @throws InterruptedException
     */
    @GetMapping("/temporary-redirect")
    public String temporaryRedirect() throws InterruptedException {
        // Wait for 1 second to ensure the image is properly saved
        Thread.sleep(1000);
        return "redirect:/community";
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
