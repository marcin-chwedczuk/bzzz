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

        scheduleInitialization(describeAs("schedule initialization"), () -> {
            LogicState input1LS = input1.logicState();
            LogicState input2LS = input2.logicState();
            LogicState input3LS = input3.logicState();

            LogicState initOutput = ttlNand3(input1LS, input2LS, input3LS);
            output.applyState(initOutput, componentId());
        });
    }

    private void updateOutput() {
        LogicState input1LS = input1.logicState();
        LogicState input2LS = input2.logicState();
        LogicState input3LS = input3.logicState();

        LogicState outputLS = ttlNand3(input1LS, input2LS, input3LS);

        String desc = describeAs("set output to %s because of inputs %s, %s, %s",
                outputLS, input1LS, input2LS, input3LS);
        scheduleWithPropagationDelay(desc, () -> {
            output.applyState(outputLS, componentId());
        });
    }

    private LogicState ttlNand3(LogicState input1LS, LogicState input2LS, LogicState input3LS) {
        return (input1LS.toTTL().isOne() &&
                input2LS.toTTL().isOne() &&
                input3LS.toTTL().isOne())
                    ? LogicState.ZERO
                    : LogicState.ONE;
    }

    public Wire input1() { return input1; }
    public Wire input2() { return input2; }
    public Wire input3() { return input3; }
    public Wire output() { return output; }
}
