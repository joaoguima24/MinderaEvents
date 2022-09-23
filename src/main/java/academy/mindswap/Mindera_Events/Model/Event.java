package academy.mindswap.Mindera_Events.Model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document("events")
public class Event {
    @Id
    private String id;
    private String title;

    private String type;

    private String state= "unapproved";

    private Date date;

    private String startingTime;


    public Event() {

    }
}