package um.nija123098.darkgame.base;

import um.nija123098.darkgame.base.dispatch.transfers.SetupInternal;
import um.nija123098.darkgame.base.dispatch.transfers.StartupInternal;
import um.nija123098.darkgame.base.dispatch.Dispatch;
import um.nija123098.darkgame.base.dispatch.transfers.TickInternal;
import um.nija123098.darkgame.util.Resource;
import um.nija123098.darkgame.util.TimerHelper;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReference;

/**
 * The object that represents the program
 * that a server or client launches
 */
public class Service {
    /**
     * True if the server is running, false if otherwise
     */
    private boolean running;
    /**
     * If the Service is a client the user is the
     * user that the server has assigned the
     * client with the received credentials.
     * If the user is a server then the user is null
     * except if the service is a client and has not
     * been assigned a user by a server
     */
    private User user;
    /**
     * The dispatcher for the service
     */
    private Dispatch dispatch;
    /**
     * The bank of objects that have been registered
     * with this service instance
     */
    private Bank bank;
    /**
     * The list of processes of the service
     */
    private List<Process> processes;
    /**
     * A standard assignment for the service
     * that initializes
     */
    public Service(){
        this.running = true;
        this.dispatch = new Dispatch(this);
        this.bank = new Bank();
        this.processes = new CopyOnWriteArrayList<Process>();
        this.getDispatch().register(this);
        this.getDispatch().register(this.bank);
    }
    /**
     * The method to call to start the service
     */
    public void start(){
        this.getDispatch().distribute(new SetupInternal());
        this.getDispatch().distribute(new StartupInternal());
        final AtomicReference<Long> last = new AtomicReference<Long>(Resource.getTime());
        TimerHelper.repeat(this.getSettings().fps(), () -> {
            this.getDispatch().distribute(new TickInternal(Resource.getTime() - last.get()));
            last.set(Resource.getTime());
        });
    }
    /**
     * @param process the process to be registered as a process.
     *                The process will be ticked after all already registered processes have been ticked.
     */
    public void register(Process process){
        process.setService(this);
        this.dispatch.register(process);
        this.processes.add(process);
    }
    /**
     * @param process the process to be unregistered as a process.
     *                It will no longer be ticked as a process.
     */
    public void unregister(Process process){
        this.dispatch.unregister(process);
        this.processes.remove(process);
    }
    public <E extends Process> E getProcess(Class<E> clazz){
        for (Process process : this.processes){
            if (clazz.isInstance(process)){
                return (E) process;
            }
        }
        return null;
    }
    /**
     * @param user sets the user the service has been assigned
     */
    public void setUser(User user){
        this.user = user;
    }
    /**
     * @return the user the service has been assigned
     */
    public User getUser() {
        return this.user;
    }
    /**
     * @return the service's dispatch
     */
    public Dispatch getDispatch(){
        return this.dispatch;
    }
    /**
     * @return this service's bank
     */
    public Bank getBank(){
        return this.bank;
    }

    public Settings getSettings(){
        return new Settings();
    }
}
