package pl.marcinchwedczuk.bzzz.util;

import org.junit.Assert;
import pl.marcinchwedczuk.bzzz.primitives.ComponentId;
import pl.marcinchwedczuk.bzzz.primitives.LogicState;
import pl.marcinchwedczuk.bzzz.primitives.wires.Wire;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

public class TruthTableTestCase {
    private final ComponentId componentId;
    private final LogicState[] inputs;
    private final LogicState[] outputs;

    TruthTableTestCase(int index, LogicState[] inputs, LogicState[] outputs) {
        this.componentId = ComponentId.of("test-case #" + index);
        this.inputs = inputs;
        this.outputs = outputs;
    }

    public void setInputs(Wire[] wireInputs) {
        if (wireInputs.length != inputs.length) {
            throw new IllegalArgumentException(String.format(
                    "Wrong number of input wires passed to test-case. Expected %d, got %d.",
                    inputs.length, wireInputs.length));
        }

        for (int i = 0; i < inputs.length; i++) {
            wireInputs[i].applyState(inputs[i], componentId);
        }
    }

    public void detachInputs(Wire[] wireInputs) {
        if (wireInputs.length != inputs.length) {
            throw new IllegalArgumentException(String.format(
                    "Wrong number of input wires passed to test-case. Expected %d, got %d.",
                    inputs.length, wireInputs.length));
        }

        for (int i = 0; i < inputs.length; i++) {
            wireInputs[i].applyState(LogicState.NOT_CONNECTED, componentId);
        }
    }

    public void assertOutputs(Wire[] wireOutputs) {
        if (wireOutputs.length != outputs.length) {
            throw new IllegalArgumentException(String.format(
                    "Wrong number of output wires passed to test-case. Expected %d, got %d.",
                    outputs.length, outputs.length));
        }

        var inputsString = Arrays.stream(inputs)
                .map(LogicState::toString)
                .collect(joining(", ", "[", "]"));

        var actualString = Arrays.stream(wireOutputs)
                .map(Wire::logicState)
                .map(LogicState::toString)
                .collect(joining(", ", "[", "]"));

        var expectedString = Arrays.stream(outputs)
                .map(LogicState::toString)
                .collect(joining(", ", "[", "]"));

        Assert.assertEquals("failed at " + componentId + " with inputs: " + inputsString + ", outputs:",
                expectedString, actualString);
    }
}
