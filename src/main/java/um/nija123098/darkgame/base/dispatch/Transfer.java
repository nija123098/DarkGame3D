package um.nija123098.darkgame.base.dispatch;

import um.nija123098.darkgame.base.Bank;

import java.io.Serializable;

/**
 * Made by nija123098 on 12/20/2016
 */
public class Transfer implements Serializable{
    /**
     * @return true if the Internal is ready to be listened to, else false
     */
    public boolean process(){
        return true;
    }
    /**
     * @param clazz the class being checked
     * @return true if the class type is the same as this Internal, else false
     */
    public boolean isType(Class clazz){
        return clazz.isInstance(this);
    }
    /**
     * The bank the Internal can use to grab necessary objects
     */
    private transient Bank bank;
    /**
     * Called on dispatch
     * @param bank the bank the internal can use
     */
    protected final void setBank(Bank bank){
        this.bank = bank;
    }
    /**
     * @return the bank for grabbing necessary objects
     */
    protected Bank getBank(){
        return this.bank;
    }
}
