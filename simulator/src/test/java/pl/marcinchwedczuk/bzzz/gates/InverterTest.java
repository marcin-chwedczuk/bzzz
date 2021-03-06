package pl.marcinchwedczuk.bzzz.gates;

import pl.marcinchwedczuk.bzzz.primitives.ComponentId;
import pl.marcinchwedczuk.bzzz.primitives.gates.Buffer;
import pl.marcinchwedczuk.bzzz.primitives.gates.Inverter;
import pl.marcinchwedczuk.bzzz.simulator.CircuitBuilder;
import pl.marcinchwedczuk.bzzz.util.BaseTruthTableTest;
import pl.marcinchwedczuk.bzzz.util.TruthTable;

public class InverterTest extends BaseTruthTableTest<Inverter> {
    @Override
    protected Inverter createComponent(CircuitBuilder builder, ComponentId sut) {
        return builder.inverter(sut);
    }

    @Override
    protected TruthTable truthTable() {
        return TruthTable.fromBooleanFunction(a -> !a);
    }
}
