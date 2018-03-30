package um.nija123098.darkgame.implementation.client.processes.clientdisplayprocess.model;

import um.nija123098.darkgame.implementation.client.processes.clientdisplayprocess.textures.ModelTexture;

/**
 * Created by Jack on 7/15/2016.
 */
public class TexturedModel {
    private RawModel rawMode;
    private ModelTexture modelTexture;
    public TexturedModel(RawModel rawMode, ModelTexture modelTexture) {
        this.rawMode = rawMode;
        this.modelTexture = modelTexture;
    }
    public RawModel getRawModel() {
        return this.rawMode;
    }
    public ModelTexture getModelTexture() {
        return this.modelTexture;
    }
}
