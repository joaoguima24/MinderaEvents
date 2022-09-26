package academy.mindswap.Mindera_Events.Commands;

import lombok.Data;

@Data
public class EventDto {
    private String id;
    private String title;
    private String type;
    private String state;
    private String date;
    private String startingTime;
    private int slots;
}
