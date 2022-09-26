package academy.mindswap.Mindera_Events.Commands;

import lombok.Data;

@Data
public class UpdateUserDto {
    private String id;
    private String name;
    private String email;
    private String officeRole;
    private String appRole;
    private String dateOfBirth;
    private String department;
}
