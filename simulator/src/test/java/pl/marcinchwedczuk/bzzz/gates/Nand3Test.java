package pl.marcinchwedczuk.bzzz.gates;

import pl.marcinchwedczuk.bzzz.primitives.ComponentId;
import pl.marcinchwedczuk.bzzz.primitives.gates.And3;
import pl.marcinchwedczuk.bzzz.primitives.gates.Nand3;
import pl.marcinchwedczuk.bzzz.simulator.CircuitBuilder;
import pl.marcinchwedczuk.bzzz.util.BaseTruthTableTest;
import pl.marcinchwedczuk.bzzz.util.TruthTable;

public class Nand3Test extends BaseTruthTableTest<Nand3> {
    @Override
    protected Nand3 createComponent(CircuitBuilder builder, ComponentId sut) {
        return builder.nand3(sut);
    }

    @Override
    protected TruthTable truthTable() {
        return TruthTable.fromBooleanFunction((a, b, c) -> !(a && b && c));
    }
}
