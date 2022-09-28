package academy.mindswap.Mindera_Events.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@RequiredArgsConstructor
@Document("users")
public class User {
    @Id
    private String id;
    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotBlank(message = "Email is mandatory")
    private String email;
    private List<String> events;
    private String password;
    private String officeRole;
    private String appRole;
    private String dateOfBirth;
    private String department;
}
