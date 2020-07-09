package pl.marcinchwedczuk.bzzz.simulator;

import pl.marcinchwedczuk.bzzz.primitives.ComponentId;

import java.util.PriorityQueue;

public class EventLoopSimulator implements Simulator {
    private final PriorityQueue<Event> eventQueue = new PriorityQueue<>(
            new EventsByTimestamp());

    private long time = 1_000_000;

    public void schedule(Event event) {
        eventQueue.add(event);
    }

    public void schedule(long delay,
                         ComponentId source,
                         Runnable action) {
        schedule(new Event(source, time + delay, action));
    }

    public boolean runSingleStep() {
        Event event = eventQueue.poll();
        if (event == null) {
            return false;
        }
        else {
            event.action.run();
            return true;
        }
    }

    public void run() {
        Event event;
        while ((event = eventQueue.poll()) != null) {
            System.out.println("Running event from " + event.source);
            time = event.fireAt;
            event.fire();
        }
    }
}
