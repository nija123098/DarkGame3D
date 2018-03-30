package um.nija123098.darkgame.base.dispatch;

import um.nija123098.darkgame.base.User;

import java.util.ArrayList;
import java.util.List;

/**
 * A class for which all objects sent from
 * a server to a client will extend
 */
public class Event extends Transfer {
    private transient List<User> destination;
    public void setDestination(User destination){
        this.destination = new ArrayList<User>(1);
        this.destination.add(destination);
    }
    public void setDestination(List<User> destination){
        this.destination = destination;
    }
    public boolean isDestination(User user){
        return this.destination == null || this.destination.contains(user);
    }
}
