package m1.miage.sostudy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for handling requests to the index page.
 * This controller is responsible for displaying the index page of the application.
 */
@Controller
@RequestMapping("/")
public class IndexController {
    /**
     * Displays the index page.
     *
     * @return the name of the view to be rendered
     */
    @RequestMapping
    public String index() {
        return "index"; // Return the name of the view (e.g., Thymeleaf template)
    }
}
