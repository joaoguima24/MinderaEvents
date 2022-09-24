package academy.mindswap.Mindera_Events.Controller;

import academy.mindswap.Mindera_Events.Model.Event;
import academy.mindswap.Mindera_Events.Model.User;
import academy.mindswap.Mindera_Events.Service.EventService;
import academy.mindswap.Mindera_Events.Service.UserService;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/user")
public class UserController {

    private final UserService userService;
    private final EventService eventService;

    public UserController(UserService userService, EventService eventService) {
        this.userService = userService;
        this.eventService = eventService;
    }

    @PostMapping("/user/createevent")
    public Event createEvent(@RequestBody Event event){
        return eventService.createEvent(event);
    }

    @PutMapping("/user/updateeventstate")
    public Event updateEventState(@RequestBody Event event) throws ChangeSetPersister.NotFoundException {
        return eventService.updateEventState(event);
    }

    @PutMapping("/user/addtoevent")
    public Event addUserToEvent(@RequestBody Event eventId ) throws ChangeSetPersister.NotFoundException {
        return eventService.addUserToEvent(eventId);
    }
    @GetMapping("/user/getuserlist")
    public ResponseEntity<List<User>> getUserList() {
        List<User> userList = userService.getUserList();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }
    @GetMapping("/user/geteventlist")
    public ResponseEntity<List<Event>> getEventList() {
        List<Event> eventList = eventService.getEventList();
        return new ResponseEntity<>(eventList, HttpStatus.OK);
    }
}
