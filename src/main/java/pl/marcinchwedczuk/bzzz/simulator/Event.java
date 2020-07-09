package pl.marcinchwedczuk.bzzz.simulator;

import pl.marcinchwedczuk.bzzz.primitives.ComponentId;

public class Event {
    public final ComponentId source;
    public final long fireAt;
    public final Runnable action;

    public Event(ComponentId source, long fireAt, Runnable action) {
        this.source = source;
        this.fireAt = fireAt;
        this.action = action;
    }

    public void fire() {
        action.run();
    }
}
