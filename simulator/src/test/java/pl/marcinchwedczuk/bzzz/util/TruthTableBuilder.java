package pl.marcinchwedczuk.bzzz.util;

import pl.marcinchwedczuk.bzzz.primitives.LogicState;

import java.util.ArrayList;
import java.util.List;

import static pl.marcinchwedczuk.bzzz.util.X.defensiveCopy;

class TruthTableBuilder
        implements TruthTable.SetNumberOfInputs, TruthTable.SetNumberOfOutputs,
                    TruthTable.ProvideTestCase, TruthTable.ProvideExpectedOutput
{
    private int numInputs;
    private int numOutputs;
    private final List<LogicState[]> inputs;
    private final List<LogicState[]> outputs;

    TruthTableBuilder() {
        numInputs = -1;
        numOutputs = -1;
        inputs = new ArrayList<>();
        outputs = new ArrayList<>();
    }

    @Override
    public TruthTableBuilder thatHasInputs(int numInputs) {
        this.numInputs = numInputs;
        return this;
    }

    @Override
    public TruthTableBuilder thatHasOutputs(int numOutputs) {
        this.numOutputs = numOutputs;
        return this;
    }

    @Override
    public TruthTableBuilder testCaseInputs(LogicState... inputs) {
        checkInputsCount(inputs);
        this.inputs.add(defensiveCopy(inputs));

        return this;
    }

    @Override
    public TruthTableBuilder expectOutput(LogicState... outputs) {
        checkOutputsCount(outputs);
        this.outputs.add(defensiveCopy(outputs));

        return this;
    }

    public TruthTable build() {
        inputs.forEach(this::checkInputsCount);
        outputs.forEach(this::checkOutputsCount);

        if (inputs.size() != outputs.size()) {
            throw new IllegalArgumentException("Different number of inputs and expected outputs!");
        }

        return new TruthTable(inputs, outputs);
    }

    private void checkInputsCount(LogicState[] inputs) {
        if (inputs.length != this.numInputs) {
            throw new IllegalArgumentException(String.format(
                    "Invalid number of inputs, expected %d but got %d.", numInputs, inputs.length));
        }
    }

    private void checkOutputsCount(LogicState[] outputs) {
        if (outputs.length != this.numOutputs) {
            throw new IllegalArgumentException(String.format(
                    "Invalid number of outputs, expected %d but got %d.", numOutputs, outputs.length));
        }
    }
}
