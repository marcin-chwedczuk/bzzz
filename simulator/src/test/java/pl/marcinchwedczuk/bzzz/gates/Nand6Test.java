package pl.marcinchwedczuk.bzzz.gates;

import pl.marcinchwedczuk.bzzz.primitives.ComponentId;
import pl.marcinchwedczuk.bzzz.primitives.gates.Nand3;
import pl.marcinchwedczuk.bzzz.primitives.gates.Nand6;
import pl.marcinchwedczuk.bzzz.simulator.CircuitBuilder;
import pl.marcinchwedczuk.bzzz.util.BaseTruthTableTest;
import pl.marcinchwedczuk.bzzz.util.TruthTable;

public class Nand6Test extends BaseTruthTableTest<Nand6> {
    @Override
    protected Nand6 createComponent(CircuitBuilder builder, ComponentId sut) {
        return builder.nand6(sut);
    }

    @Override
    protected TruthTable truthTable() {
        return TruthTable.fromBooleanFunction((a, b, c, d, e, f) -> !(a && b && c && d && e && f));
    }
}
