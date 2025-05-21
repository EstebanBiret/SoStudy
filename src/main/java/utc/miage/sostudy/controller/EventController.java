package utc.miage.sostudy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import utc.miage.sostudy.model.entity.Event;
import utc.miage.sostudy.model.entity.User;
import utc.miage.sostudy.repository.EventRepository;

/**
 * Controller for the Event entity
 */
@Controller
@RequestMapping("/event")
public class EventController {

    /**
     * Event repository
     */
    @Autowired
    private EventRepository eventRepository;

    /**
     * Path for uploading files.
     */
    private final String UPLOAD_DIR = "./src/main/resources/static/images/events/";

    /**
     * Get all events
     * @param model the model of the view
     * @param session the session of the user
     * @param request the request of the user
     * @return the name of the view to be rendered
     */
    @GetMapping("")
    public String getAllEvents(Model model, HttpSession session, HttpServletRequest request) {
        //user not logged in
        if(session.getAttribute("user") == null) {return "redirect:/auth/login";}
        
        User user = (User) session.getAttribute("user");
        List<Event> events = eventRepository.findAllOrderByPublicationDate();
        
        //update number of members and posts
        for (Event event : events) {
            event.setNumberOfMembers(eventRepository.countUsersInEvent(event.getEventId()));
            
            //format dates
            String[] dateParts = event.getEventPublicationDate().split("-");
            String formattedDate = Integer.parseInt(dateParts[2]) + " " +
                CommunityController.getMonthName(Integer.parseInt(dateParts[1])) + " " +
                dateParts[0];
            event.setEventPublicationDate(formattedDate);

            String[] eventDateParts = event.getEventBeginningDate().split("-");
            String formattedEventDate = Integer.parseInt(eventDateParts[2]) + " " +
                CommunityController.getMonthName(Integer.parseInt(eventDateParts[1])) + " " +
                eventDateParts[0];
            event.setEventBeginningDate(formattedEventDate);

            String[] eventEndDateParts = event.getEventEndDate().split("-");
            String formattedEventEndDate = Integer.parseInt(eventEndDateParts[2]) + " " +
                CommunityController.getMonthName(Integer.parseInt(eventEndDateParts[1])) + " " +
                eventEndDateParts[0];
            event.setEventEndDate(formattedEventEndDate);
            
            System.out.println(event.getEventName() + "-----------");

            // Check if user is member of this community
            if(eventRepository.existsByUsers_IdUserAndEventId(user.getIdUser(), event.getEventId())) {
                System.out.println("User is member of this event");
                event.getUsers().add(user);
                System.out.println(user.getPseudo());
                System.out.println(event.getEventName());
            }
        }

        model.addAttribute("events", events);
        model.addAttribute("user", user);
        model.addAttribute("currentUri", request.getRequestURI());

        return "event/list";
    }

    /**
     * Create a new event
     * @return the name of the view to be rendered
     */
    @PostMapping("/new")
    public String createEvent() {
        return "redirect:/event";
    }

    /**
     * Edit an event
     * @param eventid the id of the event
     * @return the name of the view to be rendered
     */
    @PostMapping("/edit/{eventid}")
    public String updateEvent(@PathVariable int eventid) {
        return "redirect:/event";
    }

    /**
     * Delete an event
     * @param eventid the id of the event
     * @return the name of the view to be rendered
     */
    @PostMapping("/delete/{eventid}")
    public String deleteEvent(@PathVariable int eventid) {
        return "redirect:/event";
    }

    /**
     * Join an event
     * @param eventid the id of the event
     * @return the name of the view to be rendered
     */
    @PostMapping("/join/{eventid}")
    public String joinEvent(@PathVariable int eventid) {
        return "redirect:/event";
    }

    /**
     * Leave an event
     * @param eventid the id of the event
     * @return the name of the view to be rendered
     */
    @PostMapping("/leave/{eventid}")
    public String leaveEvent(@PathVariable int eventid) {
        return "redirect:/event";
    }

}