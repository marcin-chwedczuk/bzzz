package pl.marcinchwedczuk.bzzz.simulator;

import pl.marcinchwedczuk.bzzz.primitives.ComponentId;

class EventProbe {
    public static EventProbe fromFiresAtAndSource(Instant fireAt, String name) {
        return new EventProbe(fireAt, name);
    }

    public final String name;
    public final Event event;
    private boolean fired;

    private EventProbe(Instant fireAt, String name) {
        this.name = name;
        this.event = new Event(
            ComponentId.of(name),
            fireAt,
            () -> {
                if (!fired) {
                    fired = true;
                }
                else {
                    throw new RuntimeException(String.format(
                        "Event %s already fired!", name));
                }
            });
    }

    public void assertFired() {
        if (!fired) {
            throw new RuntimeException(String.format(
                    "Probe %s did not fire (but should).", name));
        }
    }

    public void assertNotFired() {
        if (fired) {
            throw new RuntimeException(String.format(
                    "Probe %s fired (but should not).", name));
        }
    }
}
