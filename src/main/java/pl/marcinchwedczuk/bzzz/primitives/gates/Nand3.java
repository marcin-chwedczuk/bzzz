package pl.marcinchwedczuk.bzzz.primitives.gates;

import pl.marcinchwedczuk.bzzz.primitives.BaseElement;
import pl.marcinchwedczuk.bzzz.primitives.ComponentId;
import pl.marcinchwedczuk.bzzz.primitives.LogicState;
import pl.marcinchwedczuk.bzzz.primitives.wires.Wire;
import pl.marcinchwedczuk.bzzz.simulator.Simulator;

public class Nand3 extends BaseElement {
    private final Wire input1;
    private final Wire input2;
    private final Wire input3;
    private final Wire output;

    @Override
    protected long propagationDelay() {
        return 200;
    }

    public Nand3(Simulator simulator, ComponentId componentId) {
        super(simulator, componentId);

        input1 = new Wire(simulator, componentId.extend("input1Pin"));
        input2 = new Wire(simulator, componentId.extend("input2Pin"));
        input3 = new Wire(simulator, componentId.extend("input3Pin"));
        output = new Wire(simulator, componentId.extend("outputPin"));

        onStateChange(input1, this::updateOutput);
        onStateChange(input2, this::updateOutput);
        onStateChange(input3, this::updateOutput);

        scheduleInitialization(describeAs("schedule initialization"), this::updateOutput);
    }

    private void updateOutput() {
        LogicState input1LS = input1.logicState().toTTL();
        LogicState input2LS = input2.logicState().toTTL();
        LogicState input3LS = input3.logicState().toTTL();

        LogicState outputLS = (input1LS.isOne() && input2LS.isOne() && input3LS.isOne())
                ? LogicState.ZERO
                : LogicState.ONE;

        String desc = describeAs("set output to %s because of inputs %s, %s, %s",
                outputLS, input1LS, input2LS, input3LS);
        scheduleWithPropagationDelay(desc, () -> {
            output.applyState(outputLS, componentId());
        });
    }

    public Wire input1() { return input1; }
    public Wire input2() { return input2; }
    public Wire input3() { return input3; }
    public Wire output() { return output; }
}
