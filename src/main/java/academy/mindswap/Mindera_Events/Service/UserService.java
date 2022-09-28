package academy.mindswap.Mindera_Events.Service;


import academy.mindswap.Mindera_Events.Commands.CreatingUserDto;
import academy.mindswap.Mindera_Events.Commands.DisplayUserDto;
import academy.mindswap.Mindera_Events.Commands.UserConverter;
import academy.mindswap.Mindera_Events.Commands.UserDto;

import academy.mindswap.Mindera_Events.Logger.*;

import academy.mindswap.Mindera_Events.Logger.LogExecutionTime;
import academy.mindswap.Mindera_Events.Logger.NoUserFoundException;
import academy.mindswap.Mindera_Events.Logger.UserAlreadyExistsException;

import academy.mindswap.Mindera_Events.Model.User;
import academy.mindswap.Mindera_Events.Repository.UserRepository;


import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.mail.MessagingException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static academy.mindswap.Mindera_Events.messages.Message.NO_USERS_FOUND;
import static academy.mindswap.Mindera_Events.messages.Message.USER_ALREADY_EXISTS;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    private final EmailSenderService emailSenderService;
    public UserService(UserRepository userRepository, EmailSenderService emailSenderService) {
        this.userRepository = userRepository;
        this.emailSenderService = emailSenderService;

    }

    public CreatingUserDto createUser(CreatingUserDto dto) {
        if(userRepository.existsByEmail(dto.getEmail())){
           log.error(USER_ALREADY_EXISTS);
            throw new UserAlreadyExistsException();
        }
        User user = UserConverter.creatingUserDto(dto);
        userRepository.save(user);

        try {
            emailSenderService.sendSimpleEmail(user.getEmail(),"Welcome to our app", qrCode(user.getId()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }


        return dto;
    }
    public byte[] qrCode(String userID) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://api.qrserver.com/v1/create-qr-code/?data="+userID+"&size=100x100"))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<InputStream> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofInputStream());
        return StreamUtils.copyToByteArray(response.body());

    }
    @LogExecutionTime
    public List<DisplayUserDto> getUserList() {
        return userRepository.findAll().stream()
                .map(UserConverter::displayUserDto)
                .toList();
    }
    public List<DisplayUserDto> getByRole(String officeRole) throws NoUserFoundException {
        if(userRepository.findByOfficeRole(officeRole).stream().toList().isEmpty()){
            log.error(NO_USERS_FOUND);
            throw new NoUserFoundException(); }
        List<DisplayUserDto> dtoList = userRepository.findByOfficeRole(officeRole)
                .stream()
                .map(UserConverter::displayUserDto)
                .toList();
        return dtoList;
    }
    public List<DisplayUserDto> getByDepartment(String department) throws NoUserFoundException {
        if(userRepository.findByDepartment(department).stream().toList().isEmpty()){
            log.error(NO_USERS_FOUND);
            throw new NoUserFoundException();}
        List<DisplayUserDto> dtoList = userRepository.findByDepartment(department)
                .stream()
                .map(UserConverter::displayUserDto)
                .toList();
        return dtoList;
    }

    public UserDto getUserById(String id) throws NoUserFoundException {
        if(userRepository.findById(id).isEmpty()){
            log.error(NO_USERS_FOUND);
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
