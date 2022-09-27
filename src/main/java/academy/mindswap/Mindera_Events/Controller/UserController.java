package academy.mindswap.Mindera_Events.Controller;

import academy.mindswap.Mindera_Events.Commands.*;
import academy.mindswap.Mindera_Events.Exceptions.EventNotFoundException;
import academy.mindswap.Mindera_Events.Exceptions.UserNotFoundException;

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

    public EventDto createEvent(@RequestBody EventDto dto){return eventService.createEvent(dto);}

    @PostMapping("/createUser")
    public CreatingUserDto createUser(@RequestBody CreatingUserDto dto){return userService.createUser(dto);}
    @PutMapping("/updateevent")
    public ResponseEntity<EventDto> updateEventStateById(@RequestBody EventDto dto) throws Exception {
        return eventService.updateEvent(dto);
    }
    @GetMapping("/getuserlist")
    public ResponseEntity<List<DisplayUserDto>> getUserList() {
        List<DisplayUserDto> userList = userService.getUserList();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }
    @GetMapping("/geteventlist")
    public ResponseEntity<List<DisplayEventListDto>> getEventList() {
        List<DisplayEventListDto> eventList = eventService.getEventList();
        return new ResponseEntity<>(eventList, HttpStatus.OK);
    }
    @GetMapping("/getbyrole/{officeRole}")
    public ResponseEntity<List<DisplayUserDto>> getByRole(@PathVariable String officeRole) throws UserNotFoundException {
        List<DisplayUserDto> userList = userService.getByRole(officeRole);
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }
    @GetMapping("/getbydepartment/{department}")
    public ResponseEntity<List<DisplayUserDto>> getByDepartment(@PathVariable String department) throws UserNotFoundException {
        List<DisplayUserDto> userList = userService.getByDepartment(department);
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String id) throws UserNotFoundException {
        return ResponseEntity.ok(userService.getUserById(id));
    }
    @GetMapping("/getbytitle/{title}")

    public ResponseEntity<List<DisplayEventListDto>> getByTitle(@PathVariable String title) throws EventNotFoundException {
        List<DisplayEventListDto> eventList = eventService.getByTitle(title).getBody();
        return new ResponseEntity<>(eventList, HttpStatus.OK);
    }
    @GetMapping("/getbystate/{state}")
    public ResponseEntity<List<DisplayEventListDto>> getByState(@PathVariable String state) throws EventNotFoundException {
        List<DisplayEventListDto> eventList = eventService.getByState(state).getBody();
        return new ResponseEntity<>(eventList, HttpStatus.OK);
    }
    @GetMapping("/getbydate/{date}")
    public ResponseEntity<List<DisplayEventListDto>> getByDate(@PathVariable String date) throws EventNotFoundException {
        List<DisplayEventListDto> eventList = eventService.getByDate(date).getBody();
        return new ResponseEntity<>(eventList, HttpStatus.OK);
    }
    @GetMapping("/getbytype/{type}")
    public ResponseEntity<List<DisplayEventListDto>> getByType(@PathVariable String type) throws EventNotFoundException {
        List<DisplayEventListDto> eventList = eventService.getByType(type).getBody();
        return new ResponseEntity<>(eventList, HttpStatus.OK);

    }
      @PutMapping("/updateuser/{id}")
      public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User user) {
          return userService.updateUser(id, user);
      }
    @PostMapping("/{idUser}/{id}")
    public void addEventToUser(@PathVariable String idUser, @PathVariable String id) throws UserNotFoundException {
        eventService.relateEventToUser(idUser, id);

    }
    
}
