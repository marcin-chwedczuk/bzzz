package pl.marcinchwedczuk.bzzz.primitives.passives;

import pl.marcinchwedczuk.bzzz.primitives.ComponentId;
import pl.marcinchwedczuk.bzzz.primitives.ElementBase;
import pl.marcinchwedczuk.bzzz.primitives.LogicState;
import pl.marcinchwedczuk.bzzz.primitives.wires.Wire;
import pl.marcinchwedczuk.bzzz.simulator.Simulator;

public class Switch extends ElementBase {
    private final Wire output;

    public Switch(Simulator simulator, ComponentId componentId) {
        super(simulator, componentId);

        output = new Wire(simulator, componentId.extend("outputPin"));
    }

    public Wire output() { return output; }

    public void highState() {
        output.applyState(LogicState.ONE, componentId());
    }

    public void lowState() {
        output.applyState(LogicState.ZERO, componentId());
    }

    public void off() {
        output.applyState(LogicState.NOT_CONNECTED, componentId());
    }
}
