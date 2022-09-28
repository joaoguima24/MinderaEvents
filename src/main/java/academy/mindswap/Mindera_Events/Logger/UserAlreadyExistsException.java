package academy.mindswap.Mindera_Events.Logger;

import academy.mindswap.Mindera_Events.messages.Message;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException() {
        super(Message.USER_ALREADY_EXISTS);
    }
}