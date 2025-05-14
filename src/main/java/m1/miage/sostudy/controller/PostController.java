package m1.miage.sostudy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import m1.miage.sostudy.model.entity.Post;

/**
 * Controller for the post
 */
@Controller
@RequestMapping("/post")
public class PostController {

    /**
     * Get a post by id
     * @param id the id of the post
     * @param model the model
     * @return the post details page
     */
    @GetMapping("/{id}")
    public String getPost(@PathVariable Integer id, Model model) {
        return "post";
    }

    /**
     * Publish a post
     * @param post the post to publish
     * @return the publish post page
     */
    @PostMapping("/publish")
    public String publishPost(@ModelAttribute Post post) {
        return "publish_post";
    }

    /**
     * Edit a post
     * @param id the id of the post
     * @param model the model
     * @return the edit post page (form)
     */
    @GetMapping("/edit/{id}")
    public String editPost(@PathVariable Integer id, Model model) {
        return "edit_post";
    }

    /**
     * Update a post
     * @param id the id of the post
     * @param post the post to update
     * @return the post details page
     */
    @PostMapping("/edit/{id}")
    public String updatePost(@PathVariable Integer id, @ModelAttribute Post post) {
        return "redirect:/post/" + id;
    }

    /**
     * Delete a post
     * @param id the id of the post
     * @return the home page (feed)
     */
    @GetMapping("/delete/{id}")
    public String deletePost(@PathVariable Integer id) {
        return "redirect:/";
    }
    
}