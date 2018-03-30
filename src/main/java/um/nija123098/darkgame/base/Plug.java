package um.nija123098.darkgame.base;

import um.nija123098.darkgame.base.dispatch.Transfer;

/**
 * A class that represents the connection
 * between one service and this service
 */
public abstract class Plug {
    /**
     * The service the plug is a part of
     */
    private Service service;
    /**
     * @return the service the plug is a part of
     */
    public Service getService() {
        return this.service;
    }
    /**
     * @param service the service the plug is a part of
     */
    public void setService(Service service){
        this.service = service;
    }
    /**
     * @param transfer the transfer sent to appropriate connected services
     */
    public abstract void send(Transfer transfer);
}
