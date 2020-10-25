package pl.marcinchwedczuk.bzzz.gates;

import pl.marcinchwedczuk.bzzz.primitives.ComponentId;
import pl.marcinchwedczuk.bzzz.primitives.gates.And;
import pl.marcinchwedczuk.bzzz.primitives.gates.Buffer;
import pl.marcinchwedczuk.bzzz.simulator.CircuitBuilder;
import pl.marcinchwedczuk.bzzz.util.BaseTruthTableTest;
import pl.marcinchwedczuk.bzzz.util.TruthTable;

public class BufferTest extends BaseTruthTableTest<Buffer> {
    @Override
    protected Buffer createComponent(CircuitBuilder builder, ComponentId sut) {
        return builder.buffer(sut);
    }

    @Override
    protected TruthTable truthTable() {
        return TruthTable.fromBooleanFunction(a -> a);
    }
}
