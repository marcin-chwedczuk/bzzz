package pl.marcinchwedczuk.bzzz.simulator;

import pl.marcinchwedczuk.bzzz.primitives.ComponentId;

import java.util.PriorityQueue;

public class EventLoopSimulator {
    private static final PriorityQueue<Event> eventQueue = new PriorityQueue<>(
            new EventsByTimestamp());

    private static long time = 1_000_000;

    public static long currentTime() {
        return time;
    }

    public static void schedule(Event event) {
        eventQueue.add(event);
    }

    public static void schedule(long delay,
                                ComponentId source,
                                Runnable action) {
        schedule(new Event(source, time + delay, action));
    }

    public static void runSingleStep() {
        // TODO
    }

    public static void run() {
        Event event;
        while ((event = eventQueue.poll()) != null) {
            time = event.fireAt;
            event.fire();
        }
    }

    public static void scheduleAfterSteadyState(Runnable validate) {

    }
}
