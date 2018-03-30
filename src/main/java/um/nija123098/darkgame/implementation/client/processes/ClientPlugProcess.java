package um.nija123098.darkgame.implementation.client.processes;

import um.nija123098.darkgame.Reference;
import um.nija123098.darkgame.base.Plug;
import um.nija123098.darkgame.base.Process;
import um.nija123098.darkgame.base.Service;
import um.nija123098.darkgame.base.dispatch.Request;
import um.nija123098.darkgame.base.dispatch.Subscriber;
import um.nija123098.darkgame.base.dispatch.Transfer;
import um.nija123098.darkgame.base.dispatch.transfers.SetupInternal;
import um.nija123098.darkgame.base.dispatch.transfers.ShutdownInternal;
import um.nija123098.darkgame.implementation.server.events.DisconnectEvent;
import um.nija123098.darkgame.util.ObjectSocket;
import um.nija123098.darkgame.util.TimerHelper;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;

/**
 * The client process responsible for
 * connecting to the server
 *
 * Currently the plug pretends like it never disconnects
 * and if it does it pretends as though everything is ok
 */
public class ClientPlugProcess extends Process {
    /**
     * The plug object containing the Socket
     */
    private ClientPlug plug;
    /**
     * The default constructor
     */
    public ClientPlugProcess() {
    }
    /**
     * A standard SetupInternal listener to init the plug
     */
    @Subscriber
    public void handle(SetupInternal internal){
        this.plug = new ClientPlug(this.getService());
    }
    /**
     * A listener to send all requests to the server
     * @param request the request sent to the server
     */
    @Subscriber
    public void handle(Request request){
        this.plug.send(request);
    }
    /**
     * The shutdown process that closes the socket
     * @param internal the internal signal
     */
    @Subscriber
    public void handle(ShutdownInternal internal){
        TimerHelper.time(250, () -> this.plug.socket.close());
    }
    @Subscriber
    public void handle(DisconnectEvent event){
        if (event.getUser().equals(this.getService().getUser().getID())){
            this.plug.socket.close();
        }
    }
    /**
     * The Plug implementation for clients
     */
    private class ClientPlug extends Plug {
        private ObjectSocket socket;
        public ClientPlug(Service service) {
            this.setService(service);
            try {this.socket = new ObjectSocket(new Socket(Reference.IP, Reference.PORT), false){
                @Override
                public void recived(){
                    try {service.getDispatch().distribute((Transfer) this.next());
                    }catch (Exception e){e.printStackTrace();}
                }
            };
            } catch (IOException e) {
                if (e instanceof ConnectException){
                    System.out.println("Connection refused!  The server is probably offline.");
                    System.exit(7);
                }
                e.printStackTrace();
            }
        }
        @Override
        public void send(Transfer transfer) {
            this.socket.send(transfer);
        }
    }
}
