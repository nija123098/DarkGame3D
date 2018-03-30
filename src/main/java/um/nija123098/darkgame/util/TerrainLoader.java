package um.nija123098.darkgame.util;

import javafx.util.Pair;
import org.lwjgl.util.vector.Vector3f;
import um.nija123098.darkgame.implementation.client.processes.clientdisplayprocess.model.RawModel;
import um.nija123098.darkgame.implementation.client.processes.clientdisplayprocess.render.Loader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Made by nija123098 on 1/21/2017
 */
public class TerrainLoader {
    private static final float SIZE = 800;
    private static final float MAX_HEIGHT = 40;
    private static final float MAX_Pixel_COLOUR = 16777216;// 256^3
    private Loader loader;
    public TerrainLoader(Loader loader) {
        this.loader = loader;
    }
    public Pair<RawModel, float[][]> load(Loader loader, String heightMap){
        float[][] heights;
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(Resource.getPath(Resource.ResourceType.TEXTURE, heightMap)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        int vertexCount = image.getHeight();
        heights = new float[vertexCount][vertexCount];
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
                heights[j][i] = height;
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
        return new Pair<RawModel, float[][]>(loader.loadToVAO(vertices, textureCoords, normals, indices), heights);
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
