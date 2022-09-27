package academy.mindswap.Mindera_Events.Commands;

import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private String id;
    private String name;
    private String email;
    private List<String> events;
    private String officeRole;
    private String appRole;
    private String dateOfBirth;
    private String department;
}
