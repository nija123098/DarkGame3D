package um.nija123098.darkgame.implementation.server.events;

import um.nija123098.darkgame.base.User;
import um.nija123098.darkgame.implementation.client.requests.LoginRequest;

/**
 * Made by nija123098 on 12/25/2016
 */
public class LoginEvent extends RegisterUserEvent {
    private transient LoginRequest loginRequest;
    public LoginEvent(LoginRequest loginRequest, User user) {
        super(user);
        this.loginRequest = loginRequest;
        this.setDestination(user);
    }
    public LoginRequest getLoginRequest() {
        return this.loginRequest;
    }
}
