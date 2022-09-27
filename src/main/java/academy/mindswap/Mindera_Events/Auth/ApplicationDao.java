package academy.mindswap.Mindera_Events.Auth;

import java.util.Optional;

public interface ApplicationDao {
    Optional<ApplicationUser> selectApplicationUserByUsername(String username);
}
