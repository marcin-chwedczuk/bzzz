package pl.marcinchwedczuk.bzzz.simulator;

import pl.marcinchwedczuk.bzzz.primitives.ComponentId;

public interface Simulator {
    void schedule(long delay, ComponentId source, Runnable action);
    void run();
}
