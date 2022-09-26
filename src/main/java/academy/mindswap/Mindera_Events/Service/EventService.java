package academy.mindswap.Mindera_Events.Service;

import academy.mindswap.Mindera_Events.Model.Event;

import academy.mindswap.Mindera_Events.Repository.EventRepository;
import academy.mindswap.Mindera_Events.excption.UserNotFoundException;
import org.springframework.http.ResponseEntity;
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

    public List<Event> getByTitle(String title) {

        return  eventRepository.findByTitle(title);
    }
    public List<Event> getByState(String state) {
        return  eventRepository.findByState(state);
    }

    public List<Event> getByDate(String date) {
        return  eventRepository.findByDate(date);
    }

    public List<Event> getByType(String type) {
        return  eventRepository.findByType(type);
    }

    public ResponseEntity<Event> updateEvent(String id, Event eventDetails) {
        Event updateEvent = eventRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("This user doesn't exist with this id: " + id));

        updateEvent.setAttendance(eventDetails.getAttendance());
        updateEvent.setDate(eventDetails.getDate());
        updateEvent.setSlots(eventDetails.getSlots());
        updateEvent.setState(eventDetails.getState());
        updateEvent.setStartingTime(eventDetails.getStartingTime());
        updateEvent.setType(eventDetails.getType());
        updateEvent.setWaitingList(eventDetails.getWaitingList());
        updateEvent.setTitle(eventDetails.getTitle());

        eventRepository.save(updateEvent);
        return ResponseEntity.ok(updateEvent);
    }
}