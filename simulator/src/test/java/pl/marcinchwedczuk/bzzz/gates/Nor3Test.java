package pl.marcinchwedczuk.bzzz.gates;

import pl.marcinchwedczuk.bzzz.primitives.ComponentId;
import pl.marcinchwedczuk.bzzz.primitives.gates.Nor;
import pl.marcinchwedczuk.bzzz.primitives.gates.Nor3;
import pl.marcinchwedczuk.bzzz.simulator.CircuitBuilder;
import pl.marcinchwedczuk.bzzz.util.BaseTruthTableTest;
import pl.marcinchwedczuk.bzzz.util.TruthTable;

public class Nor3Test extends BaseTruthTableTest<Nor3> {
    @Override
    protected Nor3 createComponent(CircuitBuilder builder, ComponentId sut) {
        return builder.nor3(sut);
    }

    @Override
    protected TruthTable truthTable() {
        return TruthTable.fromBooleanFunction((a, b, c) -> !(a || b || c));
    }
}
