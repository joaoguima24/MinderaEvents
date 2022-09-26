package academy.mindswap.Mindera_Events.Service;


import academy.mindswap.Mindera_Events.Commands.CreatingUserDto;
import academy.mindswap.Mindera_Events.Commands.DisplayUserDto;
import academy.mindswap.Mindera_Events.Commands.UserConverter;
import academy.mindswap.Mindera_Events.Exceptions.UserNotFoundException;
import academy.mindswap.Mindera_Events.Model.User;
import academy.mindswap.Mindera_Events.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public CreatingUserDto createUser(CreatingUserDto dto) {
        User user = UserConverter.creatingUserDto(dto);
        userRepository.save(user);
        return dto;
    }
    public List<DisplayUserDto> getUserList() {
        return userRepository.findAll().stream()
                .map(UserConverter::getUserToDto)
                .toList();
    }
    public List<User> getByRole(String officeRole) {
        return userRepository.findByOfficeRole(officeRole);
    }
    public User getUserById(String id) throws UserNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("The User with this id doesn't exist. Id: " + id));
    }
    public List<User> getByDepartment(String department) {return  userRepository.findByDepartment(department);}
}
