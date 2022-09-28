package academy.mindswap.Mindera_Events.Service;

import academy.mindswap.Mindera_Events.Commands.*;
import academy.mindswap.Mindera_Events.Logger.*;
import academy.mindswap.Mindera_Events.Model.Event;

import academy.mindswap.Mindera_Events.Model.User;
import academy.mindswap.Mindera_Events.Repository.EventRepository;
import academy.mindswap.Mindera_Events.excption.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
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


    public void relateEventToUser(String userId, String id ) throws academy.mindswap.Mindera_Events.Exceptions.UserNotFoundException {
        // guarda na attendence ou na wating list, tens adiconar user
        Event event = getEventByEventId(id);
        User user= getUserByUserId(userId);
        attendanceCheckExist(event);
        userEventsCheckExist(user);
        waitingListCheckExist(event);

        if (user.getEvents().stream()
                .anyMatch(eventInUse -> eventInUse.equals(event.getId()))){
            throw new UserRelationWithEventAlreadyExist();
        }

        if(event.getWaitingList().stream()
                .anyMatch(theUser->theUser.equals(user))){
            throw new UserRelationWithEventAlreadyExist();
        }

        if (!event.getState().equalsIgnoreCase("Open")){
            throw new EventNotOpen();
        }

        if ((event.getAttendance().size() + 1)<= event.getSlots()){
            event.getAttendance().add(user);
            eventRepository.save(event);
            user.getEvents().add(id);
            userService.updateUser(userId,user);
            return;
        }

        event.getWaitingList().add(user);
        eventRepository.save(event);

}

    private void waitingListCheckExist(Event event) {
        if (event.getWaitingList()==null){
            List<User> list = new ArrayList<>();
            event.setWaitingList(list);
            eventRepository.save(event);
        }
    }

    private User getUserByUserId(String userId) {
        return UserConverter.DtoToUser(userService.getUserById(userId));
    }

    @NotNull
    private Event getEventByEventId(String id) {
        Event event= eventRepository.findById(id).orElseThrow();
        return event;
    }

    private void userEventsCheckExist(User user) {
        if (user.getEvents()== null){
            List<String> idList = new ArrayList<>();
            user.setEvents(idList);
            userService.updateUser(user.getId(), user);
        }
    }

    private void attendanceCheckExist(Event event) {
        if (event.getAttendance() == null){
            List<User> userList = new ArrayList<>();
            event.setAttendance(userList);
            eventRepository.save(event);
        }
    }

    public void deleteUserPresence(String idUser, String idEvent) {
        Event event = getEventByEventId(idEvent);
        User user = getUserByUserId(idUser);
        if (event.getAttendance().stream()
                .anyMatch(theUser->theUser.getId().equals(user.getId()))){
            event.getAttendance().removeIf(user1->user1.getId().equals(user.getId()));
            eventRepository.save(event);
            user.getEvents().remove(idEvent);
            userService.updateUser(idUser,user);
            updateWaitingListToAttendance(event);
            return;
        }
        if (event.getWaitingList().stream().anyMatch(theUser->theUser.equals(user))){
            event.getWaitingList().removeIf(user1->user1.getId().equals(user.getId()));
            eventRepository.save(event);
            return;
        }
        throw new UserNotFoundException("The user is not present in that event.");
    }

    public void updateWaitingListToAttendance(Event event){
        if (!event.getWaitingList().isEmpty()){
            if (event.getAttendance().size()<event.getSlots()){
                User user = event.getWaitingList().stream().findFirst().get();
                event.getWaitingList().remove(user);
                event.getAttendance().add(user);
                eventRepository.save(event);
            }
        }
    }

}