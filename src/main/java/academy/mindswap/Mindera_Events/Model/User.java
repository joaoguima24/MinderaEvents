package academy.mindswap.Mindera_Events.Model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("users")
public class User {
    @Id
    private String id;

    private String name;

    private String email;
    private String events;
    private String password;
    private String officeRole;
    private String appRole;
    private String dateOfBirth;

    public User(){

    }
}
