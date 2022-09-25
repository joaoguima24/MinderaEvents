package academy.mindswap.Mindera_Events.Service;

import academy.mindswap.Mindera_Events.Model.User;
import academy.mindswap.Mindera_Events.Repository.UserRepository;
import academy.mindswap.Mindera_Events.excption.UserNotFoundException;
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
    public User getByRole(String officeRole) {

        return  userRepository.findByOfficeRole(officeRole);
    }
    public User getUserById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("The User with this id doesn't exist. Id: " + id));
    }
}
