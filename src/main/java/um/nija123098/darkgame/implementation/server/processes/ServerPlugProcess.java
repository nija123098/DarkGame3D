package um.nija123098.darkgame.implementation.server.processes;

import um.nija123098.darkgame.Reference;
import um.nija123098.darkgame.base.Plug;
import um.nija123098.darkgame.base.Process;
import um.nija123098.darkgame.base.Service;
import um.nija123098.darkgame.base.User;
import um.nija123098.darkgame.base.dispatch.Event;
import um.nija123098.darkgame.base.dispatch.Request;
import um.nija123098.darkgame.base.dispatch.Transfer;
import um.nija123098.darkgame.implementation.client.requests.LoginRequest;
import um.nija123098.darkgame.util.ObjectSocket;
import um.nija123098.darkgame.util.Resource;
import um.nija123098.darkgame.base.dispatch.Subscriber;
import um.nija123098.darkgame.implementation.server.events.LoginEvent;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The plug process responsible for the server
 * side of communication.  It is responsible for
 * appropreatly receiving requests and appropriately
 * sending events to servers.
 */
public class ServerPlugProcess extends Process {
    /**
     * The ServerSocket that spawns
     */
    private ServerSocket serverSocket;
    /**
     * The list of server plugs
     */
    private List<ServerPlug> plugs;
    /**
     * A constructor establishing the server socket
     * and thr thread that checks for sockets
     */
    public ServerPlugProcess() {
        this.plugs = new CopyOnWriteArrayList<>();
        try {this.serverSocket = new ServerSocket(Reference.PORT);
        } catch (IOException e) {e.printStackTrace();}
        Resource.getThread("Server Socket Checker", () -> {
            while (true){
                Socket socket = null;
                try {socket = this.serverSocket.accept();
                } catch (IOException e) {e.printStackTrace();}
                if (socket != null){
                    this.plugs.add(new ServerPlug(socket, this.getService()));
                }
            }
        }).start();
    }
    /**
     * @param event the event to be sent to all appropriate users.
     *              Appropriate is designated by the isDestination return value.
     */
    @Subscriber
    public void handle(Event event){
        for (int i = 0; i < this.plugs.size(); i++) {
            if (event.isDestination(this.plugs.get(i).user)){
                this.plugs.get(i).send(event);
            }
        }
    }
    /**
     * @param event the internal for setting the authentication for a plug
     */
    @Subscriber
    public void handle(LoginEvent event){
        for (int i = 0; i < this.plugs.size(); i++) {
            ServerPlug plug = this.plugs.get(i);
            if (plug.loginRequest.equals(event.getLoginRequest())){
                plug.user = event.getUser();
                plug.loginRequest = null;
                plug.send(event);
            }
        }
    }
    /**
     * The implementation for individual plugs
     * for each client communication socket
     */
    public class ServerPlug extends Plug {
        private LoginRequest loginRequest;
        private User user;
        private ObjectSocket socket;
        public ServerPlug(Socket socket, Service service) {
            this.setService(service);
            this.socket = new ObjectSocket(socket, true){
                @Override
                public void recived(){
                    try {
                        Request transfer = (Request) this.next();
                        if (transfer instanceof LoginRequest){
                            loginRequest = (LoginRequest) transfer;
                        }else{
                            transfer.setRequester(user);
                        }
                        service.getDispatch().distribute(transfer);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            };
        }
        @Override
        public void send(Transfer transfer) {
            this.socket.send(transfer);
        }
    }
}
