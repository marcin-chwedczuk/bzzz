package pl.marcinchwedczuk.bzzz.simulator;

import pl.marcinchwedczuk.bzzz.primitives.ComponentId;

public interface Simulator {
    void schedule(
            long delay,
            ComponentId source,
            String description,
            Runnable action);

    void run();
    void run(String description);
    void run(int maxSteps);
    void run(int maxSteps, String description);

    default long time() { return -1; }
}
