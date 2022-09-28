package academy.mindswap.Mindera_Events.Model;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Data
@RequiredArgsConstructor
@Document("events")
public class Event {
    @Id
    private String id;
    @NotBlank(message = "Title is mandatory")
    private String title;
    @NotBlank(message = "Type is mandatory")
    private String type;
    private String state;
    private String date;
    private String startingTime;
    private int slots;
    private List<User> attendance;
    private List<User> waitingList;

}