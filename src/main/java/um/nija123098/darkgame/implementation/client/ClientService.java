package um.nija123098.darkgame.implementation.client;

import um.nija123098.darkgame.base.Service;
import um.nija123098.darkgame.base.dispatch.Subscriber;
import um.nija123098.darkgame.base.dispatch.transfers.ShutdownInternal;
import um.nija123098.darkgame.implementation.client.processes.ClientPlugProcess;
import um.nija123098.darkgame.implementation.client.processes.clientdisplayprocess.ClientDisplayProcess;
import um.nija123098.darkgame.implementation.client.requests.ShutdownRequest;
import um.nija123098.darkgame.implementation.server.events.DisconnectEvent;

/**
 * The client version of the main service
 */
public class ClientService extends Service {
    /**
     * The default constructor that registers all necessary processes (so far)
     */
    public ClientService() {
        this.register(new ClientPlugProcess());
        //this.register(new ClientTextProcess());
        this.register(new ClientDisplayProcess());
    }
    /**
     * @param request the request that triggers a ShutdownInternal process
     */
    @Subscriber
    public void handle(ShutdownRequest request){
        this.getDispatch().distribute(new ShutdownInternal());
    }
    /**
     * @param event the request that triggers a ShutdownInternal process
     */
    @Subscriber
    public void handle(DisconnectEvent event){
        this.getDispatch().distribute(new ShutdownInternal());
    }
}
