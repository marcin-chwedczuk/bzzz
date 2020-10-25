package pl.marcinchwedczuk.bzzz.simulator;

import pl.marcinchwedczuk.bzzz.primitives.ComponentId;
import pl.marcinchwedczuk.bzzz.primitives.gates.*;
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

    public And and(ComponentId componentId) {
        return new And(simulator, componentId);
    }

    public And1N1 and1N1(ComponentId componentId) {
        return new And1N1(simulator, componentId);
    }

    public And3 and3(ComponentId componentId) {
        return new And3(simulator, componentId);
    }

    public And4 and4(ComponentId componentId) {
        return new And4(simulator, componentId);
    }

    public And5 and5(ComponentId componentId) {
        return new And5(simulator, componentId);
    }

}
