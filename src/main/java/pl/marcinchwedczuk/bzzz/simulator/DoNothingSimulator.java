package pl.marcinchwedczuk.bzzz.simulator;

import pl.marcinchwedczuk.bzzz.logger.Logger;
import pl.marcinchwedczuk.bzzz.primitives.ComponentId;

public class DoNothingSimulator implements Simulator {
    private Instant time = Instant.of(1_000_000);

    public void advanceTime() { time = time.add(Duration.of(1)); }

    @Override
    public Instant time() {
        return time;
    }


    @Override
    public void schedule(ComponentId source, Duration delay, Runnable action) {

    }


    @Override
    public void run(String description) {

    }

    @Override
    public FiniteSimulationResult run(int maxSteps, String description) {
        return FiniteSimulationResult.FINISHED;
    }
}
