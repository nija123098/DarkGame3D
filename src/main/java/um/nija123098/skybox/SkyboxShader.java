package um.nija123098.skybox;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import um.nija123098.entity.Camera;
import um.nija123098.render.DisplayManager;
import um.nija123098.shaders.ShaderProgram;
import um.nija123098.toolbox.Maths;

public class SkyboxShader extends ShaderProgram {

	private static final String VERTEX_FILE = "src\\main\\java\\um\\nija123098\\skybox\\skyboxVertexShader.txt";
	private static final String FRAGMENT_FILE = "src\\main\\java\\um\\nija123098\\skybox\\skyboxFragmentShader.txt";

	private static final float ROTATION_SPEED = 1;
	
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_skyColour;
	private int location_cubeMap;
	private int location_cubeMap2;
	private int location_floatFactor;

	private float rotation = 0;
	
	public SkyboxShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}
	
	public void loadProjectionMatrix(Matrix4f matrix){
		super.loadMatrix(location_projectionMatrix, matrix);
	}

	public void loadViewMatrix(Camera camera){
		Matrix4f matrix = Maths.createViewMatrix(camera);
		matrix.m30 = 0;
		matrix.m31 = 0;
		matrix.m32 = 0;
		rotation += ROTATION_SPEED * DisplayManager.getFrameTimeSeconds();
		Matrix4f.rotate((float) Math.toRadians(this.rotation), new Vector3f(0, 1, 0), matrix, matrix);
		super.loadMatrix(location_viewMatrix, matrix);
	}

	public void loadFogColour(Vector3f vector3f){
		super.loadVector(this.location_skyColour, vector3f);
	}

	public void connectTextureUnits(){
		super.loadInt(this.location_cubeMap, 0);
		super.loadInt(this.location_cubeMap2, 1);
	}

	public void loadBlendFactor(float blend){
		super.loadFloat(this.location_floatFactor, blend);
	}
	
	@Override
	protected void getAllUniformLocations() {
		this.location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		this.location_viewMatrix = super.getUniformLocation("viewMatrix");
		this.location_skyColour = super.getUniformLocation("fogColour");
		this.location_floatFactor = super.getUniformLocation("blendFactor");
		this.location_cubeMap = super.getUniformLocation("cubeMap");
		this.location_cubeMap2 = super.getUniformLocation("cubeMap2");
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}

}
