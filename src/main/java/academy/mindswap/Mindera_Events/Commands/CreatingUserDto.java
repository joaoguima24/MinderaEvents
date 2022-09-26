package academy.mindswap.Mindera_Events.Commands;

import lombok.Data;


@Data
public class CreatingUserDto {

    private String id;
    private String name;
    private String email;
    private String events;
    private String password;
    private String officeRole;
    private String dateOfBirth;

    public CreatingUserDto() {}
}
