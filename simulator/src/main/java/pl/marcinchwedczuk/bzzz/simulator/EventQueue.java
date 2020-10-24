package pl.marcinchwedczuk.bzzz.simulator;

import pl.marcinchwedczuk.bzzz.logger.Logger;
import pl.marcinchwedczuk.bzzz.primitives.ComponentId;
import pl.marcinchwedczuk.bzzz.primitives.LogicState;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public class EventQueue {
    private final Logger logger;

    // There can be only a single event for the given component and
    // instant. If we have two events scheduled at the same time
    // (say both inputs of NAND gates changed at the same time), the
    // later one overrides the earlier one.
    // This results in a proper behaviour as the later event uses
    // the most recent input values.
    private final TreeMap<Instant, Map<ComponentId, Event>> queue = new TreeMap<>();
    private Instant currentTime = Instant.of(1_000_000);

    public EventQueue(Logger logger) {
        this.logger = logger;
    }

    public Instant currentTime() { return currentTime; }

    public void schedule(Event e) {
        if (e.fireAt.isBefore(currentTime))
            throw new IllegalArgumentException("Cannot schedule event into the past");

        Instant fireAt = e.fireAt;
        ComponentId source = e.source;

        queue
            .computeIfAbsent(fireAt, key  -> new HashMap<>())
            .put(source, e);
    }

    public Event poll() {
        var entry = queue.firstEntry();
        if (entry == null) return null;

        Map<ComponentId, Event> events = entry.getValue();
        if (events.isEmpty()) {
            queue.remove(entry.getKey());
            return poll();
        }

        ComponentId key = events.keySet().iterator().next();
        Event event = events.get(key);
        events.remove(key);

        if (currentTime.isAfter(event.fireAt))
            throw new AssertionError();

        if (!currentTime.equals(event.fireAt)) {
            logger.log("\n+++++ Moving time to %s", event.fireAt);
            currentTime = event.fireAt;
        }
        return event;
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
