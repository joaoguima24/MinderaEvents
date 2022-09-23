package academy.mindswap.Mindera_Events.Repository;

import academy.mindswap.Mindera_Events.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends MongoRepository<User, String> {
}
