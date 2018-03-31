package um.nija123098.textures;

/**
 * Created by Jack on 7/15/2016.
 */
public class TerrainTexturePack {
    private TerrainTexture background, r, g, b, blendMap;
    public TerrainTexturePack(TerrainTexture background, TerrainTexture r, TerrainTexture g, TerrainTexture b, TerrainTexture blendMap) {
        this.background = background;
        this.r = r;
        this.g = g;
        this.b = b;
        this.blendMap = blendMap;
    }
    public TerrainTexture getBackground() {
        return this.background;
    }
    public TerrainTexture getR() {
        return this.r;
    }
    public TerrainTexture getG() {
        return this.g;
    }
    public TerrainTexture getB() {
        return this.b;
    }
    public TerrainTexture getBlendMap() {
        return this.blendMap;
    }
}
