package academy.mindswap.Mindera_Events.Commands;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreatingUserDto {

   // private String id;
    private String name;
    private String email;
    private String password;
    private String officeRole;
    private String appRole;
    private String dateOfBirth;
    private String department;

}
