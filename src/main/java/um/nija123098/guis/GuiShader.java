package um.nija123098.guis;

import org.lwjgl.util.vector.Matrix4f;
import um.nija123098.shaders.ShaderProgram;

public class GuiShader extends ShaderProgram {
	
	private static final String VERTEX_FILE = "src\\main\\java\\um\\nija123098\\guis\\guiVertexShader.txt";
	private static final String FRAGMENT_FILE = "src\\main\\java\\um\\nija123098\\guis\\guiFragmentShader.txt";
	
	private int location_transformationMatrix;

	public GuiShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}
	
	public void loadTransformation(Matrix4f matrix){
		super.loadMatrix(location_transformationMatrix, matrix);
	}

	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}
	
	
	

}
