package um.nija123098.darkgame.implementation.server;

import um.nija123098.darkgame.base.Service;
import um.nija123098.darkgame.implementation.server.processes.LoginProcess;
import um.nija123098.darkgame.implementation.server.processes.ServerTextProcess;
import um.nija123098.darkgame.implementation.server.processes.ServerPlugProcess;

/**
 * Made by nija123098 on 12/25/2016
 */
public class ServerService extends Service {
    public ServerService() {
        this.register(new LoginProcess());
        this.register(new ServerPlugProcess());
        this.register(new ServerTextProcess());
    }
}
