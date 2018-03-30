package um.nija123098.darkgame.implementation.noside.entity;

import um.nija123098.darkgame.implementation.client.processes.clientdisplayprocess.model.TexturedModel;
import um.nija123098.darkgame.implementation.noside.entity.ai.AI;

/**
 * Created by Dev on 1/2/2017.
 */
public class Ent {
    private transient AI ai;
    private Physics physics;
    private GameMech gameMech;
    private transient GraphicsEntity graphicsEntity;

    @Deprecated
    public Ent(Physics physics, TexturedModel model) {
        this.physics = physics;
        this.graphicsEntity = new GraphicsEntity(model, this);
    }

    public Physics getPhysics() {
        return this.physics;
    }

    public GraphicsEntity getGraphics() {
        return this.graphicsEntity;
    }
}
