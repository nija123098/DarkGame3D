package um.nija123098.darkgame.implementation.client.processes.clientdisplayprocess.render;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import um.nija123098.darkgame.implementation.client.processes.clientdisplayprocess.model.RawModel;
import um.nija123098.darkgame.implementation.client.processes.clientdisplayprocess.model.TexturedModel;
import um.nija123098.darkgame.implementation.client.processes.clientdisplayprocess.shaders.StaticShader;
import um.nija123098.darkgame.implementation.client.processes.clientdisplayprocess.textures.ModelTexture;
import um.nija123098.darkgame.implementation.noside.entity.GraphicsEntity;
import um.nija123098.darkgame.util.Maths;

import java.util.List;
import java.util.Map;

/**
 * Created by Jack on 7/14/2016.
 */
public class EntityRenderer {

    private StaticShader shader;

    public EntityRenderer(StaticShader shader, Matrix4f projectionMatrix){
        this.shader = shader;
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
    }
    public void render(Map<TexturedModel, List<GraphicsEntity>> entities){
        for (TexturedModel model : entities.keySet()) {
            if (model == null){
                continue;
            }
            this.prepareTexturedModel(model);
            List<GraphicsEntity> batch = entities.get(model);
            for (GraphicsEntity entity : batch) {
                this.prepareInstance(entity);
                GL11.glDrawElements(GL11.GL_TRIANGLES, model.getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
            }
            this.unbindTexturedModel();
        }
    }
    private void prepareTexturedModel(TexturedModel model){
        RawModel rModel = model.getRawModel();
        GL30.glBindVertexArray(rModel.getVaoId());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        ModelTexture texture = model.getModelTexture();
        shader.loadNumberOfRows(texture.getNumberOfRows());
        if (texture.isHasTransparency()){
            MasterRenderer.disableCulling();
        }
        this.shader.loadFakeLighting(texture.isUseFakeLighting());
        this.shader.loadShineVariables(texture.getSineDamper(), texture.getReflectivity());
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getModelTexture().getTextureID());

    }
    private void unbindTexturedModel(){
        MasterRenderer.enableCulling();
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }
    private void prepareInstance(GraphicsEntity entity){
        Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(), entity.getRotation(), entity.getScail());
        this.shader.loadTransformationMatrix(transformationMatrix);
        this.shader.loadOffSet(entity.getTextureXoff(), entity.getTextureYoff());
    }
    /*
    public void render(Entity entity, StaticShader shader){
        TexturedModel texturedModel = entity.getModel();
        RawModel model = texturedModel.getRawModel();
        GL30.glBindVertexArray(model.getVaoId());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        Matrix4f transformationMatrix = Maths.makeTransformationMtrix(entity.getPosition(), entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
        shader.loadTransformationMatrix(transformationMatrix);
        ModelTexture texture = texturedModel.getModelTexture();
        shader.loadShineVariables(texture.getSineDamper(), texture.getReflectivity());
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturedModel.getModelTexture().getTextureID());
        GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }*/
}