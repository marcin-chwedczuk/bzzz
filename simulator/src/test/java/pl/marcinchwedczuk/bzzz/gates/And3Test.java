package pl.marcinchwedczuk.bzzz.gates;

import pl.marcinchwedczuk.bzzz.primitives.ComponentId;
import pl.marcinchwedczuk.bzzz.primitives.gates.And3;
import pl.marcinchwedczuk.bzzz.simulator.CircuitBuilder;
import pl.marcinchwedczuk.bzzz.util.BaseTruthTableTest;
import pl.marcinchwedczuk.bzzz.util.TruthTable;

public class And3Test extends BaseTruthTableTest<And3> {
    @Override
    protected And3 createComponent(CircuitBuilder builder, ComponentId sut) {
        return builder.and3(sut);
    }

    @Override
    protected TruthTable truthTable() {
        return TruthTable.fromBooleanFunction((a, b, c) -> a && b && c);
    }
}
