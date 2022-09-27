package academy.mindswap.Mindera_Events.Service;

import academy.mindswap.Mindera_Events.Auth.ApplicationDao;
import academy.mindswap.Mindera_Events.Auth.ApplicationUser;
import academy.mindswap.Mindera_Events.Model.User;
import academy.mindswap.Mindera_Events.Repository.UserRepository;
import academy.mindswap.Mindera_Events.excption.UserNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static academy.mindswap.Mindera_Events.Security.ApplicationUserRole.*;

@Service
public class ApplicationDaoService implements ApplicationDao {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ApplicationDaoService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
        User user = getAllUsers()
                .stream()
                .filter(theUser -> username.equals(theUser.getName()))
                .findFirst()
                .orElseThrow(()->new UserNotFoundException("Username Not Found, can't login"));

        return Optional.of(new ApplicationUser(user.getName(),
                passwordEncoder.encode(user.getPassword()),
                ADMIN.getGrantedAuthorities(),
                true,
                true,
                true,
                true));

    }

    private List<User> getAllUsers(){
        return userRepository.findAll();
    }


}
