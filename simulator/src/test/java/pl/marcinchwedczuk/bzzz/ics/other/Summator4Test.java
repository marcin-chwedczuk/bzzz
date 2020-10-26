package pl.marcinchwedczuk.bzzz.ics.other;

import pl.marcinchwedczuk.bzzz.primitives.ComponentId;
import pl.marcinchwedczuk.bzzz.primitives.LogicState;
import pl.marcinchwedczuk.bzzz.primitives.wires.Wire;
import pl.marcinchwedczuk.bzzz.simulator.CircuitBuilder;
import pl.marcinchwedczuk.bzzz.util.BaseTruthTableTest;
import pl.marcinchwedczuk.bzzz.util.TruthTable;

public class Summator4Test extends BaseTruthTableTest<Summator4> {
    @Override
    protected Summator4 createComponent(CircuitBuilder builder, ComponentId sut) {
        return builder.summator4(sut);
    }

    @Override
    protected Wire[] inputs(Summator4 s) {
        return new Wire[]{
                s.A4, s.A3, s.A2, s.A1,
                s.B4, s.B3, s.B2, s.B1,
                s.carryIn
        };
    }

    private LogicState[] toInputs(int a, int b, int carryIn) {
        int bits = (a << 5) | (b << 1) | carryIn;
        return BaseTruthTableTest.bitsToLogicStates(4 + 4 + 1, bits);
    }

    @Override
    protected Wire[] outputs(Summator4 s) {
        return new Wire[]{
                s.carryOut, s.S4, s.S3, s.S2, s.S1
        };
    }

    private LogicState[] toOutputs(int sum, int carryOut) {
        int bits = (carryOut << 4) | sum;
        return BaseTruthTableTest.bitsToLogicStates(5, bits);
    }

    @Override
    protected TruthTable truthTable() {
        var builder = TruthTable.builder()
                .thatHasInputs(4 + 4 + 1)
                .thatHasOutputs(4 + 1);

        // @formatter:off
        for (int a = 0; a < 16; a++)
        for (int b = 0; b < 16; b++)
        for (int carryIn = 0; carryIn < 2; carryIn++) {
            int sum = a + b + carryIn;
            int carryOut = (sum >= 16) ? 1 : 0;
            sum = sum % 16;

            builder.testCaseInputs(toInputs(a, b, carryIn))
                    .expectOutput(toOutputs(sum, carryOut));
        }
        // @formatter:on

        return builder.build();
    }
}