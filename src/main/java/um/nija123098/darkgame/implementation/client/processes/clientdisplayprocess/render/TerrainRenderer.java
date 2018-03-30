package um.nija123098.darkgame.implementation.client.processes.clientdisplayprocess.render;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import um.nija123098.darkgame.implementation.client.processes.clientdisplayprocess.model.RawModel;
import um.nija123098.darkgame.implementation.client.processes.clientdisplayprocess.shaders.TerrainShader;
import um.nija123098.darkgame.implementation.client.processes.clientdisplayprocess.terrain.Terrain;
import um.nija123098.darkgame.implementation.client.processes.clientdisplayprocess.textures.TerrainTexturePack;
import um.nija123098.darkgame.util.Maths;

import java.util.List;

/**
 * Created by Jack on 7/15/2016.
 */
public class TerrainRenderer {
    private TerrainShader shader;
    public TerrainRenderer(TerrainShader shader, Matrix4f projectionMatrix){
        this.shader = shader;
        this.shader.start();
        this.shader.loadProjectionMatrix(projectionMatrix);
        this.shader.connectTextureUnits();
        this.shader.stop();
    }
    public void render(List<Terrain> terrains){
        for (Terrain terrain : terrains) {
            this.prepareTerrain(terrain);
            this.loadModelMatrix(terrain);
            GL11.glDrawElements(GL11.GL_TRIANGLES, terrain.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
            this.unbindTexturedModel();
        }
    }
    private void prepareTerrain(Terrain terrain){
        RawModel rModel = terrain.getModel();
        GL30.glBindVertexArray(rModel.getVaoId());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        this.bindTextures(terrain);
        shader.loadShineVariables(1, 0);
    }
    private void bindTextures(Terrain terrain){
        TerrainTexturePack texturePack = terrain.getTexturePack();
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, terrain.getTexturePack().getBackground().getTextureID());
        GL13.glActiveTexture(GL13.GL_TEXTURE1);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, terrain.getTexturePack().getR().getTextureID());
        GL13.glActiveTexture(GL13.GL_TEXTURE2);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, terrain.getTexturePack().getG().getTextureID());
        GL13.glActiveTexture(GL13.GL_TEXTURE3);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, terrain.getTexturePack().getB().getTextureID());
        GL13.glActiveTexture(GL13.GL_TEXTURE4);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, terrain.getTexturePack().getBlendMap().getTextureID());
    }
    private void unbindTexturedModel(){
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }
    private void loadModelMatrix(Terrain terrain){
        Matrix4f transformationMatrix = Maths.createTransformationMatrix(new Vector3f(terrain.getX(), 0, terrain.getZ()), new Vector3f(), new Vector3f(1, 1, 1));
        shader.loadTransformationMatrix(transformationMatrix);
    }
}
