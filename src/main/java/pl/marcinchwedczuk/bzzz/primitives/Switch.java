package pl.marcinchwedczuk.bzzz.primitives;

import pl.marcinchwedczuk.bzzz.simulator.Simulator;

public class Switch extends ActiveElement {
    private final Wire output;

    public Switch(Simulator simulator, ComponentId componentId) {
        super(simulator, componentId);

        output = new Wire(simulator, componentId.extend("outputPin"));
    }

    public Wire output() { return output; }

    public void highState() {
        output.forceState(LogicState.HIGH, componentId());
    }

    public void lowState() {
        output.forceState(LogicState.LOW, componentId());
    }

    public void off() {
        output.forceState(LogicState.HIGH_IMPEDANCE, componentId());
    }
}
