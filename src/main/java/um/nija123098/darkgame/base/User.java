package um.nija123098.darkgame.base;

import java.io.Serializable;

/**
 * The object that represents a user
 */
public class User implements Serializable {
    /**
     * The unique id of the user that identifies the user
     *
     */
    private String id;
    /**
     * A generic full assignment constructor
     * @param id
     */
    public User(String id) {
        this.id = id;
    }
    /**
     * @return the getter of the unique id
     */
    public String getID() {
        return this.id;
    }
}
