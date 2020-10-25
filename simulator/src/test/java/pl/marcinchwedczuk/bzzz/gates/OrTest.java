package pl.marcinchwedczuk.bzzz.gates;

import pl.marcinchwedczuk.bzzz.primitives.ComponentId;
import pl.marcinchwedczuk.bzzz.primitives.gates.And;
import pl.marcinchwedczuk.bzzz.primitives.gates.Or;
import pl.marcinchwedczuk.bzzz.simulator.CircuitBuilder;
import pl.marcinchwedczuk.bzzz.util.BaseTruthTableTest;
import pl.marcinchwedczuk.bzzz.util.TruthTable;

public class OrTest extends BaseTruthTableTest<Or> {
    @Override
    protected Or createComponent(CircuitBuilder builder, ComponentId sut) {
        return builder.or(sut);
    }

    @Override
    protected TruthTable truthTable() {
        return TruthTable.fromBooleanFunction((a, b) -> a || b);
    }
}
