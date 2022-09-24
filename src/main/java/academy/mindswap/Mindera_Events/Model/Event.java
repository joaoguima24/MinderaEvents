package academy.mindswap.Mindera_Events.Model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Document("events")
public class Event {
    @Id
    private String id;
    private String title;
    private String type;
    private String state;
    private Date date;
    private String startingTime;
    private int slots;
    private List<User> attendance;
    private List<User> waitingList;

    public Event(String title, String type, Date date, String startingTime) {
        this.title = title;
        this.type = type;
        this.state = "UNAPPROVED";
        this.date = date;
        this.startingTime = startingTime;
    }
}