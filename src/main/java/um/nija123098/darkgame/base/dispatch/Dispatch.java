package um.nija123098.darkgame.base.dispatch;

import um.nija123098.darkgame.base.Service;
import um.nija123098.darkgame.util.Resource;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The object responsible for dispatching
 * events to listeners and receiving events from
 * other services
 */
public class Dispatch {
    /**
     * A list of all Transfers that have not indicated
     * that they should to be processed yet
     */
    private final List<Transfer> scheduled;
    /**
     * A list of all listeners that
     * if an object of the type they are listening to
     * is ready to be processed will be passed that object
     */
    private final Map<Class, List<Listener>> listeners;
    /**
     * The user that this dispatcher should assign to events
     */
    private final Service service;
    /**
     * Initiates a dispatcher for the assigned service
     * @param service the service assigned to requests
     */
    public Dispatch(Service service) {
        this.service = service;
        this.scheduled = new CopyOnWriteArrayList<>();
        this.listeners = new ConcurrentHashMap<Class, List<Listener>>();
        Resource.getThread("Dispatch Thread", () -> {
            List<Transfer> removes = new ArrayList<Transfer>();
            while (true){
                for (int i = 0; i < this.scheduled.size(); i++) {
                    Transfer transfer = this.scheduled.get(i);
                    if (transfer.process()){
                        removes.add(transfer);
                        this.dispatch(transfer);
                    }
                }
                this.scheduled.removeAll(removes);
                removes.clear();
            }
        }).start();
    }
    /**
     * @param transfer the Transfer to be dispatched for listening to all applicable listeners
     */
    private void dispatch(Transfer transfer){
        transfer.setBank(this.service.getBank());
        this.listeners.keySet().stream().filter(transfer::isType).forEach(clazz -> this.listeners.get(clazz).stream().forEach(listener -> listener.sendInternal(transfer)));
    }
    /**
     * @param event the event that will be distributed across the network and listeners as appropriate
     */
    public void distribute(Transfer event){
        this.scheduled.add(event);// a execution attempt at distribution will break login attempts
    }
    /**
     * @param o the object whose methods are registered as Transfer listeners
     */
    public void register(Object o){
        for (Method method : o.getClass().getMethods()) {
            if (method.isAnnotationPresent(Subscriber.class) && method.getParameterCount() == 1 && Transfer.class.isAssignableFrom(method.getParameterTypes()[0])){
                Class<?> key = method.getParameterTypes()[0];
                if (this.listeners.get(key) == null){
                    this.listeners.put(key, new CopyOnWriteArrayList<Listener>());
                }
                this.listeners.get(key).add(new Listener(method, o));
            }
        }
    }
    /**
     * @param o the object whose methods are unregistered as Transfer listeners
     */
    public void unregister(Object o){
        List<Listener> remove = new ArrayList<Listener>();
        for (Class clazz : new ArrayList<Class>(this.listeners.keySet())) {
            List<Listener> list = this.listeners.get(clazz);
            for (int i = 0; i < list.size(); i++) {
                Listener listener = list.get(i);
                if (listener.isObject(o)){
                    remove.add(listener);
                }
            }
            list.remove(remove);
            remove.clear();
        }
    }
    /**
     * A basic listener pair class
     */
    private class Listener {
        /**
         * The listener method
         */
        private Method method;
        /**
         * The listener object to be passed the Internal
         */
        private Object object;
        /**
         * A basic alignment constructor
         * @param method the listener method
         * @param object the listener object
         */
        public Listener(Method method, Object object) {
            this.method = method;
            this.object = object;
        }
        /**
         * Passes the internal to the listener and catches invocation exceptions
         * @param transfer the internal to be passed to the listener
         */
        public void sendInternal(Transfer transfer){
            try{this.method.invoke(this.object, transfer);
            }catch(IllegalAccessException | InvocationTargetException e){e.printStackTrace();}
        }
        /**
         * @param o the object to be checked
         * @return if the checked object is the same as the listener's
         */
        public boolean isObject(Object o){
            return this.object.equals(o);
        }
    }
}
