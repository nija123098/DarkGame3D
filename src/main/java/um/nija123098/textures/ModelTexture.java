package um.nija123098.textures;

/**
 * Created by Jack on 7/15/2016.
 */
public class ModelTexture {
    private int textureID;
    private float sineDamper = 1;
    private float reflectivity = 0;
    private boolean hasTransparency = false;
    private boolean useFakeLighting = false;

    private int numberOfRows = 1;

    public ModelTexture(int textureID) {
        this.textureID = textureID;
    }
    public int getNumberOfRows() {
        return this.numberOfRows;
    }
    public void setNumberOfRows(int numberOfRows) {
        this.numberOfRows = numberOfRows;
    }
    public boolean isHasTransparency() {
        return hasTransparency;
    }
    public void setHasTransparency(boolean hasTransparency) {
        this.hasTransparency = hasTransparency;
    }
    public boolean isUseFakeLighting() {
        return this.useFakeLighting;
    }
    public void setUseFakeLighting(boolean useFakeLighting) {
        this.useFakeLighting = useFakeLighting;
    }

    public int getTextureID() {
        return this.textureID;
    }
    public float getSineDamper() {
        return this.sineDamper;
    }
    public void setSineDamper(float sineDamper) {
        this.sineDamper = sineDamper;
    }
    public float getReflectivity() {
        return this.reflectivity;
    }
    public void setReflectivity(float reflectivity) {
        this.reflectivity = reflectivity;
    }
}
