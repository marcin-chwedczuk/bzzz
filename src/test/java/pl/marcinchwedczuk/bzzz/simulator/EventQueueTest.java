package pl.marcinchwedczuk.bzzz.simulator;

import org.junit.Test;
import pl.marcinchwedczuk.bzzz.logger.Logger;
import pl.marcinchwedczuk.bzzz.primitives.ComponentId;

import static org.junit.Assert.*;

public class EventQueueTest {
    private final Duration d100 = Duration.of(100);
    private final Duration d200 = Duration.of(200);

    @Test public void works() {
        var queue = new EventQueue(new Logger(
                ComponentId.of(""),
                ComponentType.of(this.getClass())));
        var t = queue.currentTime();

        var e1 = EventProbe.fromFiresAtAndSource(t.add(d100), "event_1");
        var e2 = EventProbe.fromFiresAtAndSource(t.add(d100), "event_2");
        var e3 = EventProbe.fromFiresAtAndSource(t.add(d200), "event_3");

        queue.schedule(e1.event);
        queue.schedule(e2.event);
        queue.schedule(e3.event);

        queue.poll().fire();
        queue.poll().fire();

        e1.assertFired();
        e2.assertFired();
        e3.assertNotFired();

        queue.poll().fire();
        e3.assertFired();

        assertNull(queue.poll());
    }

    @Test public void later_event_overrides_earlier() {
        var queue = new EventQueue(new Logger(
                ComponentId.of(""),
                ComponentType.of(this.getClass())));
        var t = queue.currentTime();

        var e1 = EventProbe.fromFiresAtAndSource(t.add(d100), "event_1");
        var e2 = EventProbe.fromFiresAtAndSource(t.add(d100), "event_1");

        queue.schedule(e1.event);
        queue.schedule(e2.event);

        queue.poll().fire();
        assertNull(queue.poll());

        e1.assertNotFired();
        e2.assertFired();
    }
}