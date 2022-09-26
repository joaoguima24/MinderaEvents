package academy.mindswap.Mindera_Events.Commands;

import academy.mindswap.Mindera_Events.Model.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;
import org.jetbrains.annotations.NotNull;

@Data
@NoArgsConstructor
public class CreatingUserDto {

    @NotNull
    private String id;
    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    private String officeRole;
    private String appRole;
    private String dateOfBirth;
    private String department;

}
