package academy.mindswap.Mindera_Events.Service;

import academy.mindswap.Mindera_Events.Commands.CreatingUserDto;
import academy.mindswap.Mindera_Events.Commands.DisplayUserDto;
import academy.mindswap.Mindera_Events.Commands.UserConverter;
import academy.mindswap.Mindera_Events.Commands.UserDto;
import academy.mindswap.Mindera_Events.Exceptions.UserNotFoundException;
import academy.mindswap.Mindera_Events.Model.User;
import academy.mindswap.Mindera_Events.Repository.UserRepository;
import org.springframework.http.ResponseEntity;

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
                .map(UserConverter::displayUserDto)
                .toList();
    }
    public List<DisplayUserDto> getByRole(String officeRole) throws UserNotFoundException {
        if(userRepository.findByOfficeRole(officeRole).stream().toList().isEmpty()){
            throw new UserNotFoundException("No users found."); }
        List<DisplayUserDto> dtoList = userRepository.findByOfficeRole(officeRole)
                .stream()
                .map(UserConverter::displayUserDto)
                .toList();
        return dtoList;
    }
    public List<DisplayUserDto> getByDepartment(String department) throws UserNotFoundException {
        if(userRepository.findByDepartment(department).stream().toList().isEmpty()){
            throw new UserNotFoundException("No users found.");}
        List<DisplayUserDto> dtoList = userRepository.findByDepartment(department)
                .stream()
                .map(UserConverter::displayUserDto)
                .toList();
        return dtoList;
    }

    public UserDto getUserById(String id) throws UserNotFoundException {
        if(userRepository.findById(id).isEmpty()){
            throw new UserNotFoundException("The User with this id doesn't exist. Id: " + id);}
        User user = userRepository.findById(id).get();
        return UserConverter.UserToDto(user);

    }

    public ResponseEntity<UserDto> updateUser(UserDto dto) throws UserNotFoundException{
        userRepository.findById(dto.getId()).orElseThrow(()-> new UserNotFoundException("This user doesn't exist with this id: " + dto.getId()));
        User updatedUser = UserConverter.dtoToUser(dto);
        userRepository.save(updatedUser);
        return ResponseEntity.ok(dto);
    }

}
