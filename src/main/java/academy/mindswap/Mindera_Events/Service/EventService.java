package academy.mindswap.Mindera_Events.Service;

import academy.mindswap.Mindera_Events.Model.Event;
import academy.mindswap.Mindera_Events.Repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class EventService {
    private final EventRepository eventRepository;
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event createEvent(Event event) {
        return eventRepository.insert(event);
    }
}
