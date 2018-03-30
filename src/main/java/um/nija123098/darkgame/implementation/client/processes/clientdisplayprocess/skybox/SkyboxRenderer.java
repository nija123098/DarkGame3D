package um.nija123098.darkgame.implementation.client.processes.clientdisplayprocess.skybox;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import um.nija123098.darkgame.implementation.client.processes.clientdisplayprocess.Cam;
import um.nija123098.darkgame.implementation.client.processes.clientdisplayprocess.model.RawModel;
import um.nija123098.darkgame.implementation.client.processes.clientdisplayprocess.render.DisplayManager;
import um.nija123098.darkgame.implementation.client.processes.clientdisplayprocess.render.Loader;

/**
 * Created by Jack on 7/17/2016.
 */
public class SkyboxRenderer {
    private static final float SIZE = 500f;

    private static final float[] VERTICES = {
            -SIZE,  SIZE, -SIZE,
            -SIZE, -SIZE, -SIZE,
            SIZE, -SIZE, -SIZE,
            SIZE, -SIZE, -SIZE,
            SIZE,  SIZE, -SIZE,
            -SIZE,  SIZE, -SIZE,

            -SIZE, -SIZE,  SIZE,
            -SIZE, -SIZE, -SIZE,
            -SIZE,  SIZE, -SIZE,
            -SIZE,  SIZE, -SIZE,
            -SIZE,  SIZE,  SIZE,
            -SIZE, -SIZE,  SIZE,

            SIZE, -SIZE, -SIZE,
            SIZE, -SIZE,  SIZE,
            SIZE,  SIZE,  SIZE,
            SIZE,  SIZE,  SIZE,
            SIZE,  SIZE, -SIZE,
            SIZE, -SIZE, -SIZE,

            -SIZE, -SIZE,  SIZE,
            -SIZE,  SIZE,  SIZE,
            SIZE,  SIZE,  SIZE,
            SIZE,  SIZE,  SIZE,
            SIZE, -SIZE,  SIZE,
            -SIZE, -SIZE,  SIZE,

            -SIZE,  SIZE, -SIZE,
            SIZE,  SIZE, -SIZE,
            SIZE,  SIZE,  SIZE,
            SIZE,  SIZE,  SIZE,
            -SIZE,  SIZE,  SIZE,
            -SIZE,  SIZE, -SIZE,

            -SIZE, -SIZE, -SIZE,
            -SIZE, -SIZE,  SIZE,
            SIZE, -SIZE, -SIZE,
            SIZE, -SIZE, -SIZE,
            -SIZE, -SIZE,  SIZE,
            SIZE, -SIZE,  SIZE
    };
    private static String[] TEXTURE_FILES = {"right", "left", "top", "bottom", "back", "front"};
    private static String[] TEXTURE_FILES_NIGHT = {"nightRight", "nightLeft", "nightTop", "nightBottom", "nightBack", "nightFront"};

    private RawModel cube;
    private int texture;
    private int nightTexture;
    private SkyboxShader shader;
    private float time = 0;

    public SkyboxRenderer(Loader loader, Matrix4f projectionMatrix){
        this.cube = loader.loadToVAO(VERTICES, 3);
        this.texture = loader.loadCubeMap(TEXTURE_FILES);
        this.nightTexture = loader.loadCubeMap(TEXTURE_FILES_NIGHT);
        this.shader = new SkyboxShader();
        this.shader.start();
        this.shader.connectTextureUnits();
        this.shader.loadProjectionMatrix(projectionMatrix);
        this.shader.stop();
    }
    public void render(Cam camera, Vector3f fogColour){
        this.shader.start();
        this.shader.loadViewMatrix(camera);
        this.shader.loadFogColour(fogColour);
        GL30.glBindVertexArray(this.cube.getVaoId());
        GL20.glEnableVertexAttribArray(0);
        this.bindTextures();
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, this.cube.getVertexCount());
        GL30.glBindVertexArray(0);
        this.shader.stop();
    }
    public void cleanUp(){
        this.shader.cleanUp();
    }

    private void bindTextures(){
        this.time += DisplayManager.getFrameTimeSeconds() * 1000;
        this.time %= 24000;
        int texture1;
        int texture2;
        float blendFactor;
        if(this.time >= 0 && this.time < 5000){
            texture1 = this.nightTexture;
            texture2 = this.nightTexture;
            blendFactor = (this.time - 0)/(5000 - 0);
        }else if(this.time >= 5000 && this.time < 8000){
            texture1 = this.nightTexture;
            texture2 = this.texture;
            blendFactor = (this.time - 5000)/(8000 - 5000);
        }else if(this.time >= 8000 && this.time < 21000){
            texture1 = this.texture;
            texture2 = this.texture;
            blendFactor = (this.time - 8000)/(21000 - 8000);
        }else{
            texture1 = this.texture;
            texture2 = this.nightTexture;
            blendFactor = (this.time - 21000)/(24000 - 21000);
        }

        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, texture1);
        GL13.glActiveTexture(GL13.GL_TEXTURE1);
        GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, texture2);
        shader.loadBlendFactor(blendFactor);
    }
}
