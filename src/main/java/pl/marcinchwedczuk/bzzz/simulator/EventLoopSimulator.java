package pl.marcinchwedczuk.bzzz.simulator;

import pl.marcinchwedczuk.bzzz.logger.Logger;
import pl.marcinchwedczuk.bzzz.primitives.ComponentId;

import java.util.concurrent.ThreadLocalRandom;

public class EventLoopSimulator implements Simulator {
    private final ComponentId componentId = ComponentId.of("SIMULATOR");
    private final ComponentType componentType = ComponentType.of(this.getClass());

    private final Logger logger = new Logger(componentId, componentType);

    private final EventQueue eventQueue = new EventQueue(logger);


    private void schedule(Event event) {
        eventQueue.schedule(event);
    }

    public void schedule(ComponentId source,
                         Duration delay,
                         Runnable action) {
        Instant fireAt = eventQueue.currentTime().add(delay);
        schedule(new Event(source, fireAt, action));
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

    @Override
    public FiniteSimulationResult run(int maxSteps, String description) {
        logger.log(description);

        for (int i = 0; i < maxSteps; i++) {
            runSingleStep();
        }

        return eventQueue.isEmpty()
                ? FiniteSimulationResult.FINISHED
                : FiniteSimulationResult.NOT_FINISHED;
    }

    public void run(String description) {
        logger.log(description);

        while (runSingleStep())
            ;
    }

    @Override
    public Instant time() {
        return eventQueue.currentTime();
    }
}
