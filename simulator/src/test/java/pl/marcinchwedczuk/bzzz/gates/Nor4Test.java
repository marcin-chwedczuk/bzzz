package pl.marcinchwedczuk.bzzz.gates;

import pl.marcinchwedczuk.bzzz.primitives.ComponentId;
import pl.marcinchwedczuk.bzzz.primitives.gates.Nor3;
import pl.marcinchwedczuk.bzzz.primitives.gates.Nor4;
import pl.marcinchwedczuk.bzzz.simulator.CircuitBuilder;
import pl.marcinchwedczuk.bzzz.util.BaseTruthTableTest;
import pl.marcinchwedczuk.bzzz.util.TruthTable;

public class Nor4Test extends BaseTruthTableTest<Nor4> {
    @Override
    protected Nor4 createComponent(CircuitBuilder builder, ComponentId sut) {
        return builder.nor4(sut);
    }

    @Override
    protected TruthTable truthTable() {
        return TruthTable.fromBooleanFunction((a, b, c, d) -> !(a || b || c || d));
    }
}
