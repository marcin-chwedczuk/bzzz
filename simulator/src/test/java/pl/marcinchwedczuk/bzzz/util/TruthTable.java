package pl.marcinchwedczuk.bzzz.util;

import com.google.common.collect.Sets;
import pl.marcinchwedczuk.bzzz.primitives.LogicState;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TruthTable implements Iterable<TruthTableTestCase> {
    public static SetNumberOfInputs builder() {
        return new TruthTableBuilder();
    }

    public static TruthTable fromBooleanFunction(BiFunction<Boolean, Boolean, Boolean> f) {
        return fromFunction((wireState1, wireState2) -> LogicState.fromBoolean(
                f.apply(wireState1.toTTL().isOne(),
                        wireState2.toTTL().isOne())));
    }

    public static TruthTable fromFunction(BiFunction<LogicState, LogicState, LogicState> f) {
        return fromFunction(2, states -> f.apply(states[0], states[1]));
    }

    public static TruthTable fromBooleanFunction(TriFunction<Boolean, Boolean, Boolean, Boolean> f) {
        return fromFunction((wireState1, wireState2, wireState3) -> LogicState.fromBoolean(
                f.apply(wireState1.toTTL().isOne(),
                        wireState2.toTTL().isOne(),
                        wireState3.toTTL().isOne())));
    }

    public static TruthTable fromFunction(TriFunction<LogicState, LogicState, LogicState, LogicState> f) {
        return fromFunction(3, states -> f.apply(states[0], states[1], states[2]));
    }

    public static TruthTable fromFunction(int numInputs, Function<LogicState[], LogicState> f) {
        var statesPerSingleInput = Set.of(LogicState.ZERO, LogicState.ONE, LogicState.NOT_CONNECTED);
        var statesPerAllInputs = Collections.nCopies(numInputs, statesPerSingleInput);
        var allPossibleInputs = Sets.cartesianProduct(statesPerAllInputs);

        var builder = TruthTable.builder()
                .thatHasInputs(numInputs)
                .thatHasOutputs(1);

        for (var inputs : allPossibleInputs) {
            var inputsAsArray = inputs.toArray(LogicState[]::new);

            builder.testCaseInputs(inputsAsArray)
                    .expectOutput(f.apply(inputsAsArray));
        }

        return builder.build();
    }

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