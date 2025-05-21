package utc.miage.sostudy.controller;

import utc.miage.sostudy.model.entity.Community;
import utc.miage.sostudy.model.entity.Post;
import utc.miage.sostudy.model.entity.User;
import utc.miage.sostudy.model.entity.UserPostReaction;
import utc.miage.sostudy.model.entity.id.UserPostReactionID;
import utc.miage.sostudy.model.enums.ReactionType;
import utc.miage.sostudy.repository.CommunityRepository;
import utc.miage.sostudy.repository.PostRepository;
import utc.miage.sostudy.repository.RepostRepository;
import utc.miage.sostudy.repository.UserPostReactionRepository;
import utc.miage.sostudy.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static utc.miage.sostudy.controller.IndexController.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
     * Autowired RepostRepository
     */
    @Autowired
    private RepostRepository repostRepository;

    /**
     * Autowired UserPostReactionRepository
     */
    @Autowired
    private UserPostReactionRepository userPostReactionRepository;

    /**
     * Path for uploading files.
     */
    private String uploadDir = "./src/main/resources/static/images/community/";

    /**
     * Get the name of the month in French
     * @param month the month
     * @return the name of the month in French
     */
    public static String getMonthName(int month) {
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
        List<Community> communities = communityRepository.findAllOrderByCreationDate();
        
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
            
            // Check if user is member of this community
            if(communityRepository.existsByUsers_IdUserAndCommunityId(user.getIdUser(), community.getCommunityId())) {
                community.addUser(user);
            }
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
     * @return the name of the view to be rendered
     */
    @PostMapping("/join/{communityId}")
    public String joinCommunity(@PathVariable Integer communityId, HttpSession session) {

        // Check if user is logged in
        if (session.getAttribute("user") == null) {return "redirect:/auth/login";}

        User user = (User) session.getAttribute("user");
        Optional<Community> optionalCommunity = communityRepository.findById(communityId);
        
        if (optionalCommunity.isPresent()) {
            Community community = optionalCommunity.get();
            if (!communityRepository.existsByUsers_IdUserAndCommunityId(user.getIdUser(), communityId)) {
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
     * @return the name of the view to be rendered
     */
    @PostMapping("/leave/{communityId}")
    public String leaveCommunity(@PathVariable Integer communityId, HttpSession session) {
        
        // Check if user is logged in
        if (session.getAttribute("user") == null) {return "redirect:/auth/login";}

        User user = (User) session.getAttribute("user");
        Optional<Community> optionalCommunity = communityRepository.findById(communityId);
        
        if (optionalCommunity.isPresent()) {
            Community community = optionalCommunity.get();
            if (communityRepository.existsByUsers_IdUserAndCommunityId(user.getIdUser(), communityId)) {
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
     * @return the community created
     * @throws IOException if an I/O error occurs
     */
    @PostMapping(value = "/new", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ResponseEntity<Community> createCommunity(@RequestParam String communityName,
                                                 @RequestParam String communityDescription,
                                                 @RequestParam MultipartFile communityImage,
                                                 HttpSession session) throws IOException {
        if(session.getAttribute("user") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User sessionUser = (User) session.getAttribute("user");
        User user = userRepository.findById(sessionUser.getIdUser()).orElseThrow();

        Community community = new Community();
        community.setCommunityName(communityName);
        community.setCommunityDescription(communityDescription);

        String fileName;
        if (!communityImage.isEmpty()) {
            String rawFileName = UUID.randomUUID().toString() + "_" + communityImage.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, rawFileName);
            Files.createDirectories(filePath.getParent());
            Files.copy(communityImage.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            fileName = "/images/community/" + rawFileName;
        } else {
            fileName = "/images/community/defaultCommunity.png";
        }
        community.setCommunityImagePath(fileName);

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

        session.setAttribute("user", user);

        return ResponseEntity.ok(community);
    }

    /**
     * Edit a community
     * @param communityId the ID of the community
     * @param communityName the name of the community
     * @param communityDescription the description of the community
     * @param communityImage the image of the community
     * @param session the session of the user
     * @return the community edited
     * @throws IOException if an I/O error occurs
     */
    @PostMapping(value = "/edit/{communityId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ResponseEntity<Community> editCommunity(@PathVariable Integer communityId,
                                                 @RequestParam String communityName,
                                                 @RequestParam String communityDescription,
                                                 @RequestParam MultipartFile communityImage,
                                                 HttpSession session) throws IOException {
        if(session.getAttribute("user") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User sessionUser = (User) session.getAttribute("user");
        User user = userRepository.findById(sessionUser.getIdUser()).orElseThrow();

        Community community = communityRepository.findById(communityId).orElseThrow();
        community.setCommunityName(communityName);
        community.setCommunityDescription(communityDescription);

        String fileName;
        if (!communityImage.isEmpty()) {
            // Delete the old image if it exists and is not the default image
            String oldImagePath = community.getCommunityImagePath();
            if (oldImagePath != null && !oldImagePath.contains("defaultCommunity.png")) {
                Path oldImageFilePath = Paths.get("src/main/resources/static" + oldImagePath);
                try {
                    Files.deleteIfExists(oldImageFilePath);
                } catch (IOException e) {
                    System.err.println("Erreur lors de la suppression de l'image : " + e.getMessage());
                }
            }
            String rawFileName = UUID.randomUUID().toString() + "_" + communityImage.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, rawFileName);
            Files.createDirectories(filePath.getParent());
            Files.copy(communityImage.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            fileName = "/images/community/" + rawFileName;
        } else {
            fileName = community.getCommunityImagePath();
        }
        community.setCommunityImagePath(fileName);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        community.setCommunityCreationDate(LocalDate.now().format(formatter));

        community.setNumberOfMembers(community.getUsers().size());
        community.setNumberOfPosts(communityRepository.countPostsInCommunity(community.getCommunityId()));
        communityRepository.save(community);

        session.setAttribute("user", user);

        return ResponseEntity.ok(community);
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

                //remove community picture
                String imagePath = community.getCommunityImagePath();
                if (imagePath != null && !imagePath.contains("defaultCommunity.png")) {
                    Path imageFilePath = Paths.get("src/main/resources/static" + imagePath);
                    try {
                        Files.deleteIfExists(imageFilePath);
                    } catch (IOException e) {
                        System.err.println("Erreur lors de la suppression de l'image : " + e.getMessage());
                    }
                }
                
                // delete community
                communityRepository.deleteById(communityId);
                communityRepository.flush();

                // Reload user to avoid null pointer exception
                user = userRepository.findById(user.getIdUser()).orElse(null);
                session.setAttribute("user", user);
            }
        }

        return "redirect:/community";
    }

    /**
     * Get a community to see its posts
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
        
        //reload user to avoid null pointer exception
        user = userRepository.findById(user.getIdUser()).orElse(null);
        session.setAttribute("user", user);

        //community doesn't exist
        Optional<Community> optionalCommunity = communityRepository.findById(communityId);
        if (!optionalCommunity.isPresent()) {
            return "redirect:/community";
        }

        //user is not subscribed to the community
        if (!communityRepository.existsByUsers_IdUserAndCommunityId(user.getIdUser(), communityId)) {
            return "redirect:/community";
        }

        List<Post> posts = communityRepository.findById(communityId).get().getPosts();

        //community has no posts yet
        if(posts.isEmpty()) {
            model.addAttribute("posts", new ArrayList<>());
            model.addAttribute("user", user);
            model.addAttribute("currentUri", request.getRequestURI());
            return "community/details";
        }

        Map<Integer, ReactionType> userReactedPosts = new HashMap<>();
        Map<Integer, Boolean> postMediaExistsMap = new HashMap<>();
        Map<Integer, Integer> postCommentCounts = new HashMap<>();
        Map<Integer, Long> repostCounts = new HashMap<>();
        Map<Integer, Boolean> repostedPostIds = new HashMap<>();

        for (Post post : posts) {
            //create the composite ID
            UserPostReactionID reactionId = new UserPostReactionID();
            reactionId.setUserId(user.getIdUser());
            reactionId.setPostId(post.getPostId());
            
            //get the reaction type from the repository
            List<UserPostReaction> reactions = userPostReactionRepository.findByPost_PostId(post.getPostId());
            post.setReactions(reactions);
            if (reactions != null && !reactions.isEmpty()) {
                // Find the reaction for this user
                for (UserPostReaction r : reactions) {
                    if (r.getUser().equals(user)) {
                        userReactedPosts.put(post.getPostId(), r.getReaction().getReactionType());
                        break;
                    }
                }

                // count reactions by type
                long likeCount = reactions.stream()
                    .filter(r -> r.getReaction().getReactionType() == ReactionType.LIKE)
                    .count();
                long loveCount = reactions.stream()
                    .filter(r -> r.getReaction().getReactionType() == ReactionType.LOVE)
                    .count();
                long laughCount = reactions.stream()
                    .filter(r -> r.getReaction().getReactionType() == ReactionType.LAUGH)
                    .count();
                long cryCount = reactions.stream()
                    .filter(r -> r.getReaction().getReactionType() == ReactionType.CRY)
                    .count();
                long angryCount = reactions.stream()
                    .filter(r -> r.getReaction().getReactionType() == ReactionType.ANGRY)
                    .count();

                post.setLikeCount(likeCount);
                post.setLoveCount(loveCount);
                post.setLaughCount(laughCount);
                post.setCryCount(cryCount);
                post.setAngryCount(angryCount);
            }

            //format date
            LocalDate date = LocalDate.parse(post.getPostPublicationDate());
            post.setFormattedDate(formatPostDate(date));

            //map of post media presence
            postMediaExistsMap.put(post.getPostId(), postMediaExists(post.getPostMediaPath()));

            //count comments
            postCommentCounts.put(post.getPostId(), countAllComments(post));

            //count reposts
            long repostCount = repostRepository.countByOriginalPost(post);
            repostCounts.put(post.getPostId(), repostCount);

            //check if user has reposted this post
            boolean hasReposted = repostRepository.findByUser(user)
            .stream()
            .anyMatch(repost -> repost.getOriginalPost().getPostId().equals(post.getPostId()));
            repostedPostIds.put(post.getPostId(), hasReposted);
        }
        
        // sort posts by date
        posts.sort((post1, post2) -> {
            LocalDate date1 = LocalDate.parse(post1.getPostPublicationDate());
            LocalDate date2 = LocalDate.parse(post2.getPostPublicationDate());
            return date2.compareTo(date1);
        });

        model.addAttribute("user", user);
        model.addAttribute("currentUri", request.getRequestURI());
        model.addAttribute("postMediaExistsMap", postMediaExistsMap);
        model.addAttribute("posts", posts);
        model.addAttribute("userReactedPosts", userReactedPosts);
        model.addAttribute("postCommentCounts", postCommentCounts);
        model.addAttribute("repostCounts", repostCounts);
        model.addAttribute("repostedPostIds", repostedPostIds);
        
        return "community/details";
    }
}