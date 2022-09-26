package academy.mindswap.Mindera_Events.Repository;

import academy.mindswap.Mindera_Events.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    List<User> findByOfficeRole(String officeRole);

    List<User> findByDepartment(String department);
}
