package um.nija123098.darkgame.base;

/**
 * The object representing a process
 * such as a graphics system or network system
 */
public class Process {
    /**
     * The service assigned to the process
     */
    private Service service;
    /**
     * @param service the service assigned to the process
     */
    public void setService(Service service){
        this.service = service;
    }
    /**
     * @return the service assigned to the process
     */
    protected Service getService(){
        return this.service;
    }
}
