package um.nija123098.darkgame;

import um.nija123098.darkgame.implementation.client.ClientService;
import um.nija123098.darkgame.implementation.server.ServerService;
import um.nija123098.darkgame.util.TimerHelper;

public class Main {

    public static void main(String[] args) {
	// write your code here
        TimerHelper.cheat(() -> {
            new ServerService().start();
            new ClientService().start();
        });
    }
}
