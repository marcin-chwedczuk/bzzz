package pl.marcinchwedczuk.bzzz.gates;

import pl.marcinchwedczuk.bzzz.primitives.ComponentId;
import pl.marcinchwedczuk.bzzz.primitives.LogicState;
import pl.marcinchwedczuk.bzzz.primitives.gates.Buffer;
import pl.marcinchwedczuk.bzzz.primitives.gates.TriStateBuffer;
import pl.marcinchwedczuk.bzzz.simulator.CircuitBuilder;
import pl.marcinchwedczuk.bzzz.util.BaseTruthTableTest;
import pl.marcinchwedczuk.bzzz.util.TruthTable;

public class TriStateBufferTest extends BaseTruthTableTest<TriStateBuffer> {
    @Override
    protected TriStateBuffer createComponent(CircuitBuilder builder, ComponentId sut) {
        return builder.triStateBuffer(sut);
    }

    @Override
    protected TruthTable truthTable() {
        return TruthTable.fromFunction((LogicState a, LogicState enableN) -> {
            if (enableN.toTTL().isZero()) {
                return a.toTTL();
            }
            else {
                return LogicState.NOT_CONNECTED;
            }
        });
    }
}
