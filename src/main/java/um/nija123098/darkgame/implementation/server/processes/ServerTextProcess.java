package um.nija123098.darkgame.implementation.server.processes;

import um.nija123098.darkgame.base.Process;
import um.nija123098.darkgame.base.dispatch.Subscriber;
import um.nija123098.darkgame.implementation.client.requests.MessageSendRequest;
import um.nija123098.darkgame.implementation.server.events.MessageReceivedEvent;

/**
 * Made by nija123098 on 12/25/2016
 */
public class ServerTextProcess extends Process {
    @Subscriber
    public void handle(MessageSendRequest request){
        this.getService().getDispatch().distribute(new MessageReceivedEvent(request.getMessage(), request.getRequester().getID()));
    }
}
