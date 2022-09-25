package academy.mindswap.Mindera_Events.Model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class User {
    @Id
    private String id;
    //@Indexed(unique = true)
    private String email;
    private String events;
    private String password;
    private String officeRole;
    private String appRole;
    private String dateOfBirth;
    private String department;
    public User(){

    }
}
