package pl.marcinchwedczuk.bzzz.simulator;

import pl.marcinchwedczuk.bzzz.primitives.ComponentId;
import pl.marcinchwedczuk.bzzz.primitives.gates.Inverter;
import pl.marcinchwedczuk.bzzz.primitives.passives.Probe;
import pl.marcinchwedczuk.bzzz.primitives.passives.Switch;
import pl.marcinchwedczuk.bzzz.primitives.wires.Wire;

public class CircuitBuilder {
    private final Simulator simulator;

    public CircuitBuilder(Simulator simulator) {
        this.simulator = simulator;
    }

    public Wire wire(String name) {
        return new Wire(simulator, ComponentId.of(name));
    }

    public Probe probeFor(Wire w) {
        var p = new Probe(simulator, w.componentId().extend("#probe"));
        p.input().connectWith(w);
        return p;
    }

    public Switch aSwitch(String name) {
        return new Switch(simulator, ComponentId.of(name));
    }

    public Switch switchFor(Wire w) {
        Switch s = new Switch(simulator, w.componentId().extend("#switch"));
        s.output().connectWith(w);
        return s;
    }

    public Inverter inverter(String name) {
        return new Inverter(simulator, ComponentId.of(name));
    }
}
