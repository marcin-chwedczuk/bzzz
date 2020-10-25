package pl.marcinchwedczuk.bzzz.util;

import pl.marcinchwedczuk.bzzz.primitives.LogicState;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TruthTable implements Iterable<TruthTableTestCase> {
    public static SetNumberOfInputs builder() {
        return new TruthTableBuilder();
    }

    // TODO: Use LogicState
    private final List<TruthTableTestCase> testCases;

    TruthTable(List<LogicState[]> inputs, List<LogicState[]> outputs) {
        final var rowNumbers = Stream.iterate(0, n -> n + 1);

        testCases = X
                .zip(rowNumbers, inputs.stream(), outputs.stream(), TruthTableTestCase::new)
                .collect(Collectors.toList());
    }

    @Override
    public Iterator<TruthTableTestCase> iterator() {
        return testCases.iterator();
    }

    public interface SetNumberOfInputs {
        SetNumberOfOutputs thatHasInputs(int numInputs);
    }

    public interface SetNumberOfOutputs {
        ProvideTestCase thatHasOutputs(int numOutputs);
    }

    public interface ProvideTestCase extends BuildTruthTable {
        ProvideExpectedOutput testCaseInputs(LogicState... inputs);
    }

    public interface ProvideExpectedOutput {
        ProvideTestCase expectOutput(LogicState... outputs);
    }

    public interface BuildTruthTable {
        TruthTable build();
    }
}
