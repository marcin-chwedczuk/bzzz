package pl.marcinchwedczuk.bzzz.primitives.wires;

import pl.marcinchwedczuk.bzzz.primitives.LogicStateChangedListener;
import pl.marcinchwedczuk.bzzz.primitives.ComponentId;
import pl.marcinchwedczuk.bzzz.primitives.LogicState;

import java.util.ArrayList;
import java.util.List;

class ConnectedWires implements LogicStateChangedListener {
    private final List<LogicStateChangedListener> wires = new ArrayList<>();

    public void add(LogicStateChangedListener receiver) {
        wires.add(receiver);
    }

    public void onStateChanged(LogicState newState, ComponentId sourceId) {
        for (LogicStateChangedListener receiver : wires) {
            receiver.onStateChanged(newState, sourceId);
        }
    }
}
