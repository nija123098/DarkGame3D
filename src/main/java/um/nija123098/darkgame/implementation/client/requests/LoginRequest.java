package um.nija123098.darkgame.implementation.client.requests;

import um.nija123098.darkgame.base.dispatch.Request;

/**
 * The event sent for an attempt at verification
 */
public class LoginRequest extends Request {
    private String name;
    public LoginRequest(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
