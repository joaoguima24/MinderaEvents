package academy.mindswap.Mindera_Events.Service;

import academy.mindswap.Mindera_Events.Model.Event;

import academy.mindswap.Mindera_Events.Model.User;
import academy.mindswap.Mindera_Events.Repository.EventRepository;
import academy.mindswap.Mindera_Events.excption.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final UserService userService;
    public EventService(EventRepository eventRepository, UserService userService) {
        this.eventRepository = eventRepository;
        this.userService = userService;
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

        return eventRepository.findByTitle(title);
    }

    public List<Event> getByState(String state) {
        return eventRepository.findByState(state);
    }

    public List<Event> getByDate(String date) {
        return eventRepository.findByDate(date);
    }

    public List<Event> getByType(String type) {
        return eventRepository.findByType(type);
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
    public void relateEventToUser(String userId, String id ) {
        // guarda na attendence ou na wating list, tens adiconar user

        Event event= eventRepository.findById(id).orElseThrow();
        User user= userService.getUserById(userId);
        if ((event.getAttendance().size() + 1)<= event.getSlots()){

        }
        event.getAttendance().add(user);
        eventRepository.save(event);
        user.getEvents().add(event);
        userService.updateUser(userId,user);

    }
}