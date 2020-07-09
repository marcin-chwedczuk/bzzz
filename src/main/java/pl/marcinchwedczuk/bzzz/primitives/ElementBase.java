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
}
