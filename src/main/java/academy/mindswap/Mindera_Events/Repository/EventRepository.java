package academy.mindswap.Mindera_Events.Repository;

import academy.mindswap.Mindera_Events.Model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends MongoRepository<Event, String> {
    List<Event> findByTitle(String title);

    List<Event> findByState(String state);

    List<Event> findByDate(String date);

    List<Event> findByType(String type);
}
