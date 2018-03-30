package um.nija123098.darkgame.implementation.client.processes.clientdisplayprocess.animated;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import um.nija123098.darkgame.implementation.client.processes.clientdisplayprocess.shaders.ShaderProgram;

/**
 * Made by nija123098 on 1/21/2017
 */
public class AnimatedShader extends ShaderProgram{
    private static final int MAX_JOINTS = 50;
    private int location_jointTransforms[];
    private int location_projectionViewMatrix;
    //private int location_diffuseMap;
    private int location_lightDirection;
    public AnimatedShader() {
        super("animatedVertexShader", "animatedFragmentShader");
    }
    @Override
    protected void bindAttributes() {
        this.bindAttribute(0, "in_position");
        this.bindAttribute(1, "in_textureCoords");
        this.bindAttribute(2, "in_normal");
        this.bindAttribute(3, "in_jointIndicies");
        this.bindAttribute(4, "in_weights");
    }
    @Override
    protected void getAllUniformLocations() {
        for (int i = 0; i < MAX_JOINTS; i++) {
            this.location_jointTransforms[i] = this.getUniformLocation("jointTransforms[" + i + "]");
        }
        this.location_projectionViewMatrix = this.getUniformLocation("projectionViewMatrix");
        //this.location_diffuseMap = this.getUniformLocation("diffuseMap");
        this.location_lightDirection = this.getUniformLocation("lightDirection");
    }
    public void loadJointTransforms(Matrix4f[] matrix4fs){
        for (int i = 0; i < MAX_JOINTS; i++) {
            this.loadMatrix(this.location_jointTransforms[i], matrix4fs[i]);
        }
    }
    public void loadProjectionMatrix(Matrix4f matrix4f){
        this.loadMatrix(this.location_projectionViewMatrix, matrix4f);
    }
    /*public void loadDiffuseMap(){
        this.loadDiffuseMap();
    }*/
    public void loadLightDirection(Vector3f vector3f){
        this.loadVector(this.location_lightDirection, vector3f);
    }
}
