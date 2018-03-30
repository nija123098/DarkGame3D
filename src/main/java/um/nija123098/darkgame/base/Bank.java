package um.nija123098.darkgame.base;

import um.nija123098.darkgame.base.dispatch.Subscriber;
import um.nija123098.darkgame.implementation.server.events.RegisterUserEvent;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The object containing references to all objects
 * for the purpose of not duplicating objects
 */
public class Bank {
    /**
     * The cashed list of users
     */
    private final List<User> users;
    /**
     * The bank initialization for all lists
     */
    public Bank() {
        this.users = new CopyOnWriteArrayList<User>();
    }
    /**
     * @param id the id of the desired user
     * @return the user that has the id, null if no cashed object is found with the id
     */
    public User getUser(String id){
        for (int i = 0; i < users.size(); i++) {
            if (this.users.get(i).getID().equals(id)){
                return users.get(i);
            }
        }
        return null;
    }
    /**
     * @param event the event that registers an user to the bank
     */
    @Subscriber
    public void register(RegisterUserEvent event){
        this.users.add(event.getUser());
    }
}
