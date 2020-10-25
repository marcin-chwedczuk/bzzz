package pl.marcinchwedczuk.bzzz.gates;

import pl.marcinchwedczuk.bzzz.primitives.ComponentId;
import pl.marcinchwedczuk.bzzz.primitives.gates.And3;
import pl.marcinchwedczuk.bzzz.primitives.gates.And4;
import pl.marcinchwedczuk.bzzz.simulator.CircuitBuilder;
import pl.marcinchwedczuk.bzzz.util.BaseTruthTableTest;
import pl.marcinchwedczuk.bzzz.util.TruthTable;

public class And4Test extends BaseTruthTableTest<And4> {
    @Override
    protected And4 createComponent(CircuitBuilder builder, ComponentId sut) {
        return builder.and4(sut);
    }

    @Override
    protected TruthTable truthTable() {
        return TruthTable.fromBooleanFunction((a, b, c, d) -> a && b && c && d);
    }
}
