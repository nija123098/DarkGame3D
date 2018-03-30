package um.nija123098.darkgame.implementation.client.requests;

import um.nija123098.darkgame.base.dispatch.Request;

/**
 * Made by nija123098 on 12/25/2016
 */
public class MessageSendRequest extends Request {
    private String message;
    public MessageSendRequest(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
