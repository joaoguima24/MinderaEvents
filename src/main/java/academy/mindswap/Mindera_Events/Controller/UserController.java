package academy.mindswap.Mindera_Events.Controller;

import academy.mindswap.Mindera_Events.Service.EventService;
import academy.mindswap.Mindera_Events.Service.UserService;
import org.springframework.web.bind.annotation.RestController;

@RestController("/user")
public class UserController {

    private final UserService userService;
    private final EventService eventService;

    public UserController(UserService userService, EventService eventService) {
        this.userService = userService;
        this.eventService = eventService;
    }

}
