package academy.mindswap.Mindera_Events.Service;

import academy.mindswap.Mindera_Events.Commands.CreatingUserDto;
import academy.mindswap.Mindera_Events.Repository.UserRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import static reactor.core.publisher.Mono.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @MockBean
    private final UserService userService;
    @MockBean
    private final UserRepository userRepository;

    UserServiceTest(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Nested
    public class createUserTest{
        @Test
        void createUserWithExistingEmailExpectThrowError() {
            //Given
            CreatingUserDto creatingUserDto = new CreatingUserDto();
            creatingUserDto.setEmail("jj@gmail.pt");

            //When
            userService.createUser(creatingUserDto);

            //when(userRepository.findAll().size()).thenReturn(false);
            //Then

        }

    }


    @Test
    void updateUser() {
    }
}