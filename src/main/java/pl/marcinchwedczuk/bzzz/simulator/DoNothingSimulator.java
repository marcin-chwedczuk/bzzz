package pl.marcinchwedczuk.bzzz.simulator;

import pl.marcinchwedczuk.bzzz.primitives.ComponentId;

public class DoNothingSimulator implements Simulator {
    private long time = 1_000_000;

    public void advanceTime() { time++; }

    @Override
    public long time() {
        return time;
    }

    @Override
    public void schedule(long delay, ComponentId source, String description, Runnable action) {

    }

    @Override
    public void run() {

    }

    @Override
    public void run(String description) {

    }

    @Override
    public void run(int maxSteps) {

    }

    @Override
    public void run(int maxSteps, String description) {

    }
}
