package um.nija123098.darkgame.implementation.client.processes.clientdisplayprocess.shaders;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import um.nija123098.darkgame.util.Resource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

/**
 * Created by Jack on 7/15/2016.
 */
public abstract class ShaderProgram {
    private int programID;
    private int vertexShaiderID;
    private int fragmentShaiderID;
    private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);

    public ShaderProgram(String vertexFile, String fragmentFile){
        this.vertexShaiderID = loadShader(Resource.getPath(Resource.ResourceType.SHADER, vertexFile), GL20.GL_VERTEX_SHADER);
        this.fragmentShaiderID = loadShader(Resource.getPath(Resource.ResourceType.SHADER, fragmentFile), GL20.GL_FRAGMENT_SHADER);
        this.programID = GL20.glCreateProgram();
        GL20.glAttachShader(this.programID, this.vertexShaiderID);
        GL20.glAttachShader(this.programID, this.fragmentShaiderID);
        this.bindAttributes();
        GL20.glLinkProgram(this.programID);
        GL20.glValidateProgram(this.programID);
        this.getAllUniformLocations();
    }
    protected abstract void getAllUniformLocations();
    protected int getUniformLocation(String uniformName){
        return GL20.glGetUniformLocation(this.programID, uniformName);
    }
    protected void loadFloat(int location, float value){
        GL20.glUniform1f(location, value);
    }
    protected void loadInt(int location, int value){
        GL20.glUniform1i(location, value);
    }
    protected void loadVector(int location, Vector3f vector){
        GL20.glUniform3f(location, vector.x, vector.y, vector.getZ());
    }
    protected void load2DVector(int location, Vector2f vector){
        GL20.glUniform2f(location, vector.x, vector.y);
    }
    protected void loadBoolean(int location, boolean value){
        this.loadFloat(location, value ? 1f : 0f);// THIS VARIES FROM CARL'S CODE - T7 6:12
    }
    protected void loadMatrix(int location, Matrix4f matrix){
        matrix.store(matrixBuffer);
        matrixBuffer.flip();
        GL20.glUniformMatrix4(location, false, matrixBuffer);
    }
    protected void loadMatrixArray(int location, Matrix4f[] matrix4fs){
        for (int i = 0; i < matrix4fs.length; i++) {
            this.loadMatrix(location, matrix4fs[i]);
        }
    }
    public void start(){
        GL20.glUseProgram(this.programID);
    }
    public void stop(){
        GL20.glUseProgram(0);
    }
    public void cleanUp(){
        this.stop();
        GL20.glDetachShader(this.programID, this.vertexShaiderID);
        GL20.glDetachShader(this.programID, this.fragmentShaiderID);
        GL20.glDeleteShader(this.vertexShaiderID);
        GL20.glDeleteShader(this.fragmentShaiderID);
        GL20.glDeleteProgram(this.programID);
    }
    protected void bindAttribute(int attribute, String variableName){
        GL20.glBindAttribLocation(this.programID, attribute, variableName);
    }
    protected abstract void bindAttributes();
    private static int loadShader(String file, int type){
        StringBuilder shaderSource = new StringBuilder();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while((line = reader.readLine())!=null){
                shaderSource.append(line).append("//\n");
            }
            reader.close();
        }catch(IOException e){
            e.printStackTrace();
            System.exit(-1);
        }
        int shaderID = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderID, shaderSource);
        GL20.glCompileShader(shaderID);
        if(GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS )== GL11.GL_FALSE){
            System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
            System.err.println("Could not compile shader!");
            System.exit(-1);
        }
        return shaderID;
    }
}
