package um.nija123098.darkgame.util;

/**
 * The recource
 */
public class Resource {
    /**
     * The thread group for tracking all threads spawned
     */
    private static final ThreadGroup THREAD_GROUP;
    static {
        THREAD_GROUP = new ThreadGroup("Resource Thread Group");
    }
    /**
     * @param name the name of the thread requested
     * @param runnable the operation the thread will do on runtime
     * @return the non-started thread
     */
    public static Thread getThread(String name, Runnable runnable){
        return new Thread(THREAD_GROUP, runnable, name);
    }
    /**
     * @return the system time in millis
     */
    public static long getTime(){
        return System.currentTimeMillis();
    }

    /**
     * @param type the type of resource file being looked for
     * @param name the name of the resource file being looked for
     * @return the file's path, regardless if it actually exists
     */
    public static String getPath(ResourceType type, String name){
        return type.prefixPath + "\\" + name + "." + type.type;
    }
    /**
     * The source path of all resource file container directories
     */
    private static String BASE = "src\\main\\resources\\";
    /**
     * An enum representing types of resource files
     */
    public enum ResourceType{
        TEXTURE("png", BASE + "textures"), SHADER("glsl", BASE + "shaders"), MODEL("obj", BASE + "models"), ANIMATED("dae", "animated"),;
        private String type, prefixPath;
        ResourceType(String type, String prefixPath) {
            this.type = type;
            this.prefixPath = prefixPath;
        }
    }
}
