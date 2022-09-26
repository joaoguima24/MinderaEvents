package academy.mindswap.Mindera_Events.Service;

import academy.mindswap.Mindera_Events.Commands.DisplayEventListDto;
import academy.mindswap.Mindera_Events.Commands.EventConverter;
import academy.mindswap.Mindera_Events.Commands.UpdateEventStateDto;
import academy.mindswap.Mindera_Events.Exceptions.EventNotFoundException;
import academy.mindswap.Mindera_Events.Exceptions.UserNotFoundException;
import academy.mindswap.Mindera_Events.Model.Event;

import academy.mindswap.Mindera_Events.Repository.EventRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    private final EventRepository eventRepository;
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event createEvent(Event event) {
        return eventRepository.insert(event);
    }
    public List<DisplayEventListDto> getEventList() {
        return eventRepository.findAll().stream()
                .map(EventConverter::getEventToDto)
                .toList();
    }

    public Event getByTitle(String title) {
        return  eventRepository.findByTitle(title);
    }
    public Event getByState(String state) {
        return  eventRepository.findByState(state);
    }

    public Event getByDate(String date) {
        return  eventRepository.findByDate(date);
    }

    public Event getByType(String type) {
        return  eventRepository.findByType(type);
    }

    public ResponseEntity<UpdateEventStateDto> updateEventState(String id, UpdateEventStateDto dto) throws UserNotFoundException {
        Event updateEvent = eventRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("This user doesn't exist with this id: " + id));
        updateEvent.setState(dto.getState());
        eventRepository.save(updateEvent);
        return ResponseEntity.ok(dto);
    }
}
