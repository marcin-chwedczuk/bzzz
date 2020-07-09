package pl.marcinchwedczuk.bzzz.simulator;

import pl.marcinchwedczuk.bzzz.primitives.ComponentId;
import pl.marcinchwedczuk.bzzz.primitives.Probe;
import pl.marcinchwedczuk.bzzz.primitives.Switch;
import pl.marcinchwedczuk.bzzz.primitives.Wire;

public class CircuitBuilder {
    private final Simulator simulator;

    public CircuitBuilder(Simulator simulator) {
        this.simulator = simulator;
    }

    public Wire wire(String name) {
        return new Wire(simulator, ComponentId.of(name));
    }

    public Probe probeFor(Wire w) {
        var p = new Probe(simulator, ComponentId.of("probe-" + w.componentId()));
        Wire.connect(w, p);
        return p;
    }

    public Switch aSwitch(String name) {
        return new Switch(simulator, ComponentId.of(name));
    }
}
