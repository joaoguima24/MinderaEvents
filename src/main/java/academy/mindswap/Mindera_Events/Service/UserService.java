package academy.mindswap.Mindera_Events.Service;


import academy.mindswap.Mindera_Events.Model.User;
import academy.mindswap.Mindera_Events.Repository.UserRepository;
import academy.mindswap.Mindera_Events.excption.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public List<User> getUserList() {

        List<User> userList = userRepository.findAll();

        return userList;
    }

    public User createUser(User user) {

       return userRepository.insert(user);
    }

    public List<User> getByRole(String officeRole) {

        return (List<User>) userRepository.findByOfficeRole(officeRole);
    }
    public User getUserById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("The User with this id doesn't exist. Id: " + id));
    }
    public List<User> getByDepartment(String department) {

        return  userRepository.findByDepartment(department);
    }
    public ResponseEntity<User> updateUser(String id, User userDetails) {
       User updateUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("This user doesn't exist with this id: " + id));
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
