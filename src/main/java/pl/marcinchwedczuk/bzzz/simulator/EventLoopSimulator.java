package pl.marcinchwedczuk.bzzz.simulator;

import pl.marcinchwedczuk.bzzz.primitives.ComponentId;

import java.util.PriorityQueue;
import java.util.concurrent.ThreadLocalRandom;

public class EventLoopSimulator implements Simulator {
    private final EventQueue eventQueue = new EventQueue();

    private final ThreadLocalRandom r = ThreadLocalRandom.current();

    private void schedule(Event event) {
        eventQueue.schedule(event);
    }

    public void schedule(long delay,
                         ComponentId source,
                         String description,
                         Runnable action) {

        schedule(new Event(source,
                eventQueue.currentTime() + delay,
                action, description));
    }

    public boolean runSingleStep() {
        Event event = eventQueue.poll();
        if (event == null) {
            return false;
        }
        else {
            System.out.println("T" + event.fireAt + ": " + event.description);

            event.action.run();
            return true;
        }
    }

    @Override
    public void run(int maxSteps) {
        run(maxSteps, "run(" + maxSteps + ") called.");
    }

    @Override
    public void run(int maxSteps, String description) {
        System.out.println("SIMULATOR: " + description);

        for (int i = 0; i < maxSteps; i++) {
            runSingleStep();
        }
    }

    @Override
    public void run() {
        run("run() called");
    }

    public void run(String description) {
        System.out.println("SIMULATOR: " + description);

        while (runSingleStep())
            ;
    }

    @Override
    public long time() {
        return eventQueue.currentTime();
    }
}
