package um.nija123098.darkgame.base.dispatch.transfers;

import um.nija123098.darkgame.base.dispatch.Internal;

/**
 * The even dispatched before the startup
 * internal to set up all processes so
 * when StartupInternal has been sent
 * all Processes run normally.
 *
 * Natives setup, file loading, and other executions
 * should be invoked at the receiving of this event
 */
public class SetupInternal extends Internal {
}
