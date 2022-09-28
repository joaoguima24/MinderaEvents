package academy.mindswap.Mindera_Events.Logger;

import academy.mindswap.Mindera_Events.messages.Message;

public class EventAlreadyExistsException extends RuntimeException {
    public EventAlreadyExistsException() {
        super(Message.EVENT_ALREADY_EXISTS);
    }
}
