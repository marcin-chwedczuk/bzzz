package pl.marcinchwedczuk.bzzz.simulator;

import pl.marcinchwedczuk.bzzz.primitives.ComponentId;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public class EventQueue {
    // There can be only a single event for the given component and
    // instant. If we have two events scheduled at the same time
    // (say both inputs of NAND gates changed at the same time), the
    // later one overrides the earlier one.
    // This results in a proper behaviour as the later event uses
    // the most recent input values.
    private final TreeMap<Long, Map<ComponentId, Event>> queue = new TreeMap<>();
    private long currentTime = 1_000_000;

    public long currentTime() { return currentTime; }

    public void schedule(Event e) {
        if (e.fireAt < currentTime)
            throw new IllegalArgumentException("Cannot schedule event into the past");

        ComponentId source = e.source;
        Long fireAt = e.fireAt;

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

        if (currentTime > event.fireAt)
            throw new AssertionError();

        currentTime = event.fireAt;
        return event;
    }
}
