package um.nija123098.entity;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by Jack on 7/15/2016.
 */
public class LoseCamera {
    private Vector3f position = new Vector3f(0,50,0);
    private float pitch;
    private float yaw;
    private float roll;
    public LoseCamera(){
    }
    public void move(){
        if (Keyboard.isKeyDown(Keyboard.KEY_W)){
            this.position.z -= 1f;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)){
            this.position.x += 1;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)){
            this.position.x -= 1;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)){
            this.position.z += 1f;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_R)){
            this.position.y += 1f;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_F)){
            this.position.y -= 1f;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_Q)){
            this.yaw += 1f;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_E)){
            this.yaw -= 1f;
        }
    }
    public Vector3f getPosition() {
        return this.position;
    }
    public float getPitch() {
        return this.pitch;
    }
    public float getYaw() {
        return this.yaw;
    }
    public float getRoll() {
        return this.roll;
    }
}
