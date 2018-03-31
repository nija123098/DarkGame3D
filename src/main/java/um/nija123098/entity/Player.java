package um.nija123098.entity;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;
import um.nija123098.model.TexturedModel;
import um.nija123098.render.DisplayManager;
import um.nija123098.terrain.Terrain;

/**
 * Created by Jack on 7/16/2016.
 */
public class Player extends Entity {
    private static final float RUN_SPEED = 20;
    private static final float TURN_SPEED = 160;
    private static final float GRAVITY = -50;
    private static final float JUMP_POWER = 30;
    private static final float TERRAIN_HIGHT = 0;

    private float currentSpeed = 0;
    private float currentTurnSpeed;
    private float upwardsSpeed = 0;
    public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        super(model, position, rotX, rotY, rotZ, scale);
    }
    public void move(Terrain terrain){
        if (Keyboard.isKeyDown(Keyboard.KEY_UP)){
            this.currentSpeed = RUN_SPEED;
        }else{
            this.currentSpeed = 0;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
            this.currentTurnSpeed = -TURN_SPEED;
        }else if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
            this.currentTurnSpeed = TURN_SPEED;
        }else{
            this.currentTurnSpeed = 0;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) && this.upwardsSpeed == 0f){
            this.upwardsSpeed = JUMP_POWER;
        }
        super.increaseRotation(0, currentTurnSpeed * DisplayManager.getFrameTimeSeconds(), 0);
        float distance = currentSpeed * DisplayManager.getFrameTimeSeconds();
        float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
        float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
        super.increasePosition(dx, 0, dz);
        upwardsSpeed += GRAVITY * DisplayManager.getFrameTimeSeconds();
        super.increasePosition(0, upwardsSpeed * DisplayManager.getFrameTimeSeconds(), 0);
        float terrainHeight = terrain.getHeightOfTerrain(super.getPosition().x, super.getPosition().z);
        if (super.getPosition().y <= terrainHeight){
            upwardsSpeed = 0;
            super.getPosition().y = terrainHeight;
        }
    }
}
