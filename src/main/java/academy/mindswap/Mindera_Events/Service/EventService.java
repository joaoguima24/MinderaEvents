package academy.mindswap.Mindera_Events.Service;

import academy.mindswap.Mindera_Events.Commands.*;
import academy.mindswap.Mindera_Events.Exceptions.EventNotFoundException;
import academy.mindswap.Mindera_Events.Model.Event;

import academy.mindswap.Mindera_Events.Model.User;
import academy.mindswap.Mindera_Events.Repository.EventRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final UserService userService;
    public EventService(EventRepository eventRepository, UserService userService) {
        this.eventRepository = eventRepository;
        this.userService = userService;
    }

    public EventDto createEvent(EventDto dto) {
        Event event = EventConverter.updateEventDto(dto);
        event.setId("open");
        event.setDate("data actual");
        eventRepository.save(event);
        return dto;
    }

    public List<DisplayEventListDto> getEventList() {
        return eventRepository.findAll().stream()
                .map(EventConverter::getEventToDto)
                .toList();
    }

    public ResponseEntity<List<DisplayEventListDto>> getByTitle(String title) throws EventNotFoundException {
        if(eventRepository.findByTitle(title).stream().toList().isEmpty()){
            throw new EventNotFoundException("No events found."); }
        List<DisplayEventListDto> updateEvent = eventRepository.findByTitle(title).stream()
                .map(EventConverter::getEventToDto)
                .toList();
        return ResponseEntity.ok(updateEvent);
    }
    public ResponseEntity<List<DisplayEventListDto>> getByState(String state) throws EventNotFoundException {
        if(eventRepository.findByState(state).stream().toList().isEmpty()){
            throw new EventNotFoundException("No events found."); }
        List<DisplayEventListDto> updateEvent = eventRepository.findByState(state).stream()
                .map(EventConverter::getEventToDto)
                .toList();
        return ResponseEntity.ok(updateEvent);
    }

    public ResponseEntity<List<DisplayEventListDto>> getByDate(String date) throws EventNotFoundException {
        if(eventRepository.findByDate(date).stream().toList().isEmpty()){
            throw new EventNotFoundException("No events found."); }
        List<DisplayEventListDto> updateEvent = eventRepository.findByDate(date).stream()
                .map(EventConverter::getEventToDto)
                .toList();
        return ResponseEntity.ok(updateEvent);
    }

    public ResponseEntity<List<DisplayEventListDto>> getByType(String type) throws EventNotFoundException {
        if(eventRepository.findByType(type).stream().toList().isEmpty()){
            throw new EventNotFoundException("No events found."); }
        List<DisplayEventListDto> updateEvent = eventRepository.findByType(type).stream()
                .map(EventConverter::getEventToDto)
                .toList();
        return ResponseEntity.ok(updateEvent);
    }

    public ResponseEntity<EventDto> updateEvent(EventDto dto) throws EventNotFoundException {
        eventRepository.findById(dto.getId()).orElseThrow(() -> new EventNotFoundException("This event doesn't exist with this id: " + dto.getId()));
        Event updateEvent = EventConverter.updateEventDto(dto);

        eventRepository.save(updateEvent);
        return ResponseEntity.ok(dto);
    }


    public void relateEventToUser(String userId, String id ) throws academy.mindswap.Mindera_Events.Exceptions.UserNotFoundException {


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
            event.getAttendance().add(user);
            eventRepository.save(event);

            user.getEvents().parallelStream().filter(idEvent -> user.getEvents().equals(user.getEvents()));

            user.getEvents().add(id);
            userService.updateUser(userId,user);

        } else if (event.getWaitingList()== null) {
            List <User> listWaiting=new ArrayList<>();
            event.setWaitingList(listWaiting);
            // alterar id no model
            event.getWaitingList().add(user);
            eventRepository.save(event);
        }



        }




}