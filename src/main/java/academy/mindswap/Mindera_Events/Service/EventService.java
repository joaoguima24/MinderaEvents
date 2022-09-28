package academy.mindswap.Mindera_Events.Service;

import academy.mindswap.Mindera_Events.Commands.*;
import academy.mindswap.Mindera_Events.Exceptions.EventNotFoundException;
import academy.mindswap.Mindera_Events.Logger.EventAlreadyExistsException;
import academy.mindswap.Mindera_Events.Logger.NoEventsFoundException;
import academy.mindswap.Mindera_Events.Model.Event;

import academy.mindswap.Mindera_Events.Model.User;
import academy.mindswap.Mindera_Events.Repository.EventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class EventService {
    private final EventRepository eventRepository;
private final UserService userService;
    public EventService(EventRepository eventRepository, UserService userService) {
        this.eventRepository = eventRepository;
        this.userService = userService;
    }

    public EventDto createEvent(EventDto dto) {
        if(eventRepository.existsById(dto.getId())){
            throw new EventAlreadyExistsException();
        }
        Event event = EventConverter.updateEventDto(dto);
        eventRepository.save(event);
        return dto;
    }

    public List<DisplayEventListDto> getEventList() {
        return eventRepository.findAll().stream()
                .map(EventConverter::getEventToDto)
                .toList();
    }

    public ResponseEntity<List<DisplayEventListDto>> getByTitle(String title) throws NoEventsFoundException {
        if(eventRepository.findByTitle(title).stream().toList().isEmpty()){
            throw new NoEventsFoundException(); }
        List<DisplayEventListDto> updateEvent = eventRepository.findByTitle(title).stream()
                .map(EventConverter::getEventToDto)
                .toList();
        return ResponseEntity.ok(updateEvent);
    }
    public ResponseEntity<List<DisplayEventListDto>> getByState(String state) throws NoEventsFoundException{
        if(eventRepository.findByState(state).stream().toList().isEmpty()){
            throw new NoEventsFoundException(); }
        List<DisplayEventListDto> updateEvent = eventRepository.findByState(state).stream()
                .map(EventConverter::getEventToDto)
                .toList();
        return ResponseEntity.ok(updateEvent);
    }

    public ResponseEntity<List<DisplayEventListDto>> getByDate(String date) throws NoEventsFoundException {
        if(eventRepository.findByDate(date).stream().toList().isEmpty()){
            throw new NoEventsFoundException(); }
        List<DisplayEventListDto> updateEvent = eventRepository.findByDate(date).stream()
                .map(EventConverter::getEventToDto)
                .toList();
        return ResponseEntity.ok(updateEvent);
    }

    public ResponseEntity<List<DisplayEventListDto>> getByType(String type) throws NoEventsFoundException {
        if(eventRepository.findByType(type).stream().toList().isEmpty()){
            throw new NoEventsFoundException(); }
        List<DisplayEventListDto> updateEvent = eventRepository.findByType(type).stream()
                .map(EventConverter::getEventToDto)
                .toList();
        return ResponseEntity.ok(updateEvent);
    }

    public ResponseEntity<EventDto> updateEvent(EventDto dto) throws NoEventsFoundException {
        if(eventRepository.findById(dto.getId()).isEmpty()){
            throw new NoEventsFoundException();
        }
            //.orElseThrow(() -> new EventNotFoundException("This event doesn't exist with this id: " + dto.getId()));
        Event updateEvent = EventConverter.updateEventDto(dto);
        eventRepository.save(updateEvent);
        return ResponseEntity.ok(dto);
    }

   /* public ResponseEntity<Event> updateEvent(String id, Event eventDetails) {
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
    }*/
    public void relateEventToUser(String userId, String id ) throws academy.mindswap.Mindera_Events.Exceptions.UserNotFoundException {
        // guarda na attendence ou na wating list, tens adiconar user

        Event event= eventRepository.findById(id).orElseThrow();
        User user= UserConverter.DtoToUser(userService.getUserById(userId));
        if (event.getAttendance() == null){
            List<User> userList = new ArrayList<>();
            event.setAttendance(userList);
            eventRepository.save(event);
        }
        if (user.getEvents()== null){
            List<String> idList = new ArrayList<>();
            user.setEvents(idList);
        }
        if ((event.getAttendance().size() + 1)<= event.getSlots()){

        }

        event.getAttendance().add(user);
        eventRepository.save(event);
        user.getEvents().add(id);
        userService.updateUser(userId,user);
}}