package um.nija123098.darkgame.base.dispatch.transfers;

import um.nija123098.darkgame.base.dispatch.Internal;

/**
 * Created by Dev on 12/30/2016.
 */
public class TickInternal extends Internal {
    private final long delta;
    public TickInternal(long delta) {
        this.delta = delta;
    }
    public long getDelta() {
        return this.delta;
    }
}
