package pl.marcinchwedczuk.bzzz.gates;

import org.junit.Test;
import pl.marcinchwedczuk.bzzz.primitives.BaseElement;
import pl.marcinchwedczuk.bzzz.primitives.ComponentId;
import pl.marcinchwedczuk.bzzz.primitives.gates.Nand;
import pl.marcinchwedczuk.bzzz.primitives.wires.Wire;
import pl.marcinchwedczuk.bzzz.simulator.CircuitBuilder;
import pl.marcinchwedczuk.bzzz.util.TruthTable;
import pl.marcinchwedczuk.bzzz.util.BaseTruthTableTest;

import static pl.marcinchwedczuk.bzzz.primitives.LogicState.ONE;
import static pl.marcinchwedczuk.bzzz.primitives.LogicState.ZERO;

public class And3Test extends BaseTruthTableTest<Nand> {

    @Override
    protected Nand createComponent(CircuitBuilder builder, ComponentId sut) {
        return builder.nand(sut);
    }

    @Override
    protected TruthTable truthTable() {
        return TruthTable.builder()
                .thatHasInputs(2).thatHasOutputs(1)
                .testCaseInputs(ONE, ONE).expectOutput(ZERO)
                .testCaseInputs(ONE, ONE).expectOutput(ZERO)
                .testCaseInputs(ZERO, ONE).expectOutput(ZERO)
                .build();
    }
}
