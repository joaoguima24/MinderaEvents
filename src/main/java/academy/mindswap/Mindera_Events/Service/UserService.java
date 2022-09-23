package academy.mindswap.Mindera_Events.Service;

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
    public List<User> getUserList() {

        List<User> userList = userRepository.findAll();

        return userList;
    }

    public User createUser(User user) {

       return userRepository.insert(user);
    }
}
