package pl.marcinchwedczuk.bzzz.primitives;

import java.util.ArrayList;
import java.util.List;

public class ConnectedWires implements BroadcastReceiver {
    private final List<BroadcastReceiver> wires = new ArrayList<>();

    public void add(BroadcastReceiver receiver) {
        wires.add(receiver);
    }

    public void onBroadcast(ComponentId source, LogicState sourceNewState) {
        for (BroadcastReceiver receiver : wires) {
            receiver.onBroadcast(source, sourceNewState);
        }
    }
}
