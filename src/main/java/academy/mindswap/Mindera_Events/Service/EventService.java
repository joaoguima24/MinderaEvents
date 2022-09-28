package academy.mindswap.Mindera_Events.Service;

import academy.mindswap.Mindera_Events.Commands.*;
import academy.mindswap.Mindera_Events.Exceptions.EventNotFoundException;
import academy.mindswap.Mindera_Events.Logger.*;
import academy.mindswap.Mindera_Events.Model.Event;

import academy.mindswap.Mindera_Events.Model.User;
import academy.mindswap.Mindera_Events.Repository.EventRepository;
import academy.mindswap.Mindera_Events.excption.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

import static academy.mindswap.Mindera_Events.messages.Message.*;

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
            log.error(EVENT_ALREADY_EXISTS);
            throw new EventAlreadyExistsException();
        }
        eventRepository.save(EventConverter.updateEventDto(dto));
        return dto;
    }

    public List<DisplayEventListDto> getEventList() {
        return eventRepository.findAll().stream()
                .map(EventConverter::getEventToDto)
                .toList();
    }

    public List<DisplayEventListDto> getByTitle(String title) throws NoEventsFoundException {
        if(eventRepository.findByTitle(title).stream().toList().isEmpty()){
            log.error(NO_EVENTS_FOUND);
            throw new NoEventsFoundException();
        }
        return eventRepository.findByTitle(title).stream()
                .map(EventConverter::getEventToDto)
                .toList();
    }

    public List<DisplayEventListDto> getByState(String state) throws NoEventsFoundException{
        if(eventRepository.findByState(state).stream().toList().isEmpty()){
            log.error(NO_EVENTS_FOUND);
            throw new NoEventsFoundException();
        }
        return eventRepository.findByState(state).stream()
                .map(EventConverter::getEventToDto)
                .toList();
    }

    public List<DisplayEventListDto> getByDate(String date) throws NoEventsFoundException {
        if(eventRepository.findByDate(date).stream().toList().isEmpty()){
            log.error(NO_EVENTS_FOUND);
            throw new NoEventsFoundException();
        }
        return eventRepository.findByDate(date).stream()
                .map(EventConverter::getEventToDto)
                .toList();
    }

    public List<DisplayEventListDto> getByType(String type) throws NoEventsFoundException {
        if(eventRepository.findByType(type).stream().toList().isEmpty()){
            log.error(NO_EVENTS_FOUND);
            throw new NoEventsFoundException();
        }
        return eventRepository.findByType(type).stream()
                .map(EventConverter::getEventToDto)
                .toList();
    }

    public EventDto updateEvent(EventDto dto) throws NoEventsFoundException {
        if(eventRepository.findById(dto.getId()).isEmpty()){
            log.error(NO_EVENTS_FOUND);
            throw new NoEventsFoundException();
        }
        eventRepository.save(EventConverter.updateEventDto(dto));
        return dto;
    }


    public String relateEventToUser(String userId, String id ) throws academy.mindswap.Mindera_Events.Exceptions.UserNotFoundException {
        Event event = getEventByEventId(id);
        User user= getUserByUserId(userId);
        attendanceCheckExist(event);
        userEventsCheckExist(user);
        waitingListCheckExist(event);

        if (user.getEvents().stream()
                .anyMatch(eventInUse -> eventInUse.equals(event.getId())) ||
                event.getWaitingList().stream()
                        .anyMatch(theUser->theUser.equals(user))){
            return user.getName() + " you are already in the event list";
        }

        if (!event.getState().equalsIgnoreCase("Open")){
            return "This event is accepting users";
        }

        if ((event.getAttendance().size() + 1)<= event.getSlots()){
            event.getAttendance().add(user);
            eventRepository.save(event);
            user.getEvents().add(id);
            userService.updateUser(userId,user);
            return user.getName() + " welcome to our event";
        }
        event.getWaitingList().add(user);
        eventRepository.save(event);
        return user.getName() + " you are now in the waiting list";

}

    private void waitingListCheckExist(Event event) {
        if (event.getWaitingList()==null){
            event.setWaitingList(new ArrayList<>());
            eventRepository.save(event);
        }
    }

    private User getUserByUserId(String userId) {
        return UserConverter.DtoToUser(userService.getUserById(userId));
    }

    @NotNull
    private Event getEventByEventId(String id) {
        return eventRepository.findById(id).orElseThrow();
    }

    private void userEventsCheckExist(User user) {
        if (user.getEvents()== null){
            user.setEvents(new ArrayList<>());
            userService.updateUser(user.getId(), user);
        }
    }

    private void attendanceCheckExist(Event event) {
        if (event.getAttendance() == null){
            event.setAttendance(new ArrayList<>());
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