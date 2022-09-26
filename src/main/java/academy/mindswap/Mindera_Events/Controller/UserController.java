package academy.mindswap.Mindera_Events.Controller;

import academy.mindswap.Mindera_Events.Model.Event;
import academy.mindswap.Mindera_Events.Model.User;
import academy.mindswap.Mindera_Events.Service.EventService;
import academy.mindswap.Mindera_Events.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final EventService eventService;

    public UserController(UserService userService, EventService eventService) {
        this.userService = userService;
        this.eventService = eventService;
    }

    @PostMapping("/createevent")
    public Event createEvent(@RequestBody Event event){
        return eventService.createEvent(event);
    }


    @PostMapping("/createUser")
    public User createUser(@RequestBody User user){return userService.createUser(user);}
    @GetMapping("/getuserlist")
    public ResponseEntity<List<User>> getUserList() {
        List<User> userList = userService.getUserList();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }
    @GetMapping("/geteventlist")
    public ResponseEntity<List<Event>> getEventList() {
        List<Event> eventList = eventService.getEventList();
        return new ResponseEntity<>(eventList, HttpStatus.OK);
    }
    @GetMapping("/getbyrole/{officeRole}")
    public ResponseEntity<List<User>> getByRole(@PathVariable String officeRole) {
        List<User> userList = userService.getByRole(officeRole);
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }
    @GetMapping("/getbydepartment/{department}")
    public ResponseEntity<List<User>> getByDepartment(@PathVariable String department) {
        List<User> userList = userService.getByDepartment(department);
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }@GetMapping("/getbytitle/{title}")
    public ResponseEntity<Event> getByTitle(@PathVariable String title) {
        Event events = eventService.getByTitle(title);
        return new ResponseEntity<>(events, HttpStatus.OK);
    }
    @GetMapping("/getbystate/{state}")
    public ResponseEntity<Event> getByState(@PathVariable String state) {
        Event event = eventService.getByState(state);
        return new ResponseEntity<>(event, HttpStatus.OK);
    }
    @GetMapping("/getbydate/{date}")
    public ResponseEntity<Event> getByDate(@PathVariable String date){
        Event event = eventService.getByDate(date);
        return new ResponseEntity<>(event, HttpStatus.OK);
    }
    @GetMapping("/getbytype/{type}")
    public ResponseEntity<Event> getByType(@PathVariable String type) {
        Event event = eventService.getByType(type);
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

}
