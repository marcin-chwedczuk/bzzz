package pl.marcinchwedczuk.bzzz.primitives;

import pl.marcinchwedczuk.bzzz.simulator.Simulator;

public abstract class ElementBase {
    private final Simulator simulator;
    private final ComponentId componentId;

    public ElementBase(Simulator simulator,
                       ComponentId componentId) {
        this.simulator = simulator;
        this.componentId = componentId;
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
}
