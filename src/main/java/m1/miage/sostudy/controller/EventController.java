package m1.miage.sostudy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for the Event entity
 */
@Controller
@RequestMapping("/event")
public class EventController {

    /**
     * Get an event
     * @param eventid the id of the event
     * @return the name of the view to be rendered
     */
    @GetMapping("/{eventid}")
    public String getEvent(@PathVariable int eventid) {
        return "event";
    }

    /**
     * formular to create a new event
     * @return the name of the view to be rendered
     */
    @GetMapping("/new")
    public String newEvent() {
        return "event";
    }


    /**
     * create a new event
     * @return the name of the view to be rendered
     */
    @PostMapping("/new")
    public String createEvent() {
        return "redirect:/event";
    }


    /**
     * formular to edit an event
     * @param eventid the id of the event
     * @return the name of the view to be rendered
     */
    @GetMapping("/edit/{eventid}")
    public String editEvent(@PathVariable int eventid) {
        return "event";
    }

    /**
     * edit an event
     * @param eventid the id of the event
     * @return the name of the view to be rendered
     */
    @PostMapping("/edit/{eventid}")
    public String updateEvent(@PathVariable int eventid) {
        return "redirect:/event";
    }

    /**
     * delete an event
     * @param eventid the id of the event
     * @return the name of the view to be rendered
     */
    @PostMapping("/delete/{eventid}")
    public String deleteEvent(@PathVariable int eventid) {
        return "redirect:/event";
    }

    /**
     * join an event
     * @param eventid the id of the event
     * @return the name of the view to be rendered
     */
    @PostMapping("/join/{eventid}")
    public String joinEvent(@PathVariable int eventid) {
        return "redirect:/event";
    }

    /**
     * leave an event
     * @param eventid the id of the event
     * @return the name of the view to be rendered
     */
    @PostMapping("/leave/{eventid}")
    public String leaveEvent(@PathVariable int eventid) {
        return "redirect:/event";
    }

}