package academy.mindswap.Mindera_Events.messages;

public final class Message {
    private Message(){

    }

    public static final String USER_ALREADY_EXISTS = "User Already Exist";
    public static final String EVENT_ALREADY_EXISTS = "Event Already Exist";
    public static final String NO_EVENTS_FOUND = "No events found.";
    public static final String NO_USERS_FOUND = "No users found.";
    public static final String EVENT_USER_ALREADY_IN_USE = "This Id already exists in the Event";
    public static final String EVENT_NOT_OPEN = "This Event is closed to registration";
}
