package um.nija123098.darkgame.implementation.client.processes.clientdisplayprocess.entity;

import org.lwjgl.util.vector.Vector3f;

/**
 * Created by Jack on 7/15/2016.
 */
public class Light {
    private Vector3f position;
    private Vector3f colour;
    private Vector3f attenuation = new Vector3f(1,0,0);
    public Light(Vector3f position, Vector3f colour) {
        this.position = position;
        this.colour = colour;
    }
    public Light(Vector3f position, Vector3f colour, Vector3f attenuation) {
        this(position, colour);
        this.attenuation = attenuation;
    }
    public Vector3f getAttenuation() {
        return this.attenuation;
    }
    public Vector3f getPosition() {
        return this.position;
    }
    public Vector3f getColour() {
        return this.colour;
    }
}
