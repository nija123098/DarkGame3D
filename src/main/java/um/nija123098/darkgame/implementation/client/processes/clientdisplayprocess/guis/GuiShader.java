package um.nija123098.darkgame.implementation.client.processes.clientdisplayprocess.guis;

import org.lwjgl.util.vector.Matrix4f;
import um.nija123098.darkgame.implementation.client.processes.clientdisplayprocess.shaders.ShaderProgram;

public class GuiShader extends ShaderProgram {
	
	private int location_transformationMatrix;

	public GuiShader() {
		super("guiVertexShader", "guiFragmentShader");
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
