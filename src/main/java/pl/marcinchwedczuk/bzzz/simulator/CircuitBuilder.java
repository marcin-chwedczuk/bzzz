package pl.marcinchwedczuk.bzzz.simulator;

import pl.marcinchwedczuk.bzzz.primitives.ComponentId;
import pl.marcinchwedczuk.bzzz.primitives.gates.Inverter;
import pl.marcinchwedczuk.bzzz.primitives.gates.Nand;
import pl.marcinchwedczuk.bzzz.primitives.gates.Nand3;
import pl.marcinchwedczuk.bzzz.primitives.gates.TriStateBuffer;
import pl.marcinchwedczuk.bzzz.primitives.passives.Probe;
import pl.marcinchwedczuk.bzzz.primitives.passives.Switch;
import pl.marcinchwedczuk.bzzz.primitives.wires.Wire;

public class CircuitBuilder {
    public final Simulator simulator;

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

    public TriStateBuffer triStateBuffer(String name) {
        return new TriStateBuffer(simulator, ComponentId.of(name));
    }

    public Nand nand(ComponentId componentId) {
        return new Nand(simulator, componentId);
    }

    public Nand3 nand3(ComponentId componentId) {
        return new Nand3(simulator, componentId);
    }
}
