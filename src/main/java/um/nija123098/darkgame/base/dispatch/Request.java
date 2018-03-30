package um.nija123098.darkgame.base.dispatch;

import um.nija123098.darkgame.base.User;

/**
 * A class that all requests to a
 * server from a client extend
 */
public class Request extends Transfer {
    private transient User requester;
    public void setRequester(User requester){
        this.requester = requester;
    }
    public User getRequester(){
        return this.requester;
    }
}
