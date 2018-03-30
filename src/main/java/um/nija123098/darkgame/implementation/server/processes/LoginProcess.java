package um.nija123098.darkgame.implementation.server.processes;

import um.nija123098.darkgame.base.Process;
import um.nija123098.darkgame.base.User;
import um.nija123098.darkgame.base.dispatch.Subscriber;
import um.nija123098.darkgame.implementation.client.requests.LoginRequest;
import um.nija123098.darkgame.implementation.server.events.LoginEvent;

/**
 * Made by nija123098 on 12/25/2016
 */
public class LoginProcess extends Process {
    @Subscriber
    public void handle(LoginRequest request){
        this.getService().getDispatch().distribute(new LoginEvent(request, new User(request.getName())));
    }
}
