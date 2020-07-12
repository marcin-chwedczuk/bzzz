package pl.marcinchwedczuk.bzzz.primitives;

import pl.marcinchwedczuk.bzzz.primitives.wires.Wire;
import pl.marcinchwedczuk.bzzz.simulator.Simulator;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiConsumer;

public abstract class BaseElement {
    private final Simulator simulator;
    private final ComponentId componentId;
    private final long jitter;

    public BaseElement(Simulator simulator,
                       ComponentId componentId) {
        this.simulator = simulator;
        this.componentId = componentId;
        this.jitter = selectJitter();
    }

    public final ComponentId componentId() {
        return componentId;
    }

    protected final Simulator simulator() {
        return simulator;
    }

    protected long propagationDelay() {
        return 0;
    }

    protected final void scheduleWithPropagationDelay(Runnable action) {
        simulator().schedule(
            propagationDelay(), componentId(), action);
    }

    protected final void scheduleInitialization(Runnable action) {
        simulator().schedule(0, componentId().extend("::init"), action);
    }

    protected static void onStateChange(Wire w,
                                        BiConsumer<LogicState, ComponentId> action) {
        w.registerListener(new LogicStateChangedListener() {
            @Override
            public void onStateChanged(LogicState newState, ComponentId sourceId) {
                action.accept(newState, sourceId);
            }
        });
    }

    protected static void onStateChange(Wire w, Runnable action) {
        w.registerListener(new LogicStateChangedListener() {
            @Override
            public void onStateChanged(LogicState newState, ComponentId sourceId) {
                action.run();
            }
        });
    }

    private static long selectJitter() {
        var r = ThreadLocalRandom.current();
        // To resolve oscillations like in RS N switch
        // with R=1, S=1 we need introduce a jitter in
        // ideal components.
        // stddev is *N, (70% percent of values in [-N; N]).
        long jitter = (long)(r.nextGaussian() * 5);
        return jitter;
    }
}
