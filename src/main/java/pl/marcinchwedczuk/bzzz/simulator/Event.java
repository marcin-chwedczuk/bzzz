package pl.marcinchwedczuk.bzzz.simulator;

import pl.marcinchwedczuk.bzzz.primitives.ComponentId;

public class Event {
    public final ComponentId source;
    public final long fireAt;
    public final Runnable action;
    public final String description;

    public Event(ComponentId source,
                 long fireAt,
                 Runnable action,
                 String description) {
        this.source = source;
        this.fireAt = fireAt;
        this.action = action;
        this.description = description;
    }

    public void fire() {
        action.run();
    }
}
