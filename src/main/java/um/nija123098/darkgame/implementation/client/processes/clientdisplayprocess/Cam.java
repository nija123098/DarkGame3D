package um.nija123098.darkgame.implementation.client.processes.clientdisplayprocess;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import um.nija123098.darkgame.implementation.noside.entity.Ent;
import um.nija123098.darkgame.util.Maths;

/**
 * Made by nija123098 on 1/21/2017
 */
public class Cam {
    private Ent ent;
    private Matrix4f viewMatrix;
    private Vector3f positionOff = new Vector3f(0,50,0), rotation = new Vector3f();
    public Cam(Ent ent){
        this.ent = ent;
    }
    public void update(){
        viewMatrix = Maths.createViewMatrix(Vector3f.add(new Vector3f(ent.getPhysics().getPosition()), this.positionOff, null), ent.getPhysics().getRotation());
    }
    public Matrix4f getViewMatrix(){
        return this.viewMatrix;
    }
}
