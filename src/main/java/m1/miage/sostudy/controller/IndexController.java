package m1.miage.sostudy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;

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
     * @param request the HttpServletRequest object
     * @param model the Model object
     * @return the name of the view to be rendered
     */
    @GetMapping("/")
    public String index(HttpServletRequest request, Model model) {
        model.addAttribute("currentUri", request.getRequestURI());
        return "index";
    }

}
