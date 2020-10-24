package pl.marcinchwedczuk.bzzz.simulator;

import pl.marcinchwedczuk.bzzz.primitives.ComponentId;

public interface Simulator {
    void schedule(ComponentId source, Duration delay, Runnable action);

    void run(String description);
    FiniteSimulationResult run(int maxSteps, String description);

    Instant time();
}
