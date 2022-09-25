package academy.mindswap.Mindera_Events.Service;

import academy.mindswap.Mindera_Events.Model.Event;
import academy.mindswap.Mindera_Events.Model.User;
import academy.mindswap.Mindera_Events.Repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    private EventRepository eventRepository;
    private UserService userService;
    @Autowired
    public EventService(EventRepository eventRepository , UserService userService) {
        this.eventRepository = eventRepository;
        this.userService = userService;
    }

    public Event createEvent(Event event) {return eventRepository.save(event);}

    public List<Event> getEventList() {return eventRepository.findAll();}

    public Event updateEventState(Event event) throws ChangeSetPersister.NotFoundException {
        Optional<Event> updatedEventState = eventRepository.findById(event.getId());
        if(updatedEventState.isEmpty()){throw new ChangeSetPersister.NotFoundException();}
        updatedEventState.get().setState(event.getState());
        return eventRepository.save(updatedEventState.get());
    }

}
