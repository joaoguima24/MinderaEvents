package academy.mindswap.Mindera_Events.Service;

import academy.mindswap.Mindera_Events.Commands.DisplayEventListDto;
import academy.mindswap.Mindera_Events.Commands.EventConverter;
import academy.mindswap.Mindera_Events.Commands.EventDto;
import academy.mindswap.Mindera_Events.Exceptions.EventNotFoundException;
import academy.mindswap.Mindera_Events.Model.Event;

import academy.mindswap.Mindera_Events.Repository.EventRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {this.eventRepository = eventRepository;}

    public EventDto createEvent(EventDto dto) {
        Event event = EventConverter.updateEventDto(dto);
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
        List<DisplayEventListDto> eventList = eventRepository.findByTitle(title).stream()
                .map(EventConverter::getEventToDto)
                .toList();
        return ResponseEntity.ok(eventList);
    }
    public ResponseEntity<List<DisplayEventListDto>> getByState(String state) throws EventNotFoundException {
        if(eventRepository.findByState(state).stream().toList().isEmpty()){
            throw new EventNotFoundException("No events found."); }
        List<DisplayEventListDto> eventList = eventRepository.findByState(state).stream()
                .map(EventConverter::getEventToDto)
                .toList();
        return ResponseEntity.ok(eventList);
    }

    public ResponseEntity<List<DisplayEventListDto>> getByDate(String date) throws EventNotFoundException {
        if(eventRepository.findByDate(date).stream().toList().isEmpty()){
            throw new EventNotFoundException("No events found."); }
        List<DisplayEventListDto> eventList = eventRepository.findByDate(date).stream()
                .map(EventConverter::getEventToDto)
                .toList();
        return ResponseEntity.ok(eventList);
    }

    public ResponseEntity<List<DisplayEventListDto>> getByType(String type) throws EventNotFoundException {
        if(eventRepository.findByType(type).stream().toList().isEmpty()){
            throw new EventNotFoundException("No events found."); }
        List<DisplayEventListDto> eventList = eventRepository.findByType(type).stream()
                .map(EventConverter::getEventToDto)
                .toList();
        return ResponseEntity.ok(eventList);
    }

    public ResponseEntity<EventDto> updateEvent(EventDto dto) throws EventNotFoundException {
        eventRepository.findById(dto.getId()).orElseThrow(() -> new EventNotFoundException("This event doesn't exist with this id: " + dto.getId()));
        Event updateEvent = EventConverter.updateEventDto(dto);
        eventRepository.save(updateEvent);
        return ResponseEntity.ok(dto);
    }


}

