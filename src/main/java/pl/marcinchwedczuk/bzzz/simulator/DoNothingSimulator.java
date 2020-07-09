package pl.marcinchwedczuk.bzzz.simulator;

import pl.marcinchwedczuk.bzzz.primitives.ComponentId;

public class DoNothingSimulator implements Simulator {
    @Override
    public void schedule(long delay, ComponentId source, Runnable action) {

    }

    @Override
    public void run() {

    }
}
