package um.nija123098.darkgame.implementation.noside.entity;

import org.lwjgl.util.vector.Vector3f;

/**
 * Created by Dev on 1/2/2017.
 */
public class Physics {
    private Vector3f position, rotation, scale;

    public Physics(Vector3f position, Vector3f rotation, Vector3f scale) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }

    public Vector3f getPosition() {
        return this.position;
    }

    public Vector3f getRotation() {
        return this.rotation;
    }

    public Vector3f getScale() {
        return this.scale;
    }

}
