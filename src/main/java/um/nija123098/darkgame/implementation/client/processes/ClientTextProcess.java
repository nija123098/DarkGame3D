package um.nija123098.darkgame.implementation.client.processes;

import um.nija123098.darkgame.base.Process;
import um.nija123098.darkgame.base.dispatch.Subscriber;
import um.nija123098.darkgame.implementation.client.requests.ShutdownRequest;
import um.nija123098.darkgame.implementation.client.requests.LoginRequest;
import um.nija123098.darkgame.implementation.client.requests.MessageSendRequest;
import um.nija123098.darkgame.implementation.server.events.MessageReceivedEvent;
import um.nija123098.darkgame.util.Resource;

import java.util.Scanner;

/**
 * Made by nija123098 on 12/25/2016
 */
public class ClientTextProcess extends Process {
    private Scanner scanner = new Scanner(System.in);
    public ClientTextProcess() {
        Resource.getThread("Scanner Thread", () -> {
            this.getService().getDispatch().distribute(new LoginRequest(this.scanner.nextLine()));
            while (true){
                String text = this.scanner.nextLine();
                if (text.startsWith(">")){
                    this.getService().getDispatch().distribute(new MessageSendRequest(text));
                }else if (text.startsWith("x")){
                    this.getService().getDispatch().distribute(new ShutdownRequest());
                }
            }
        }).start();
    }
    @Subscriber
    public void handle(MessageReceivedEvent event){
        System.out.println(event.getAuthor().getID() + event.getMessage());
    }
}
