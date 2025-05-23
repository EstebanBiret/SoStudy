package utc.miage.sostudy.controller;

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
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import utc.miage.sostudy.model.entity.Event;
import utc.miage.sostudy.model.entity.User;
import utc.miage.sostudy.repository.EventRepository;
import utc.miage.sostudy.repository.UserRepository;

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
     * User repository
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Path for uploading files.
     */
    private String uploadDir = "./src/main/resources/static/images/events/";

    /**
     * Logger
     */
    private static final Logger LOGGER = Logger.getLogger(EventController.class.getName());

    /**
     * Redirect to login
     */
    private static final String LOGIN = "redirect:/auth/login";

    /**
     * Redirect to event
     */
    private static final String EVENT = "redirect:/event";

    /**
     * Default event image path
     */
    private static final String DEFAULT_EVENT_IMAGE = "/images/events/defaultEventImage.jpg";

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
        if(session.getAttribute("user") == null) {return LOGIN;}
        
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
            
            // Check if user is member of this community
            if(eventRepository.existsByUsers_IdUserAndEventId(user.getIdUser(), event.getEventId())) {
                event.getUsers().add(user);
            }
        }

        model.addAttribute("events", events);
        model.addAttribute("user", user);
        model.addAttribute("currentUri", request.getRequestURI());

        return "event/list";
    }

    /**
     * Create a new event
     * @param eventName the name of the event
     * @param eventDescription the description of the event
     * @param eventBeginningDate the beginning date of the event
     * @param eventEndDate the end date of the event
     * @param eventLocation the location of the event
     * @param eventImage the image of the event
     * @param session the session of the user
     * @return the name of the view to be rendered
     * @throws IOException if an I/O error occurs
     */
    @PostMapping(value = "/new", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Event> createEvent(@RequestParam String eventName,
                              @RequestParam String eventDescription,
                              @RequestParam String eventBeginningDate,
                              @RequestParam String eventEndDate,
                              @RequestParam String eventLocation,
                              @RequestParam MultipartFile eventImage,
                              HttpSession session) throws IOException {
        
        //user not logged in
        if(session.getAttribute("user") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User sessionUser = (User) session.getAttribute("user");
        User user = userRepository.findById(sessionUser.getIdUser()).orElseThrow();

        Event event = new Event();
        event.setEventName(eventName);
        event.setEventDescription(eventDescription);
        event.setEventBeginningDate(eventBeginningDate);
        event.setEventEndDate(eventEndDate);
        event.setEventLocation(eventLocation);

        String fileName;
        if (!eventImage.isEmpty()) {
            String rawFileName = UUID.randomUUID().toString() + "_" + eventImage.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, rawFileName);
            Files.createDirectories(filePath.getParent());
            Files.copy(eventImage.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            fileName = "/images/events/" + rawFileName;
        } else {
            fileName = DEFAULT_EVENT_IMAGE;
        }
        event.setEventImagePath(fileName);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        event.setEventPublicationDate(LocalDate.now().format(formatter));

        event.setUserCreator(user);
        event.setNumberOfMembers(1);
        event.getUsers().add(user);
        eventRepository.save(event);

        user.getSubscribedEvents().add(event);
        user.addCreatedEvent(event);
        userRepository.save(user);

        session.setAttribute("user", user);

        return ResponseEntity.ok(event);
    }

    /**
     * Edit an event
     * @param eventId the id of the event
     * @param eventName the name of the event
     * @param eventDescription the description of the event
     * @param eventBeginningDate the beginning date of the event
     * @param eventEndDate the end date of the event
     * @param eventLocation the location of the event
     * @param eventImage the image of the event
     * @param session the session of the user
     * @return the name of the view to be rendered
     * @throws IOException if an I/O error occurs
     */
    @PostMapping(value = "/edit/{eventId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Event> updateEvent(@PathVariable int eventId,
                              @RequestParam String eventName,
                              @RequestParam String eventDescription,
                              @RequestParam String eventBeginningDate,
                              @RequestParam String eventEndDate,
                              @RequestParam String eventLocation,
                              @RequestParam MultipartFile eventImage,
                              HttpSession session) throws IOException {

        if(session.getAttribute("user") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User sessionUser = (User) session.getAttribute("user");
        User user = userRepository.findById(sessionUser.getIdUser()).orElseThrow();

        Event event = eventRepository.findById(eventId).orElseThrow();
        event.setEventName(eventName);
        event.setEventDescription(eventDescription);

        String fileName;
        if (!eventImage.isEmpty()) {
            // Delete the old image if it exists and is not the default image
            String oldImagePath = event.getEventImagePath();
            if (oldImagePath != null && !oldImagePath.contains("defaultEventImage.jpg")) {
                Path oldImageFilePath = Paths.get("src/main/resources/static" + oldImagePath);
                try {
                    Files.deleteIfExists(oldImageFilePath);
                } catch (IOException e) {
                    LOGGER.info("Erreur lors de la suppression de l'image : " + e.getMessage());
                }
            }
            String rawFileName = UUID.randomUUID().toString() + "_" + eventImage.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, rawFileName);
            Files.createDirectories(filePath.getParent());
            Files.copy(eventImage.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            fileName = "/images/events/" + rawFileName;
        } else {
            fileName = event.getEventImagePath();
        }
        event.setEventImagePath(fileName);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        event.setEventPublicationDate(LocalDate.now().format(formatter));

        event.setEventLocation(eventLocation);
        event.setEventBeginningDate(eventBeginningDate);
        event.setEventEndDate(eventEndDate);

        event.setNumberOfMembers(event.getUsers().size());
        eventRepository.save(event);

        session.setAttribute("user", user);

        return ResponseEntity.ok(event);
    }

    /**
     * Delete an event
     * @param eventid the id of the event
     * @param model the model of the view
     * @param session the session of the user
     * @return the name of the view to be rendered
     */
    @PostMapping("/delete/{eventid}")
    public String deleteEvent(@PathVariable int eventid, Model model, HttpSession session) {
        
        //user not logged in
        if(session.getAttribute("user") == null) {return LOGIN;}

        User user = (User) session.getAttribute("user");
        Optional<Event> optionalEvent = eventRepository.findById(eventid);

        // check if event exists
        if (optionalEvent.isPresent()) {
            Event event = optionalEvent.get();
            
            // check if user is the creator
            if (user.getIdUser() == event.getUserCreator().getIdUser()) {
                
                // remove user from event
                List<User> users = event.getUsers();
                for (User u : users) {
                    u.getSubscribedEvents().remove(event);
                    userRepository.save(u);
                }

                //remove event picture
                String imagePath = event.getEventImagePath();
                if (imagePath != null && !imagePath.contains("defaultEventImage.jpg")) {
                    Path imageFilePath = Paths.get("src/main/resources/static" + imagePath);
                    try {
                        Files.deleteIfExists(imageFilePath);
                    } catch (IOException e) {
                        LOGGER.info("Erreur lors de la suppression de l'image : " + e.getMessage());
                    }
                }

                // delete event
                eventRepository.deleteById(eventid);
                eventRepository.flush();

                // Reload user to avoid null pointer exception
                user = userRepository.findById(user.getIdUser()).orElse(null);
                session.setAttribute("user", user);
            }
        }

        return EVENT;
    }

    /**
     * Join an event
     * @param eventid the id of the event
     * @param session the session of the user
     * @return the name of the view to be rendered
     */
    @PostMapping("/join/{eventid}")
    public String joinEvent(@PathVariable int eventid, HttpSession session) {
        
        // Check if user is logged in
        if (session.getAttribute("user") == null) {return LOGIN;}

        User user = (User) session.getAttribute("user");
        Optional<Event> optionalEvent = eventRepository.findById(eventid);
        
        if (optionalEvent.isPresent()) {
            Event event = optionalEvent.get();
            if (!eventRepository.existsByUsers_IdUserAndEventId(user.getIdUser(), eventid)) {
                user.getSubscribedEvents().add(event);
                event.getUsers().add(user);
                userRepository.save(user);
                eventRepository.save(event);
            }
        }
        return EVENT;
    }

    /**
     * Leave an event
     * @param eventid the id of the event
     * @param session the session of the user
     * @return the name of the view to be rendered
     */
    @PostMapping("/leave/{eventid}")
    public String leaveEvent(@PathVariable int eventid, HttpSession session) {
        
        // Check if user is logged in
        if (session.getAttribute("user") == null) {return LOGIN;}

        User user = (User) session.getAttribute("user");
        Optional<Event> optionalEvent = eventRepository.findById(eventid);
        
        if (optionalEvent.isPresent()) {
            Event event = optionalEvent.get();
            if (eventRepository.existsByUsers_IdUserAndEventId(user.getIdUser(), eventid)) {
                user.getSubscribedEvents().remove(event);
                event.getUsers().remove(user);
                userRepository.save(user);
                eventRepository.save(event);
            }
        }
        return EVENT;
    }

}