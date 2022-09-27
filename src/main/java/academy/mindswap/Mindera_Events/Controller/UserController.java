package academy.mindswap.Mindera_Events.Controller;

import academy.mindswap.Mindera_Events.Commands.DisplayEventListDto;
import academy.mindswap.Mindera_Events.Commands.EventConverter;
import academy.mindswap.Mindera_Events.Exceptions.EventNotFoundException;
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

import academy.mindswap.Mindera_Events.Service.EmailSenderService;



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
    public ResponseEntity<List<DisplayEventListDto>> getEventList() {
        return new ResponseEntity<>(eventService.getEventList(), HttpStatus.OK);
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
    public ResponseEntity<List<DisplayEventListDto>> getByTitle(@PathVariable String title) throws EventNotFoundException {
        return new ResponseEntity<>(eventService.getByTitle(title), HttpStatus.OK);
    }
    @GetMapping("/getbystate/{state}")
    public ResponseEntity<List<DisplayEventListDto>> getByState(@PathVariable String state) throws EventNotFoundException {
        return new ResponseEntity<>(eventService.getByState(state), HttpStatus.OK);
    }
    @GetMapping("/getbydate/{date}")
    public ResponseEntity<List<DisplayEventListDto>> getByDate(@PathVariable String date) throws EventNotFoundException {
        return new ResponseEntity<>(eventService.getByDate(date), HttpStatus.OK);
    }
    @GetMapping("/getbytype/{type}")
    public ResponseEntity<List<Event>> getByType(@PathVariable String type) {
        List<Event> event = eventService.getByType(type);
        return new ResponseEntity<>(event, HttpStatus.OK);
    }
      @PutMapping("/updateuser/{id}")
      public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User user) {
          return userService.updateUser(id, user);
      }
    @PutMapping("/updateevent/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable String id, @RequestBody Event event) {

        return new ResponseEntity<>(eventService.updateEvent(EventConverter.getEventToDto(event)),HttpStatus.ACCEPTED);
    }

}
