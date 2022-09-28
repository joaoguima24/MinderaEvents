package academy.mindswap.Mindera_Events.Controller;

import academy.mindswap.Mindera_Events.Commands.*;
import academy.mindswap.Mindera_Events.Exceptions.EventNotFoundException;
import academy.mindswap.Mindera_Events.Exceptions.UserNotFoundException;
import academy.mindswap.Mindera_Events.Model.User;
import academy.mindswap.Mindera_Events.Service.EventService;
import academy.mindswap.Mindera_Events.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.io.IOException;
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public EventDto createEvent(@Valid @RequestBody EventDto dto){return eventService.createEvent(dto);}

    @PostMapping("/createUser")
    @PreAuthorize("permitAll()")
    public CreatingUserDto createUser(@Valid @RequestBody CreatingUserDto dto){return userService.createUser(dto);}

    @PutMapping("/updateevent")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<EventDto> updateEventStateById( @Valid @RequestBody EventDto dto) throws Exception {
        return new ResponseEntity<>(eventService.updateEvent(dto), HttpStatus.OK);
    }
    @GetMapping("/external")
    @PreAuthorize("permitAll()")
    public Object getQrCode() throws IOException, InterruptedException {
        return userService.qrCode("1");
    }
    @GetMapping("/getuserlist")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<DisplayUserDto>> getUserList() {
        return new ResponseEntity<>(userService.getUserList(), HttpStatus.OK);
    }

    @GetMapping("/geteventlist")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<DisplayEventListDto>> getEventList() {
        return new ResponseEntity<>(eventService.getEventList(), HttpStatus.OK);
    }

    @GetMapping("/getbyrole/{officeRole}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<DisplayUserDto>> getByRole(@PathVariable String officeRole) throws UserNotFoundException {
        return new ResponseEntity<>(userService.getByRole(officeRole), HttpStatus.OK);
    }

    @GetMapping("/getbydepartment/{department}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<DisplayUserDto>> getByDepartment(@PathVariable String department) throws UserNotFoundException {
        return new ResponseEntity<>(userService.getByDepartment(department), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserDto> getUserById(@PathVariable String id) throws UserNotFoundException {
        return ResponseEntity.ok(userService.getUserById(id));
    }
    @GetMapping("/getbytitle/{title}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<DisplayEventListDto>> getByTitle(@PathVariable String title) throws EventNotFoundException {
        return new ResponseEntity<>(eventService.getByTitle(title), HttpStatus.OK);
    }
    @GetMapping("/getbystate/{state}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<DisplayEventListDto>> getByState(@PathVariable String state) throws EventNotFoundException {
        return new ResponseEntity<>(eventService.getByState(state), HttpStatus.OK);
    }
    @GetMapping("/getbydate/{date}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<DisplayEventListDto>> getByDate(@PathVariable String date) throws EventNotFoundException {
        return new ResponseEntity<>(eventService.getByDate(date), HttpStatus.OK);
    }
    @GetMapping("/getbytype/{type}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<DisplayEventListDto>> getByType(@PathVariable String type) throws EventNotFoundException {
        return new ResponseEntity<>(eventService.getByType(type), HttpStatus.OK);

    }
      @PutMapping("/updateuser/{id}")
      @PreAuthorize("hasRole('ROLE_ADMIN')")
      public ResponseEntity<User> updateUser(@PathVariable String id,@Valid @RequestBody User user) {
          return new ResponseEntity<>(userService.updateUser(id, user), HttpStatus.OK);
      }
    @PostMapping("/{idUser}/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> addEventToUser(@PathVariable String idUser, @PathVariable String id) throws UserNotFoundException {
        return new ResponseEntity<>(eventService.relateEventToUser(idUser, id), HttpStatus.OK);

    }

    @DeleteMapping("/{idUser}/{idEvent}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updateUserPresence(@PathVariable String idUser, @PathVariable String idEvent){
        eventService.deleteUserPresence(idUser,idEvent);
    }
    
}
