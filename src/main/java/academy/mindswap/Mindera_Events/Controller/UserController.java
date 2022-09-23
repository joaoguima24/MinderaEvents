package academy.mindswap.Mindera_Events.Controller;

import academy.mindswap.Mindera_Events.Model.Event;
import academy.mindswap.Mindera_Events.Service.EventService;
import academy.mindswap.Mindera_Events.Service.UserService;
import org.springframework.web.bind.annotation.*;

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


}
