package academy.mindswap.Mindera_Events.Service;


import academy.mindswap.Mindera_Events.Commands.CreatingUserDto;
import academy.mindswap.Mindera_Events.Commands.DisplayUserDto;
import academy.mindswap.Mindera_Events.Commands.UserConverter;
import academy.mindswap.Mindera_Events.Commands.UserDto;
import academy.mindswap.Mindera_Events.Exceptions.UserNotFoundException;
import academy.mindswap.Mindera_Events.Logger.EventAlreadyExistsException;
import academy.mindswap.Mindera_Events.Logger.LogExecutionTime;
import academy.mindswap.Mindera_Events.Logger.NoUserFoundException;
import academy.mindswap.Mindera_Events.Logger.UserAlreadyExistsException;
import academy.mindswap.Mindera_Events.Model.User;
import academy.mindswap.Mindera_Events.Repository.UserRepository;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public CreatingUserDto createUser(CreatingUserDto dto) {
        if(userRepository.existsByEmail(dto.getEmail())){
            throw new UserAlreadyExistsException();
        }
        User user = UserConverter.creatingUserDto(dto);
        userRepository.save(user);
        return dto;
    }
    @LogExecutionTime
    public List<DisplayUserDto> getUserList() {
        return userRepository.findAll().stream()
                .map(UserConverter::displayUserDto)
                .toList();
    }
    public List<DisplayUserDto> getByRole(String officeRole) throws NoUserFoundException {
        if(userRepository.findByOfficeRole(officeRole).stream().toList().isEmpty()){
            throw new NoUserFoundException(); }
        List<DisplayUserDto> dtoList = userRepository.findByOfficeRole(officeRole)
                .stream()
                .map(UserConverter::displayUserDto)
                .toList();
        return dtoList;
    }
    public List<DisplayUserDto> getByDepartment(String department) throws NoUserFoundException {
        if(userRepository.findByDepartment(department).stream().toList().isEmpty()){
            throw new NoUserFoundException();}
        List<DisplayUserDto> dtoList = userRepository.findByDepartment(department)
                .stream()
                .map(UserConverter::displayUserDto)
                .toList();
        return dtoList;
    }

    public UserDto getUserById(String id) throws NoUserFoundException {
        if(userRepository.findById(id).isEmpty()){
            throw new NoUserFoundException();}
        User user = userRepository.findById(id).get();
        return UserConverter.UserToDto(user);

    }





    public ResponseEntity<User> updateUser(String id, User userDetails) {
       User updateUser = userRepository.findById(id)
                .orElseThrow();//() -> new UserNotFoundException("This user doesn't exist with this id: " + id));

        updateUser.setAppRole(userDetails.getAppRole());
        updateUser.setEmail(userDetails.getEmail());
        updateUser.setDepartment(userDetails.getDepartment());
        updateUser.setDateOfBirth(userDetails.getDateOfBirth());
        updateUser.setName(userDetails.getName());
        updateUser.setEvents(userDetails.getEvents());
        updateUser.setOfficeRole(userDetails.getOfficeRole());
        updateUser.setPassword(userDetails.getPassword());



        userRepository.save(updateUser);

        return ResponseEntity.ok(updateUser);


    }

}
