package academy.mindswap.Mindera_Events.Logger;

import academy.mindswap.Mindera_Events.messages.Message;

public class NoEventsFoundException extends RuntimeException {
    public NoEventsFoundException() {
            super(Message.NO_EVENTS_FOUND);
        }
    }