package academy.mindswap.Mindera_Events.Commands;

import academy.mindswap.Mindera_Events.Model.Event;
import lombok.Builder;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;

@Data
@Builder
public class EventConverter {
    @Bean
    public static ModelMapper modelMapper() {
        return new ModelMapper();
    }

    public static DisplayEventListDto getEventToDto(Event event) {
        return modelMapper().map(event, DisplayEventListDto.class);
    }

    public static Event updateEventDto(UpdateEventDto dto){
        return modelMapper().map(dto , Event.class);
    }

}