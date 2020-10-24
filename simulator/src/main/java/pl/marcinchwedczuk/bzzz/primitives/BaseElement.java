package pl.marcinchwedczuk.bzzz.primitives;

import pl.marcinchwedczuk.bzzz.logger.Logger;
import pl.marcinchwedczuk.bzzz.primitives.wires.Wire;
import pl.marcinchwedczuk.bzzz.simulator.ComponentType;
import pl.marcinchwedczuk.bzzz.simulator.Duration;
import pl.marcinchwedczuk.bzzz.simulator.Simulator;

public abstract class BaseElement {
    private final Simulator simulator;
    private final ComponentId componentId;
    private final ComponentType componentType;
    protected final Logger logger;

    public BaseElement(Simulator simulator,
                       ComponentId componentId) {
        this.simulator = simulator;
        this.componentId = componentId;
        this.componentType = ComponentType.of(this.getClass());
        this.logger = new Logger(componentId, componentType);
    }

    public final ComponentId componentId() {
        return componentId;
    }

    public final ComponentType componentType() {
        return componentType;
    }

    protected final Simulator simulator() {
        return simulator;
    }

    protected Duration propagationDelay() {
        return Duration.zero;
    }

    protected final void scheduleWithPropagationDelay(Runnable action) {
        simulator().schedule(componentId, propagationDelay(), action);
    }

    protected final void scheduleInitialization(Runnable action) {
        simulator().schedule(componentId, Duration.zero, action);
    }

    protected static void onStateChange(Wire w, Runnable action) {
        w.registerListener((newState, sourceId) -> action.run());
    }
}
