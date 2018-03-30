package um.nija123098.darkgame.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Made by nija123098 on 12/27/2016
 */
public class TimerHelper {
    /**
     * The singleton
     */
    private static final InternalTimer INTERNAL_TIMER = new InternalTimer();

    /**
     * @param millis the delay in millis
     * @param request the runnable to be executed after a delay
     */
    private static void schedule(long millis, Runnable request){
        INTERNAL_TIMER.add(millis, request);
    }
    /**
     * @param millis the delay time
     * @param request the runnable scheduled
     * @return a object usable to cancel the request
     */
    public static Timer time(long millis, Runnable request){
        return new Timer(millis, request);
    }
    /**
     * @param initialDelay the initial delay before repeating starts
     * @param delay the delay between repeats
     * @param runnable the repeated runnable action
     * @return a object usable to cancel the scandal
     */
    public static RepeatedTimer repeat(int initialDelay, long delay, Runnable runnable){
        return new RepeatedTimer(initialDelay, delay, runnable);
    }
    /**
     * @param delay the delay between repeats
     * @param runnable the repeated runnable action
     * @return a object usable to cancel the scandal
     */
    public static RepeatedTimer repeat(long delay, Runnable runnable){
        return new RepeatedTimer(0, delay, runnable);
    }
    /**
     * This method calls the operation immediately
     * in the timer's thread.
     *
     * Calling this method likely means that the developer is using
     * the thread and cheating on some other thread.  The developer
     * should at least buy this thread a steak dinner before calling
     * this method.
     *
     * @param runnable the operation to be executed in the timer's thread
     */
    public static void cheat(Runnable runnable){
        INTERNAL_TIMER.addImitate(runnable);
    }
    private static class InternalTimer implements Runnable {
        private final List<Runnable> imitate;
        private final Map<Long, List<Runnable>> requestMap;
        private InternalTimer() {
            this.imitate = new CopyOnWriteArrayList<>();
            this.requestMap = new ConcurrentHashMap<Long, List<Runnable>>();
            new Thread(this, "Timer Helper Thread").start();
        }
        void addImitate(Runnable runnable){
            synchronized (INTERNAL_TIMER.imitate){
                INTERNAL_TIMER.imitate.add(runnable);
            }
        }
        public void add(long millis, Runnable request){
            millis += System.currentTimeMillis();
            List<Runnable> requests = this.requestMap.get(millis);
            if (requests == null){
                requests = new ArrayList<Runnable>(1);
                this.requestMap.put(millis, requests);
            }
            requests.add(request);
        }
        @Override
        public void run() {
            List<Runnable> requests;
            long previous = System.currentTimeMillis();
            long delta;
            while (true){
                synchronized (this.imitate){
                    this.imitate.forEach(Runnable::run);
                    this.imitate.clear();
                }
                delta = System.currentTimeMillis() - previous;
                if (delta > 0){
                    ++previous;
                    requests = this.requestMap.get(previous);
                    this.requestMap.remove(previous);
                    if (requests != null){
                        requests.forEach(request -> {
                            try{request.run();
                            }catch(Exception e){e.printStackTrace();}
                        });
                    }
                }
            }
        }
    }
    public static class RepeatedTimer {
        private Runnable runnable;
        public RepeatedTimer(long inital, long delay, Runnable runnable) {
            this.runnable = () -> {
                if (this.runnable != null){
                    schedule(delay, this.runnable);
                    runnable.run();
                }
            };
            if (inital == 0){
                INTERNAL_TIMER.addImitate(this.runnable);
            }else{
                time(inital, this.runnable);
            }
        }
        public void cancle(){
            this.runnable = null;
        }
    }
    public static class Timer {
        private boolean cancle;
        public Timer(long delay, Runnable runnable){
            schedule(delay, () -> {
                if (!this.cancle){
                    runnable.run();
                }
            });
        }
        public void cancle(){
            this.cancle = true;
        }
    }
}
