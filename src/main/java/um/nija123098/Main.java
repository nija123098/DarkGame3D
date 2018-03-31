package um.nija123098;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import um.nija123098.entity.Camera;
import um.nija123098.entity.Entity;
import um.nija123098.entity.Light;
import um.nija123098.entity.Player;
import um.nija123098.guis.GuiRenderer;
import um.nija123098.guis.GuiTexture;
import um.nija123098.model.RawModel;
import um.nija123098.model.TexturedModel;
import um.nija123098.render.DisplayManager;
import um.nija123098.render.Loader;
import um.nija123098.render.MasterRenderer;
import um.nija123098.render.OBJLoader;
import um.nija123098.terrain.Terrain;
import um.nija123098.textures.ModelTexture;
import um.nija123098.textures.TerrainTexture;
import um.nija123098.textures.TerrainTexturePack;
import um.nija123098.toolbox.MousePicker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Made by Dev on 7/14/2016
 */
public class Main {
    public static void main(String[] args) {

        DisplayManager.makeDisplay();
        Loader loader = new Loader();

        /*ModelData data = OBJFileLoader.loadOBJ("stall");
        RawModel flowerModle = loader.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(), data.getIndices());
        ModelTexture flowerTexture = new ModelTexture(loader.loadTexture("stallTexture"));
        TexturedModel flowerTexturedModel = new TexturedModel(flowerModle, flowerTexture);
        flowerTexture.setHasTransparency(true);
        flowerTexture.setUseFakeLighting(true);*/

        RawModel model = OBJLoader.loadObjModel("dragon", loader);
        ModelTexture texture = new ModelTexture(loader.loadTexture("stallTexture"));
        texture.setSineDamper(10);
        texture.setReflectivity(.1f);
        TexturedModel texturedModel = new TexturedModel(model, texture);
        Entity entity = new Entity(texturedModel, new Vector3f(0,0,0),0,0,0,1);//*/

        RawModel model2 = OBJLoader.loadObjModel("tree", loader);

        TexturedModel staticModel = new TexturedModel(model2,new ModelTexture(loader.loadTexture("tree")));

        List<Entity> entities = new ArrayList<Entity>();
        Random random = new Random();

        List<Light> lights = new ArrayList<>();
        Light light = new Light(new Vector3f(20000,20000,2000), new Vector3f(.1f,.1f,.1f));
        lights.add(light);
        Light lo = new Light(new Vector3f(185, 10, -293), new Vector3f(3, 0, 0), new Vector3f(1f,.01f,.002f));
        lights.add(lo);
        Light lt = new Light(new Vector3f(370, 17, -300), new Vector3f(0, 0, 3), new Vector3f(1f,.01f,.002f));
        lights.add(lt);
        Light lr = new Light(new Vector3f(293, 7, -305), new Vector3f(0, 3, 0), new Vector3f(1f,.01f,.002f));
        lights.add(lr);

        TerrainTexture backgroundTexture = new TerrainTexture((loader.loadTexture("grassy")));
        TerrainTexture r = new TerrainTexture(loader.loadTexture("dirt"));
        TerrainTexture g = new TerrainTexture(loader.loadTexture("pinkFlowers"));
        TerrainTexture b = new TerrainTexture(loader.loadTexture("path"));
        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));
        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, r, g, b, blendMap);

        Terrain terrain = new Terrain(-400,-600, loader, texturePack, "heightMap");

        RawModel rmf = OBJLoader.loadObjModel("fern", loader);
        ModelTexture mtf = new ModelTexture(loader.loadTexture("fernAt"));
        TexturedModel tmf = new TexturedModel(rmf, mtf);
        mtf.setNumberOfRows(2);
        mtf.setHasTransparency(true);
        mtf.setUseFakeLighting(true);
        for(int i=0;i<100;i++){
            float x;
            float z;
            float y;
            {
                x = random.nextFloat()*800 - 400;
                z = random.nextFloat() * -600;
                y = terrain.getHeightOfTerrain(x, z);
                entities.add(new Entity(staticModel, new Vector3f(x,y,z),0,0,0,10));
            }
            {
                x = random.nextFloat()*800 - 400;
                z = random.nextFloat() * -600;
                y = terrain.getHeightOfTerrain(x, z);
                entities.add(new Entity(tmf, random.nextInt(4), new Vector3f(x, y, z),0,0,0,1));
            }

            //entities.add(new Entity(flowerTexturedModel, new Vector3f(random.nextFloat()*800 - 400,0,random.nextFloat() * -600),0,0,0,3));
        }

        List<GuiTexture> guis = new ArrayList<GuiTexture>();
        GuiTexture gui = new GuiTexture(loader.loadTexture("RedPix"), new Vector2f(.5f, .5f), new Vector2f(1f / 1280, 1f /720));
        GuiTexture gui2 = new GuiTexture(loader.loadTexture("YelowPix"), new Vector2f(.51f, .5f), new Vector2f(1f / 1280, 1f /720));
        guis.add(gui);
        guis.add(gui2);
        GuiRenderer guiRenderer = new GuiRenderer(loader);


        RawModel bunnyModel = OBJLoader.loadObjModel("person", loader);
        TexturedModel stanfordBunny = new TexturedModel(bunnyModel, new ModelTexture(loader.loadTexture("playerTexture")));
        Player player = new Player(stanfordBunny, new Vector3f(100, 0, -50),  0, 0, 0, 1);
        Camera camera = new Camera(player);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        MasterRenderer renderer = new MasterRenderer(loader);
        MousePicker picker = new MousePicker(camera, renderer.getProjectionMatrix());
        while(!Display.isCloseRequested()){
            //game logic
            player.move(terrain);
            camera.move();
            picker.update();
            System.out.println(picker.getCurrentRay());
            renderer.processEntity(player);
            for (int i = 0; i < entities.size(); i++) {
                renderer.processEntity(entities.get(i));
            }
            renderer.processEntity(entity);
            renderer.processTerrain(terrain);

            renderer.render(lights, camera);
            guiRenderer.render(guis);
            DisplayManager.updateDisplay();

            System.out.println(Mouse.getX() + "" + Mouse.getY());
        }
        guiRenderer.cleanUp();
        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();

    }

}
