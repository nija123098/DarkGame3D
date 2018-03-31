package um.nija123098.shaders;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import um.nija123098.entity.Camera;
import um.nija123098.entity.Light;
import um.nija123098.toolbox.Maths;

import java.util.List;

/**
 * Created by Jack on 7/15/2016.
 */
public class TerrainShader extends ShaderProgram {
    private static final String VERTEX_FILE = "src\\main\\java\\um\\nija123098\\shaders\\TerrainVertexShader.txt";
    private static final String FRAGMENT_FILE = "src\\main\\java\\um\\nija123098\\shaders\\TerrainFragmentShader.txt";
    private static final int MAX_LIGHTS = 4;
    private int location_transformationMatrix;
    private int location_projectionMatrix;
    private int location_viewMatrix;
    private int location_lightPosition[];
    private int location_lightColour[];
    private int location_attenuation[];
    private int location_shineDamper;
    private int location_reflectivity;
    private int location_SkyColour;
    private int location_backgroundTexture;
    private int location_rTexture;
    private int location_gTexture;
    private int location_bTexture;
    private int location_blendMap;
    public TerrainShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
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
        this.location_SkyColour = super.getUniformLocation("skyColour");
        this.location_backgroundTexture = super.getUniformLocation("backgroundColour");
        this.location_rTexture = super.getUniformLocation("rTexture");
        this.location_gTexture = super.getUniformLocation("gTexture");
        this.location_bTexture = super.getUniformLocation("bTexture");
        this.location_blendMap = super.getUniformLocation("blendMap");
        this.location_lightPosition = new int[MAX_LIGHTS];
        this.location_lightColour = new int[MAX_LIGHTS];
        this.location_attenuation = new int[MAX_LIGHTS];
        for (int i = 0; i < MAX_LIGHTS; i++) {
            this.location_lightPosition[i] = super.getUniformLocation("lightPosition[" + i + "]");
            this.location_lightColour[i] = super.getUniformLocation("lightColour[" + i + "]");
            this.location_attenuation[i] = super.getUniformLocation("attenuation[" + i + "]");
        }
    }
    public void connectTextureUnits(){
        super.loadInt(this.location_backgroundTexture, 0);
        super.loadInt(this.location_rTexture, 1);
        super.loadInt(this.location_gTexture, 2);
        super.loadInt(this.location_bTexture, 3);
        super.loadInt(this.location_blendMap, 4);
    }
    public void loadSkyColour(Vector3f vector){
        super.loadVector(this.location_SkyColour, vector);
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
    public void loadViewMatrix(Camera camera){
        Matrix4f viewMatrix = Maths.createViewMatrix(camera);
        super.loadMatrix(this.location_viewMatrix, viewMatrix);
    }
    public void loadProjectionMatrix(Matrix4f projection){
        super.loadMatrix(this.location_projectionMatrix, projection);
    }

}
