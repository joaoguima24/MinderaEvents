package academy.mindswap.Mindera_Events.Commands;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
public class DisplayUserDto {

    private String id;
    private String name;
    private String email;
}
