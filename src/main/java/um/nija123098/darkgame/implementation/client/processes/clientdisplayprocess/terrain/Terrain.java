package um.nija123098.darkgame.implementation.client.processes.clientdisplayprocess.terrain;

import javafx.util.Pair;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import um.nija123098.darkgame.implementation.client.processes.clientdisplayprocess.model.RawModel;
import um.nija123098.darkgame.implementation.client.processes.clientdisplayprocess.render.Loader;
import um.nija123098.darkgame.implementation.client.processes.clientdisplayprocess.textures.TerrainTexturePack;
import um.nija123098.darkgame.util.Maths;
import um.nija123098.darkgame.util.Resource;
import um.nija123098.darkgame.util.TerrainLoader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Jack on 7/15/2016.
 */
public class Terrain {
    private static final float SIZE = 800;
    private static final float MAX_HEIGHT = 40;
    private static final float MAX_Pixel_COLOUR = 16777216;// 256^3
    private float x, z;
    private RawModel model;
    private TerrainTexturePack texturePack;
    private String heightMapFileName;
    private float[][] heights;
    public Terrain(float x, float z, Loader loader, TerrainTexturePack texturePack, String heightMapFileName) {
        this.texturePack = texturePack;
        this.x = x;
        this.z = z;
        this.heightMapFileName = heightMapFileName;
        Pair<RawModel, float[][]> pair = new TerrainLoader(loader).load(loader, this.heightMapFileName);
        this.model = pair.getKey();
        this.heights = pair.getValue();
    }
    public float getX() {
        return this.x;
    }
    public void setX(float x) {
        this.x = x;
    }
    public float getZ() {
        return this.z;
    }
    public void setZ(float z) {
        this.z = z;
    }
    public RawModel getModel() {
        return this.model;
    }
    public void setModel(RawModel model) {
        this.model = model;
    }
    public TerrainTexturePack getTexturePack() {
        return this.texturePack;
    }
    public float getHeightOfTerrain(float wX, float wZ){
        float terrainX = wX - this.x;
        float terrainZ = wZ - this.z;
        float gridSquareSize = SIZE / ((float) heights.length - 1);
        int gridX = (int) Math.floor(terrainX / gridSquareSize);
        int gridZ = (int) Math.floor(terrainZ / gridSquareSize);
        if (gridX >= heights.length - 1 || gridZ >= heights.length - 1 || gridX < 0 || gridZ < 0){
            return 0;
        }
        float xCoord = (terrainX % gridSquareSize) / gridSquareSize;
        float zCoord = (terrainZ % gridSquareSize) / gridSquareSize;
        if (xCoord <= (1-zCoord)){
            return Maths.barryCentric(new Vector3f(0, this.heights[gridX][gridZ], 0), new Vector3f(1, this.heights[gridX + 1][gridZ], 0), new Vector3f(0, this.heights[gridX][gridZ + 1], 1), new Vector2f(xCoord, zCoord));
        }else{
            return Maths.barryCentric(new Vector3f(1, this.heights[gridX + 1][gridZ], 0), new Vector3f(1, this.heights[gridX + 1][gridZ + 1], 1), new Vector3f(0, this.heights[gridX][gridZ + 1], 1), new Vector2f(xCoord, zCoord));
        }
    }
    private RawModel generateTerrain(Loader loader, String heightMap){
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(Resource.getPath(Resource.ResourceType.TEXTURE, heightMap)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        int vertexCount = image.getHeight();
        this.heights = new float[vertexCount][vertexCount];
        int count = vertexCount * vertexCount;
        float[] vertices = new float[count * 3];
        float[] normals = new float[count * 3];
        float[] textureCoords = new float[count*2];
        int[] indices = new int[6*(vertexCount-1)*(vertexCount-1)];
        int vertexPointer = 0;
        for(int i=0;i<vertexCount;i++){
            for(int j=0;j<vertexCount;j++){
                vertices[vertexPointer*3] = (float)j/((float)vertexCount - 1) * SIZE;
                float height = this.getHeight(j, i, image);
                this.heights[j][i] = height;
                vertices[vertexPointer*3+1] = height;
                vertices[vertexPointer*3+2] = (float)i/((float)vertexCount - 1) * SIZE;
                Vector3f normal = calcNormal(j, i, image);
                normals[vertexPointer*3] = normal.x;
                normals[vertexPointer*3+1] = normal.y;
                normals[vertexPointer*3+2] = normal.z;
                textureCoords[vertexPointer*2] = (float)j/((float)vertexCount - 1);
                textureCoords[vertexPointer*2+1] = (float)i/((float)vertexCount - 1);
                vertexPointer++;
            }
        }
        int pointer = 0;
        for(int gz=0;gz<vertexCount-1;gz++){
            for(int gx=0;gx<vertexCount-1;gx++){
                int topLeft = (gz*vertexCount)+gx;
                int topRight = topLeft + 1;
                int bottomLeft = ((gz+1)*vertexCount)+gx;
                int bottomRight = bottomLeft + 1;
                indices[pointer++] = topLeft;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = topRight;
                indices[pointer++] = topRight;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = bottomRight;
            }
        }
        return loader.loadToVAO(vertices, textureCoords, normals, indices);
    }
    private Vector3f calcNormal(int x, int z, BufferedImage image){
        float heightL = getHeight(x-1, z, image);
        float heightR = getHeight(x+1, z, image);
        float heightD = getHeight(x, z-1, image);
        float heightU = getHeight(x, z+1, image);
        Vector3f normal = new Vector3f(heightL - heightR, 2f, heightD - heightU);
        normal.normalise();
        return normal;
    }
    private float getHeight(int x, int z, BufferedImage image){
        if (x < 0 || x>= image.getHeight() || z<0 || z>= image.getHeight()){
            return 0;
        }
        float height = image.getRGB(x, z);
        height += MAX_Pixel_COLOUR / 2f;
        height /= MAX_Pixel_COLOUR / 2f;
        height *= MAX_HEIGHT;
        return height;
    }
}
