package pl.marcinchwedczuk.bzzz.primitives.passives;

import pl.marcinchwedczuk.bzzz.primitives.ComponentId;
import pl.marcinchwedczuk.bzzz.primitives.BaseElement;
import pl.marcinchwedczuk.bzzz.primitives.LogicState;
import pl.marcinchwedczuk.bzzz.primitives.LogicStateChangedListener;
import pl.marcinchwedczuk.bzzz.primitives.wires.Wire;
import pl.marcinchwedczuk.bzzz.simulator.Simulator;

public class Probe extends BaseElement {
    private final LogicStateChangedListener listener = new LogicStateChangedListener() {
        @Override
        public void onStateChanged(LogicState newState, ComponentId sourceId) {
            logicState = newState;
            System.out.printf("PROBE: %s NEW STATE: %s%n", componentId(), state());
        }
    };

    private final Wire input;

    private LogicState logicState = LogicState.NOT_CONNECTED;

    public Probe(Simulator simulator, ComponentId componentId) {
        super(simulator, componentId);

        input = new Wire(simulator, componentId.extend("inputPin"));
        input.registerListener(listener);
    }

    public LogicState state() { return logicState; }
    public Wire input() { return input; }

    public void assertOne() { assertState(LogicState.ONE); }
    public void assertZero() { assertState(LogicState.ZERO); }
    public void assertNotConnected() { assertState(LogicState.NOT_CONNECTED); }

    public void assertState(LogicState expected) {
        if (state() != expected)
            throw new RuntimeException(String.format(
                    "Expected state: %s but was: %s.%n" +
                    "Probe: %s", expected, state(), componentId()));
    }
}
