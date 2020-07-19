package pl.marcinchwedczuk.bzzz.primitives.wires;

import pl.marcinchwedczuk.bzzz.primitives.*;
import pl.marcinchwedczuk.bzzz.simulator.Instant;
import pl.marcinchwedczuk.bzzz.simulator.Simulator;

public class Wire extends BaseElement {

    private final ConnectedWires connectedWires = new ConnectedWires();

    private final LogicStateChangedListener listener = new LogicStateChangedListener() {
        @Override
        public void onStateChanged(LogicState newState, ComponentId sourceId) {
            Wire.this.onStateChanged(newState, sourceId);
        }
    };

    private WireState state = new WireState();
    private Instant lastStateChangeTime = Instant.of(-1);

    public Wire(Simulator simulator, ComponentId componentId) {
        super(simulator, componentId);
    }

    public LogicState logicState() { return state.logicState(); }

    public void applyState(LogicState newState, ComponentId sourceId) {
        onStateChanged(newState, sourceId);
    }

    public void registerListener(LogicStateChangedListener listener) {
        this.connectedWires.add(listener);
    }

    private void onStateChanged(LogicState newLogicState, ComponentId sourceId) {
        final WireState oldState = this.state;
        final WireState newState = this.state
                .withChangedState(newLogicState, sourceId);

        if (!oldState.equals(newState)) {
            if (simulator().time().equals(lastStateChangeTime)) {
                // Two changes in same time frame
                throw new RuntimeException(
                    "Two state changes in the same time frame on Wire: " + componentId() +
                        "previous state: " + oldState + ", new state: " + newState);
            }
            this.state = newState;
            this.lastStateChangeTime = simulator().time();

            connectedWires.onStateChanged(newLogicState, sourceId);
            this.state.detectShortCircuit();
        }
    }

    public void connectWith(Wire other) {
        connect(this, other);
    }

    public static void connect(Wire wire1, Wire wire2) {
        if (wire1 == wire2) return;

        wire1.connectedWires.add(wire2.listener);
        wire2.connectedWires.add(wire1.listener);
    }

    public static void connect(Wire wire1, Wire wire2, Wire wire3) {
        connect(wire1, wire2);
        connect(wire2, wire3);
    }
}
