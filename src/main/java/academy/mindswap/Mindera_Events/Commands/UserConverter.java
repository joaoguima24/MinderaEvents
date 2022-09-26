package academy.mindswap.Mindera_Events.Commands;

import academy.mindswap.Mindera_Events.Model.User;
import lombok.Builder;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;

@Data
@Builder
public class UserConverter {
    @Bean
    public static ModelMapper modelMapper() {
        return new ModelMapper();
    }

    public static DisplayUserDto displayUserDto (User user){
        return modelMapper().map(user, DisplayUserDto.class);
    }

    public static User creatingUserDto(CreatingUserDto dto){
        return modelMapper().map(dto, User.class);
    }

    public static UserDto UserToDto (User user){
        return modelMapper().map(user, UserDto.class);
    }


    public static User dtoToUser(UserDto dto) {return modelMapper().map(dto, User.class);}
}