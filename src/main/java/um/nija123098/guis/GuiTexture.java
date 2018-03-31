package um.nija123098.guis;

import org.lwjgl.util.vector.Vector2f;

/**
 * Created by Jack on 7/16/2016.
 */
public class GuiTexture {

    private int texture;
    private Vector2f position;
    private Vector2f scail;

    public GuiTexture(int texture, Vector2f position, Vector2f scail) {
        this.texture = texture;
        this.position = position;
        this.scail = scail;
    }

    public int getTexture() {
        return this.texture;
    }

    public Vector2f getPosition() {
        return this.position;
    }

    public Vector2f getScail() {
        return this.scail;
    }
}
