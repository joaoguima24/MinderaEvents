package academy.mindswap.Mindera_Events.Logger;

import academy.mindswap.Mindera_Events.messages.Message;

public class EventNotOpen extends RuntimeException{

    public EventNotOpen() {
        super(Message.EVENT_NOT_OPEN);
    }

}
