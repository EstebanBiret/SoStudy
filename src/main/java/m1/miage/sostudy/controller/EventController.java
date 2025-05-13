package m1.miage.sostudy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for the Event entity
 */
@Controller
@RequestMapping("/event")
public class EventController {

    /**
     * Get an events
     */
    @GetMapping("/{eventid}")
    public String getEvent() {
        return "event";
    }

    /**
     * formular to create a new event
     */
    @GetMapping("/new")
    public String newEvent() {
        return "event";
    }


    /**
     * create a new event
     */
    @PostMapping("/new")
    public String createEvent() {
        return "redirect:/event";
    }


    /**
     * formular to edit an event
     */
    @GetMapping("/edit/{eventid}")
    public String editEvent() {
        return "event";
    }

    /**
     * edit an event
     */
    @PostMapping("/edit/{eventid}")
    public String updateEvent() {
        return "redirect:/event";
    }

    /**
     * delete an event
     */
    @PostMapping("/delete/{eventid}")
    public String deleteEvent() {
        return "redirect:/event";
    }

    /**
     * join an event
     */
    @PostMapping("/join/{eventid}")
    public String joinEvent() {
        return "redirect:/event";
    }

    /**
     * leave an event
     */
    @PostMapping("/leave/{eventid}")
    public String leaveEvent() {
        return "redirect:/event";
    }




}
