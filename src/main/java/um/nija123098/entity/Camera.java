package um.nija123098.entity;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by Jack on 7/15/2016.
 */
public class Camera {

    private float distanceFromPlayer = 00;
    private float angleAroundPlayer = 0;
    private Player player;

    private Vector3f position = new Vector3f(0,50,0);
    private float pitch;
    private float yaw;
    private float roll;
    public Camera(Player player){
        this.player = player;
    }
    public void move(){
        float zoomLevel = Mouse.getDWheel() * .1f;
        distanceFromPlayer -= zoomLevel;
        if (Mouse.isButtonDown(1)){
            float pitchChange = Mouse.getDY() * .1f;
            pitch -= pitchChange;
        }
        if (Mouse.isButtonDown(0)){
            float angleChange = Mouse.getDX() * .3f;
            angleAroundPlayer -= angleChange;
        }
        float horzDistance = (float) (this.distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
        this.position.y = this.player.getPosition().y + horzDistance + 10;//  - this.player.getPosition().y; removes player jump
        float virtDistance = (float) (this.distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
        float theta = this.player.getRotY() + this.angleAroundPlayer;
        float offX = (float) (horzDistance * Math.sin(Math.toRadians(theta)));
        float offZ = (float) (horzDistance * Math.cos(Math.toRadians(theta)));
        this.position.x = player.getPosition().x - offX;
        this.position.z = player.getPosition().z - offZ;
        this.yaw = 180 - (player.getRotY() + this.angleAroundPlayer);
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
