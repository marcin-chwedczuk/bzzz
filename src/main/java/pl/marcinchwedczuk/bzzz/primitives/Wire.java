package pl.marcinchwedczuk.bzzz.primitives;

import pl.marcinchwedczuk.bzzz.simulator.EventLoopSimulator;
import pl.marcinchwedczuk.bzzz.simulator.Simulator;

public class Wire
        extends ActiveElement
        implements BroadcastReceiver
{
    private final ConnectedWires connectedWires = new ConnectedWires();

    private WireState state = new WireState();

    public Wire(Simulator simulator, ComponentId componentId) {
        super(simulator, componentId);
    }

    public WireState state() { return state; }

    public void forceState(LogicState newStateAtSource, ComponentId sourceId) {
        onBroadcast(sourceId, newStateAtSource);
    }

    @Override
    public void onBroadcast(ComponentId sourceComponentId,
                            LogicState sourceNewState) {
        final WireState oldState = this.state;
        final WireState newState = this.state.withSourceStateChanged(
                sourceNewState, sourceComponentId);

        if (!oldState.equals(newState)) {
            this.state = newState;

            connectedWires.onBroadcast(sourceComponentId, sourceNewState);
            this.state.detectShortCircuit();
        }
    }

    public static void connect(Wire wire, Probe probe) {
        wire.connectedWires.add(probe);
    }

    public static void connect(Wire wire1, Wire wire2) {
        if (wire1 == wire2) return;

        wire1.connectedWires.add(wire2);
        wire2.connectedWires.add(wire1);
    }

    public static void connect(Wire wire1, Wire wire2, Wire wire3) {
        connect(wire1, wire2);
        connect(wire2, wire3);
    }
}
