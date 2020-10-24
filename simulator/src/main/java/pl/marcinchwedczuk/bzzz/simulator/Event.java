package pl.marcinchwedczuk.bzzz.simulator;

import pl.marcinchwedczuk.bzzz.primitives.ComponentId;

public class Event {
    public final ComponentId source;
    public final Instant fireAt;
    public final Runnable action;

    public Event(ComponentId source,
                 Instant fireAt,
                 Runnable action) {
        this.source = source;
        this.fireAt = fireAt;
        this.action = action;
    }

    public void fire() {
        action.run();
    }
}
