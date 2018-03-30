package um.nija123098.darkgame.implementation.noside.entity;

import org.lwjgl.util.vector.Vector3f;
import um.nija123098.darkgame.implementation.client.processes.clientdisplayprocess.entity.Light;
import um.nija123098.darkgame.implementation.client.processes.clientdisplayprocess.model.TexturedModel;

import java.util.List;

/**
 * A messy wrapper for the graphics engine
 * Made by nija123098 on 1/21/2017
 */
public class GraphicsEntity {
    private TexturedModel model;
    private Ent ent;
    private List<Light> lights;

    private int textureIndex = 0;

    public GraphicsEntity(TexturedModel model, Ent ent) {
        this.model = model;
        this.ent = ent;
    }
    public float getTextureXoff(){
        int column = this.textureIndex % this.model.getModelTexture().getNumberOfRows();
        return (float) column / (float) model.getModelTexture().getNumberOfRows();
    }
    public float getTextureYoff(){
        int row = this.textureIndex / this.model.getModelTexture().getNumberOfRows();
        return (float) row / (float) model.getModelTexture().getNumberOfRows();
    }
    public TexturedModel getModel() {
        return this.model;
    }
    public void setModel(TexturedModel model) {
        this.model = model;
    }
    public Vector3f getPosition() {
        return this.ent.getPhysics().getPosition();
    }
    public Vector3f getRotation() {
        return this.ent.getPhysics().getRotation();
    }
    public Vector3f getScail() {
        return this.ent.getPhysics().getScale();
    }
    public List<Light> getLights(){
        return this.lights;
    }
}
