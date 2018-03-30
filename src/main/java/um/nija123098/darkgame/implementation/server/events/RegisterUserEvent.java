package um.nija123098.darkgame.implementation.server.events;

import um.nija123098.darkgame.base.User;
import um.nija123098.darkgame.base.dispatch.Event;

/**
 * Made by nija123098 on 12/25/2016
 */
public class RegisterUserEvent extends Event {
    private User user;
    public RegisterUserEvent(User user) {
        this.user = user;
    }
    public User getUser(){
        return this.user;
    }
}
