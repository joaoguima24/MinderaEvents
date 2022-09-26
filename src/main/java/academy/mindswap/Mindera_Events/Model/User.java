package academy.mindswap.Mindera_Events.Model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document("users")
public class User {
    @Id
    private String id;

    private String name;

    private String email;
    private List<Event> events;
    private String password;
    private String officeRole;
    private String appRole;
    private String dateOfBirth;
    private String department;
    public User(){

    }
}
