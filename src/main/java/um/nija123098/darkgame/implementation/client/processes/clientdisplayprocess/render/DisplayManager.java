package um.nija123098.darkgame.implementation.client.processes.clientdisplayprocess.render;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.*;
import um.nija123098.darkgame.Reference;

/**
 * Made by Dev on 7/14/2016
 */
public class DisplayManager {
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;

    private static long lastFrameTime;
    private static float delta;

    public static void makeDisplay(){
        ContextAttribs attribs = new ContextAttribs(3, 2).withForwardCompatible(true).withProfileCore(true);
        try {
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
            Display.setTitle(Reference.GAME_NAME);
            Display.create(new PixelFormat().withDepthBits(24).withSamples(4), attribs);
            Display.setInitialBackground(1, 1, 1);
            GL11.glEnable(GL13.GL_MULTISAMPLE);
        }catch (LWJGLException e){
            System.err.println("Could not make OpenGL window!");
            e.printStackTrace();
            System.exit(-11);
        }
        GL11.glViewport(0, 0, WIDTH, HEIGHT);
        lastFrameTime = getCurrentTime();
    }
    public static void updateDisplay(){
        Display.update();
        long currentFrameTime = getCurrentTime();
        delta = (currentFrameTime - lastFrameTime)/1000f;
        lastFrameTime = currentFrameTime;
    }
    public static float getFrameTimeSeconds(){
        return delta;
    }
    public static void closeDisplay(){
        Display.destroy();
    }
    private static long getCurrentTime(){
        return Sys.getTime() * 1000 / Sys.getTimerResolution();
    }
}
