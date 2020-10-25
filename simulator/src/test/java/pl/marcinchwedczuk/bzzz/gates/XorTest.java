package pl.marcinchwedczuk.bzzz.gates;

import pl.marcinchwedczuk.bzzz.primitives.ComponentId;
import pl.marcinchwedczuk.bzzz.primitives.gates.Or;
import pl.marcinchwedczuk.bzzz.primitives.gates.Xor;
import pl.marcinchwedczuk.bzzz.simulator.CircuitBuilder;
import pl.marcinchwedczuk.bzzz.util.BaseTruthTableTest;
import pl.marcinchwedczuk.bzzz.util.TruthTable;

public class XorTest extends BaseTruthTableTest<Xor> {
    @Override
    protected Xor createComponent(CircuitBuilder builder, ComponentId sut) {
        return builder.xor(sut);
    }

    @Override
    protected TruthTable truthTable() {
        return TruthTable.fromBooleanFunction((a, b) -> a ^ b);
    }
}
