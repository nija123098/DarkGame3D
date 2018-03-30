package um.nija123098.darkgame.implementation.client.processes.clientdisplayprocess.shaders;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import um.nija123098.darkgame.implementation.client.processes.clientdisplayprocess.Cam;
import um.nija123098.darkgame.implementation.client.processes.clientdisplayprocess.entity.Light;

import java.util.List;

/**
 * Created by Jack on 7/15/2016.
 */
public class StaticShader extends ShaderProgram {
    private static final int MAX_LIGHTS = 4;// mirrors shader
    private int location_transformationMatrix;
    private int location_projectionMatrix;
    private int location_viewMatrix;
    private int location_lightPosition[];
    private int location_lightColour[];
    private int location_attenuation[];
    private int location_shineDamper;
    private int location_reflectivity;
    private int location_useFakeLighting;
    private int location_skyColour;
    private int location_numberOfRows;
    private int location_offset;
    public StaticShader() {
        super("VertexShader", "FragmentShader");
    }
    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
        super.bindAttribute(2, "normal");
    }
    @Override
    protected void getAllUniformLocations() {
        this.location_transformationMatrix = super.getUniformLocation("transformationMatrix");
        this.location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        this.location_viewMatrix = super.getUniformLocation("viewMatrix");
        this.location_shineDamper = super.getUniformLocation("shineDamper");
        this.location_reflectivity = super.getUniformLocation("reflectivity");
        this.location_useFakeLighting = super.getUniformLocation("useFakeLighting");
        this.location_skyColour = super.getUniformLocation("skyColour");
        this.location_numberOfRows = super.getUniformLocation("numberOfRows");
        this.location_offset = super.getUniformLocation("offset");
        this.location_lightPosition = new int[MAX_LIGHTS];
        this.location_lightColour = new int[MAX_LIGHTS];
        this.location_attenuation = new int[MAX_LIGHTS];
        for (int i = 0; i < MAX_LIGHTS; i++) {
            this.location_lightPosition[i] = super.getUniformLocation("lightPosition[" + i + "]");
            this.location_lightColour[i] = super.getUniformLocation("lightColour[" + i + "]");
            this.location_attenuation[i] = super.getUniformLocation("attenuation[" + i + "]");
        }
    }
    public void loadNumberOfRows(int numberOfRows){
        super.loadFloat(this.location_numberOfRows, numberOfRows);
    }
    public void loadOffSet(float x, float y){
        super.load2DVector(this.location_offset, new Vector2f(x, y));
    }
    public void loadSkyColour(float r, float g, float b){
        super.loadVector(location_skyColour, new Vector3f(r, g, b));
    }
    public void loadFakeLighting(boolean useFakeLighting){
        super.loadBoolean(this.location_useFakeLighting, useFakeLighting);
    }
    public void loadShineVariables(float damper, float reflectivity){
        super.loadFloat(this.location_shineDamper, damper);
        super.loadFloat(this.location_reflectivity, reflectivity);
    }
    public void loadTransformationMatrix(Matrix4f matrix){
        super.loadMatrix(this.location_transformationMatrix, matrix);
    }
    public void loadLights(List<Light> lights){
        for (int i = 0; i < MAX_LIGHTS; i++) {
            if (i < lights.size()){
                super.loadVector(this.location_lightPosition[i], lights.get(i).getPosition());
                super.loadVector(this.location_lightColour[i], lights.get(i).getColour());
                super.loadVector(this.location_attenuation[i], lights.get(i).getAttenuation());
            }else{
                super.loadVector(this.location_lightPosition[i], new Vector3f());
                super.loadVector(this.location_lightColour[i], new Vector3f());
                super.loadVector(this.location_attenuation[i], new Vector3f(1, 0, 0));
            }
        }
    }
    public void loadViewMatrix(Cam camera){
        Matrix4f viewMatrix = camera.getViewMatrix();
        super.loadMatrix(this.location_viewMatrix, viewMatrix);
    }
    public void loadProjectionMatrix(Matrix4f projection){
        super.loadMatrix(this.location_projectionMatrix, projection);
    }
}
