package pl.marcinchwedczuk.bzzz.gates;

import pl.marcinchwedczuk.bzzz.primitives.ComponentId;
import pl.marcinchwedczuk.bzzz.primitives.gates.Nor4;
import pl.marcinchwedczuk.bzzz.primitives.gates.Nor5;
import pl.marcinchwedczuk.bzzz.simulator.CircuitBuilder;
import pl.marcinchwedczuk.bzzz.util.BaseTruthTableTest;
import pl.marcinchwedczuk.bzzz.util.TruthTable;

public class Nor5Test extends BaseTruthTableTest<Nor5> {
    @Override
    protected Nor5 createComponent(CircuitBuilder builder, ComponentId sut) {
        return builder.nor5(sut);
    }

    @Override
    protected TruthTable truthTable() {
        return TruthTable.fromBooleanFunction((a, b, c, d, e) -> !(a || b || c || d || e));
    }
}
