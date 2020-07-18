package pl.marcinchwedczuk.bzzz.simulator;

import org.junit.Test;

import static org.junit.Assert.*;

public class EventQueueTest {

    @Test public void works() {
        var queue = new EventQueue();

        var e1 = EventProbe.fromFiresAtAndSource(100, "event_1");
        var e2 = EventProbe.fromFiresAtAndSource(100, "event_2");
        var e3 = EventProbe.fromFiresAtAndSource(200, "event_3");

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
        var queue = new EventQueue();

        var e1 = EventProbe.fromFiresAtAndSource(100, "event_1");
        var e2 = EventProbe.fromFiresAtAndSource(100, "event_1");

        queue.schedule(e1.event);
        queue.schedule(e2.event);

        queue.poll().fire();
        assertNull(queue.poll());

        e1.assertNotFired();
        e2.assertFired();
    }
}