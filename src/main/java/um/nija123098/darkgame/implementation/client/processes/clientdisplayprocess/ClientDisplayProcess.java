package um.nija123098.darkgame.implementation.client.processes.clientdisplayprocess;

import org.lwjgl.opengl.Display;
import um.nija123098.darkgame.base.Process;
import um.nija123098.darkgame.base.dispatch.Subscriber;
import um.nija123098.darkgame.base.dispatch.transfers.SetupInternal;
import um.nija123098.darkgame.base.dispatch.transfers.ShutdownInternal;
import um.nija123098.darkgame.base.dispatch.transfers.TickInternal;
import um.nija123098.darkgame.implementation.client.processes.clientdisplayprocess.guis.GuiRenderer;
import um.nija123098.darkgame.implementation.client.processes.clientdisplayprocess.guis.GuiTexture;
import um.nija123098.darkgame.implementation.client.processes.clientdisplayprocess.render.DisplayManager;
import um.nija123098.darkgame.implementation.client.processes.clientdisplayprocess.render.Loader;
import um.nija123098.darkgame.implementation.client.processes.clientdisplayprocess.render.MasterRenderer;
import um.nija123098.darkgame.implementation.client.requests.ShutdownRequest;
import um.nija123098.darkgame.implementation.noside.entity.Ent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Dev on 1/20/2017.
 */
public class ClientDisplayProcess extends Process{
    private Loader loader = new Loader();
    private List<Ent> entities = new ArrayList<>();
    private List<GuiTexture> guis;
    private GuiRenderer guiRenderer;
    private Cam camera;
    private MasterRenderer renderer;
    @Subscriber
    public synchronized void handle(SetupInternal internal){
        DisplayManager.makeDisplay();

        Random random = new Random();
        guis = new ArrayList<GuiTexture>();
        guiRenderer = new GuiRenderer(loader);
        //camera = new Camera(player);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        renderer = new MasterRenderer(loader);
        //picker = new MousePicker(camera, renderer.getProjectionMatrix());
    }
    @Subscriber
    public void handle(TickInternal internal){
        if (Display.isCloseRequested()){
            this.getService().getDispatch().distribute(new ShutdownRequest());
        }
        this.entities.forEach(ent -> renderer.processEntity(ent.getGraphics()));
        renderer.render(camera);
        guiRenderer.render(guis);
        DisplayManager.updateDisplay();
    }
    @Subscriber
    public void handle(ShutdownInternal internal){
        guiRenderer.cleanUp();
        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }
}
