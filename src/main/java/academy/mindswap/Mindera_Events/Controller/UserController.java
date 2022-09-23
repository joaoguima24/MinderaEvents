package academy.mindswap.Mindera_Events.Controller;

import academy.mindswap.Mindera_Events.Model.Event;
import academy.mindswap.Mindera_Events.Model.User;
import academy.mindswap.Mindera_Events.Service.EventService;
import academy.mindswap.Mindera_Events.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/user")
public class UserController {

    private final UserService userService;
    private final EventService eventService;

    public UserController(UserService userService, EventService eventService) {
        this.userService = userService;
        this.eventService = eventService;
    }
    @GetMapping
    public ResponseEntity<List<User>> getUserList() {
        List<User> personList = userService.getUserList();
        return new ResponseEntity<>(personList, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<Event>> getEventList() {
        List<Event> personList = eventService.getEventList();
        return new ResponseEntity<>(personList, HttpStatus.OK);
    }

}
