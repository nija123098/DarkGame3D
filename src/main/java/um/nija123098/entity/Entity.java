package um.nija123098.entity;

import org.lwjgl.util.vector.Vector3f;
import um.nija123098.model.TexturedModel;

/**
 * Created by Jack on 7/15/2016.
 */
public class Entity {
    private TexturedModel model;
    private Vector3f position;
    private float rotX, rotY, rotZ;
    private float scale;

    private int textureIndex = 0;

    public Entity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        this.model = model;
        this.position = position;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.scale = scale;
    }
    public Entity(TexturedModel model, int textureIndex, Vector3f position, float rotX, float rotY, float rotZ, float scale){
        this(model, position, rotX, rotY, rotZ, scale);
        this.textureIndex = textureIndex;
    }
    public float getTextureXoff(){
        int column = this.textureIndex % this.model.getModelTexture().getNumberOfRows();
        return (float) column / (float) model.getModelTexture().getNumberOfRows();
    }
    public float getTextureYoff(){
        int row = this.textureIndex / this.model.getModelTexture().getNumberOfRows();
        return (float) row / (float) model.getModelTexture().getNumberOfRows();
    }
    public void increasePosition(float dx, float dy, float dz){
        this.position.x += dx;
        this.position.y += dy;
        this.position.z += dz;
    }
    public void increaseRotation(float dx, float dy, float dz){
        this.rotX += dx;
        this.rotY += dy;
        this.rotZ += dz;
    }
    public TexturedModel getModel() {
        return this.model;
    }
    public void setModel(TexturedModel model) {
        this.model = model;
    }
    public Vector3f getPosition() {
        return this.position;
    }
    public void setPosition(Vector3f position) {
        this.position = position;
    }
    public float getRotX() {
        return this.rotX;
    }
    public void setRotX(float rotX) {
        this.rotX = rotX;
    }
    public float getRotY() {
        return this.rotY;
    }
    public void setRotY(float rotY) {
        this.rotY = rotY;
    }
    public float getRotZ() {
        return this.rotZ;
    }
    public void setRotZ(float rotZ) {
        this.rotZ = rotZ;
    }
    public float getScale() {
        return this.scale;
    }
    public void setScale(float scale) {
        this.scale = scale;
    }
}
