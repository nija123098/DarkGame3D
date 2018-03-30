package um.nija123098.darkgame.implementation.server.events;

import um.nija123098.darkgame.base.User;
import um.nija123098.darkgame.base.dispatch.Event;

/**
 * Made by nija123098 on 12/25/2016
 */
public class MessageReceivedEvent extends Event {
    private String message;
    private String senderID;
    public MessageReceivedEvent(String message, String senderID) {
        this.message = message;
        this.senderID = senderID;
    }
    public User getAuthor(){
        return this.getBank().getUser(this.senderID);
    }
    public String getMessage() {
        return message;
    }
}
