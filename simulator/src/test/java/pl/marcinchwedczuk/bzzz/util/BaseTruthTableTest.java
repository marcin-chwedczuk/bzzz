package pl.marcinchwedczuk.bzzz.util;

import org.junit.Test;
import pl.marcinchwedczuk.bzzz.primitives.BaseElement;
import pl.marcinchwedczuk.bzzz.primitives.ComponentId;
import pl.marcinchwedczuk.bzzz.primitives.LogicState;
import pl.marcinchwedczuk.bzzz.primitives.wires.Wire;
import pl.marcinchwedczuk.bzzz.simulator.CircuitBuilder;
import pl.marcinchwedczuk.bzzz.simulator.EventLoopSimulator;
import pl.marcinchwedczuk.bzzz.simulator.Simulator;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.regex.Pattern;

import static java.util.Comparator.comparing;
import static pl.marcinchwedczuk.bzzz.primitives.LogicState.ONE;
import static pl.marcinchwedczuk.bzzz.primitives.LogicState.ZERO;

public abstract class BaseTruthTableTest<T extends BaseElement> {
    protected final Simulator simulator = new EventLoopSimulator();
    protected final CircuitBuilder builder = new CircuitBuilder(simulator);

    protected abstract T createComponent(CircuitBuilder builder, ComponentId sut);

    protected Wire[] inputs(T component) {
        return getFieldsMatching(component, "^(input\\d*|enable)N?$");
    }

    protected Wire[] outputs(T component) {
        return getFieldsMatching(component, "^output\\d*N?$");
    }

    protected abstract TruthTable truthTable();

    @Test
    public void test() {
        var component = createComponent(builder, ComponentId.of("sut"));

        // Stabilize component state
        simulator.run("stabilize-state");

        var inputs = inputs(component);
        var outputs = outputs(component);

        for (var testCase : truthTable()) {
            testCase.setInputs(inputs);
            simulator.run("test");

            testCase.assertOutputs(outputs);

            testCase.detachInputs(inputs);
            simulator.run("cleanup");
        }
    }

    private Wire[] getFieldsMatching(T component, String fieldNameRegex) {
        var fieldsArray = component.getClass().getFields();

        return Arrays.stream(fieldsArray)
                .filter(f -> f.getName().matches(fieldNameRegex))
                .sorted(comparing((Field f) -> inputTypeOrder(f))
                        .thenComparing(Field::getName))
                .map(f -> {
                    try {
                        return f.get(component);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                })
                .map(value -> (Wire)value)
                .toArray(Wire[]::new);
    }

    // order: inputs enable other
    private static Integer inputTypeOrder(Field f) {
        String fieldName = f.getName();
        if (fieldName.startsWith("input")) return 0;
        if (fieldName.startsWith("enable")) return 100;
        return 200;
    }

    protected static LogicState[] bitsToLogicStates(int numBits, long value) {
        var arr = new LogicState[numBits];

        for (int i = 0; i < numBits; i++) {
            int bitPos = numBits-1 - i;
            arr[i] = (value & (1 << bitPos)) != 0
                    ? ONE
                    : ZERO;
        }

        return arr;
    }
}
