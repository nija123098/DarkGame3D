package um.nija123098.darkgame.implementation.server.events;

import um.nija123098.darkgame.base.dispatch.Event;

/**
 * The Event sent when an user has disconnected
 * from the server
 */
public class DisconnectEvent extends Event {
    /**
     * The ID of the user being that disconnected
     */
    private String user;
    /**
     * An standard assignment constructor
     * @param user the id of the disconnected user
     */
    public DisconnectEvent(String user) {
        this.user = user;
    }
    /**
     * @return the id of the user that disconnected
     */
    public String getUser() {
        return this.user;
    }
}
