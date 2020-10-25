package pl.marcinchwedczuk.bzzz.gates;

import pl.marcinchwedczuk.bzzz.primitives.ComponentId;
import pl.marcinchwedczuk.bzzz.primitives.LogicState;
import pl.marcinchwedczuk.bzzz.primitives.gates.And4;
import pl.marcinchwedczuk.bzzz.primitives.gates.And5;
import pl.marcinchwedczuk.bzzz.simulator.CircuitBuilder;
import pl.marcinchwedczuk.bzzz.util.BaseTruthTableTest;
import pl.marcinchwedczuk.bzzz.util.TruthTable;

public class And5Test extends BaseTruthTableTest<And5> {
    @Override
    protected And5 createComponent(CircuitBuilder builder, ComponentId sut) {
        return builder.and5(sut);
    }

    @Override
    protected TruthTable truthTable() {
        return TruthTable.fromBooleanFunction((a, b, c, d, e) -> a && b && c && d && e);
    }
}
