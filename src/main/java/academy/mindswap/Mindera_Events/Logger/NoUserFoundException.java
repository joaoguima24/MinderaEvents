package academy.mindswap.Mindera_Events.Logger;

import academy.mindswap.Mindera_Events.messages.Message;

public class NoUserFoundException extends RuntimeException {
    public NoUserFoundException() {
        super(Message.NO_USERS_FOUND);
    }
}
