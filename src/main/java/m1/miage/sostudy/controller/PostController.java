package m1.miage.sostudy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;

import m1.miage.sostudy.model.entity.Post;
import m1.miage.sostudy.model.entity.Community;
import m1.miage.sostudy.model.entity.User;
import m1.miage.sostudy.repository.PostRepository;
import m1.miage.sostudy.repository.CommunityRepository;
import m1.miage.sostudy.repository.UserRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/**
 * Controller for the post
 */
@Controller
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostRepository postRepository;
    
    @Autowired
    private CommunityRepository communityRepository;
    
    @Autowired
    private UserRepository userRepository;

    /**
     * Path for uploading files.
     */
    private final String UPLOAD_DIR = "./src/main/resources/static/images/posts_images/";

    /**
     * Get a post by id
     * @param id the id of the post
     * @param request the HttpServletRequest object
     * @param model the Model object
     * @param session the HttpSession object
     * @return the post details page
     */
    @GetMapping("/{id}")
    public String getPost(@PathVariable Integer id, HttpServletRequest request, Model model, HttpSession session) {
        //user not logged in
        if(session.getAttribute("user") == null) {return "redirect:/auth/login";}

        //post not found
        if(postRepository.findById(id).isEmpty()) {
            return "redirect:/";
        }
        return "post/details";
    }

    /**
     * Displays the form to publish a new post.
     *
     * @param request the HttpServletRequest object
     * @param model the Model object
     * @param session the HttpSession object
     * @return the name of the view to be rendered
     */
    @GetMapping("/publish")
    public String publishPost(HttpServletRequest request, Model model, HttpSession session) {
        //user not logged in
        if(session.getAttribute("user") == null) {return "redirect:/auth/login";}
        
        User user = (User) session.getAttribute("user");

        //get user communities
        List<Community> userCommunities = communityRepository.findByUsersMembers_IdUser(user.getIdUser());
        model.addAttribute("userCommunities", userCommunities);
        model.addAttribute("currentUri", request.getRequestURI());
        return "post/form_publish";
    }

    /**
     * Publish a new post.
     *
     * @param post the post object
     * @param request the HttpServletRequest object
     * @param session the HttpSession object
     * @return the redirect URL
     */
    @PostMapping("/publish")
    public String publishPost(@ModelAttribute Post post,
        @RequestParam(required = false) Integer communityId,
        @RequestParam("postMedia") MultipartFile image,
        HttpServletRequest request, HttpSession session) throws IOException {

        //user not logged in
        if(session.getAttribute("user") == null) {return "redirect:/auth/login";}
        
        User user = (User) session.getAttribute("user");

        //set user and publication date now
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        post.setUser(user);
        post.setPostPublicationDate(LocalDate.now().format(formatter));
        
        // If a community is selected, get it and set it in the post
        if (communityId != null) {
            Community community = communityRepository.findById(communityId).orElse(null);
            if (community != null) {
                post.setCommunity(community);
            }
        }
    
        // if we have attached an image to the post, we save it
        String fileName = null;
        if (!image.isEmpty()) {
            String rawFileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR, rawFileName);
            Files.createDirectories(filePath.getParent());
            Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            fileName = "/images/posts_images/" + rawFileName;
            post.setPostMediaPath(fileName);
        }

        postRepository.save(post);
        return "redirect:/";
    }

    /**
     * Edit a post
     * @param id the id of the post
     * @param model the model
     * @return the edit post page (form)
     */
    @GetMapping("/edit/{id}")
    public String editPost(@PathVariable Integer id, HttpServletRequest request, Model model, HttpSession session) {
        //user not logged in
        if(session.getAttribute("user") == null) {return "redirect:/auth/login";}
        return "edit_post";
    }

    /**
     * Update a post
     * @param id the id of the post
     * @param post the post to update
     * @return the post details page
     */
    @PostMapping("/edit/{id}")
    public String updatePost(@PathVariable Integer id, @ModelAttribute Post post, HttpServletRequest request, HttpSession session) {
        //user not logged in
        if(session.getAttribute("user") == null) {return "redirect:/auth/login";}
        return "redirect:/post/" + id;
    }

    /**
     * Delete a post
     * @param id the id of the post
     * @return the home page (feed)
     */
    @GetMapping("/delete/{id}")
    public String deletePost(@PathVariable Integer id, HttpServletRequest request, HttpSession session) {
        //user not logged in
        if(session.getAttribute("user") == null) {return "redirect:/auth/login";}
        return "redirect:/";
    }
    
}