package academy.mindswap.Mindera_Events.Repository;

import academy.mindswap.Mindera_Events.Model.Event;


import academy.mindswap.Mindera_Events.Model.User;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends MongoRepository<Event, String> {
    Event findByTitle(String title);

    Event findByState(String state);

    Event findByDate(String date);

    Event findByType(String type);
}
