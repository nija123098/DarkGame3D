package um.nija123098.render;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import um.nija123098.entity.Camera;
import um.nija123098.entity.Entity;
import um.nija123098.entity.Light;
import um.nija123098.model.TexturedModel;
import um.nija123098.shaders.StaticShader;
import um.nija123098.skybox.SkyboxRenderer;
import um.nija123098.terrain.Terrain;
import um.nija123098.shaders.TerrainShader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jack on 7/15/2016.
 */
public class MasterRenderer {
    private static final float FOV = 70;
    private static final float NEAR_PLANE = .1f;
    private static final float FAR_PLANE = 1000;
    private static final Vector3f SKY_COLOUR = new Vector3f(0, 1, 1);

    private StaticShader shader = new StaticShader();
    private EntityRenderer renderer;
    private TerrainRenderer terrainRenderer;
    private TerrainShader terrainShader = new TerrainShader();
    private Matrix4f projectionMatrix;
    private Map<TexturedModel, List<Entity>> entites = new HashMap<TexturedModel, List<Entity>>();
    private List<Terrain> terrains = new ArrayList<Terrain>();

    private SkyboxRenderer skyboxRenderer;

    public MasterRenderer(Loader loader){
        enableCulling();
        this.makeProjectionMatrix();
        this.renderer = new EntityRenderer(this.shader, this.projectionMatrix);
        this.terrainRenderer = new TerrainRenderer(this.terrainShader, this.projectionMatrix);
        this.skyboxRenderer = new SkyboxRenderer(loader, this.projectionMatrix);
    }
    public Matrix4f getProjectionMatrix(){
        return this.projectionMatrix;
    }
    public static void enableCulling(){
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
    }
    public static void disableCulling(){
        GL11.glDisable(GL11.GL_CULL_FACE);
    }
    public void render(List<Light> lights, Camera camera){
        this.prepare();
        this.shader.start();
        this.shader.loadSkyColour(SKY_COLOUR.x, SKY_COLOUR.y, SKY_COLOUR.z);
        this.shader.loadLights(lights);
        this.shader.loadViewMatrix(camera);
        this.renderer.render(this.entites);
        this.shader.stop();
        this.terrainShader.start();
        this.terrainShader.loadSkyColour(SKY_COLOUR);
        this.terrainShader.loadLights(lights);
        this.terrainShader.loadViewMatrix(camera);
        this.terrainRenderer.render(terrains);
        this.terrainShader.stop();
        this.skyboxRenderer.render(camera, SKY_COLOUR);
        this.terrains.clear();
        this.entites.clear();
    }
    public void processTerrain(Terrain terrain){
        this.terrains.add(terrain);
    }
    public void processEntity(Entity entity){
        TexturedModel entityModel = entity.getModel();
        List<Entity> batch = entites.get(entityModel);
        if (batch != null){
            batch.add(entity);
        }else{
            List<Entity> newBatch = new ArrayList<Entity>();
            newBatch.add(entity);
            entites.put(entityModel, newBatch);
        }
    }
    public void cleanUp(){
        this.shader.cleanUp();
        this.terrainShader.cleanUp();
        this.skyboxRenderer.cleanUp();
    }
    public void prepare(){
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT| GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(SKY_COLOUR.x, SKY_COLOUR.y, SKY_COLOUR.z, 1);
    }
    private void makeProjectionMatrix(){
        float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustum_length = FAR_PLANE - NEAR_PLANE;

        projectionMatrix = new Matrix4f();
        projectionMatrix.m00 = x_scale;
        projectionMatrix.m11 = y_scale;
        projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
        projectionMatrix.m33 = 0;
    }

}
