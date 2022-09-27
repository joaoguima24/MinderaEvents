package academy.mindswap.Mindera_Events.Repository;

import academy.mindswap.Mindera_Events.Model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRepositoryTest {
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp(){
        userRepository = mock(UserRepository.class);
        User user = new User();
        user.setOfficeRole("Dev");
        User user2 = new User();
        user2.setOfficeRole("Devoo");
        userRepository.insert(user2);
        userRepository.insert(user);
    }

    @Test
    void testIfThereIsAnyUserWithDevOfficeRoleExpectOne() {
        //Given
        /*
        User user = new User();
        user.setOfficeRole("Dev");
        List<User> listOfUsers= new ArrayList<>();
        listOfUsers.add(user);

         */
        int expectedSizeList = 1;
        String officeRoleToSearch = "Dev";


        //When
        //when(userRepository.findByOfficeRole("Devo")).thenReturn(listOfUsers);
        //List<User> list = userRepository.findByOfficeRole(user.getOfficeRole());

        //Then
        when(userRepository.findByOfficeRole(officeRoleToSearch).size()).thenReturn(expectedSizeList);
        //Assertions.assertEquals(expectedSizeList, userRepository.findByOfficeRole(officeRoleToSearch).size());
    }

    @Test
    void testIfThereIsAnyUserWithFrontEndOfficeRoleExpectZero() {
        //Given
        User user = new User();
        user.setOfficeRole("Dev");
        List<User> listOfUsers= new ArrayList<>();
        listOfUsers.add(user);
        int expectedSizeList = 0;
        String officeRoleToSearch = "Front-end";


        //When
        when(userRepository.findByOfficeRole("Dev")).thenReturn(listOfUsers);
        List<User> list = userRepository.findByOfficeRole(user.getOfficeRole());

        //Then
        Assertions.assertEquals(expectedSizeList,userRepository.findByOfficeRole(officeRoleToSearch).size());
    }

    @Test
    void findByDepartment() {
        //Given
        User user = new User();
        user.setDepartment("Operations");
        List<User> listOfUsers= new ArrayList<>();
        listOfUsers.add(user);
        int expectedSizeList = 1;
        String departmentToSearch = "Operations";


        //When
        when(userRepository.findByOfficeRole("Dev")).thenReturn(listOfUsers);
        List<User> list = userRepository.findByOfficeRole(user.getOfficeRole());

        //Then
        Assertions.assertEquals(expectedSizeList,userRepository.findByOfficeRole(departmentToSearch).size());
    }
}