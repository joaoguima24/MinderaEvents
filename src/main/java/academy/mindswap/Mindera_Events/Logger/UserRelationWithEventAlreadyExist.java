package academy.mindswap.Mindera_Events.Logger;

import academy.mindswap.Mindera_Events.messages.Message;

public class UserRelationWithEventAlreadyExist extends RuntimeException{
    public UserRelationWithEventAlreadyExist() {
        super(Message.EVENT_USER_ALREADY_IN_USE);
    }

}
