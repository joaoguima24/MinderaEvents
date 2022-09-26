package academy.mindswap.Mindera_Events.Service;

import academy.mindswap.Mindera_Events.Model.Event;

import academy.mindswap.Mindera_Events.Repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    private final EventRepository eventRepository;
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event createEvent(Event event) {
        //Event e = new Event();
        return eventRepository.insert(event);
    }
    public List<Event> getEventList() {
        List<Event> eventList = eventRepository.findAll();
        return eventList;
    }

}
