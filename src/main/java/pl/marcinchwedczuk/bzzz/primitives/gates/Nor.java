package pl.marcinchwedczuk.bzzz.primitives.gates;

import pl.marcinchwedczuk.bzzz.primitives.BaseElement;
import pl.marcinchwedczuk.bzzz.primitives.ComponentId;
import pl.marcinchwedczuk.bzzz.primitives.LogicState;
import pl.marcinchwedczuk.bzzz.primitives.wires.Wire;
import pl.marcinchwedczuk.bzzz.simulator.Duration;
import pl.marcinchwedczuk.bzzz.simulator.Simulator;

public class Nor extends BaseElement {
    public final Wire input1;
    public final Wire input2;
    public final Wire output;

    @Override
    protected Duration propagationDelay() {
        return Duration.of(200);
    }

    public Nor(Simulator simulator, ComponentId componentId) {
        super(simulator, componentId);

        input1 = new Wire(simulator, componentId.extend("input1Pin"));
        input2 = new Wire(simulator, componentId.extend("input2Pin"));
        output = new Wire(simulator, componentId.extend("outputPin"));

        onStateChange(input1, this::updateOutput);
        onStateChange(input2, this::updateOutput);

        scheduleInitialization(() -> {
            LogicState input1LS = input1.logicState();
            LogicState input2LS = input2.logicState();

            LogicState initOutput = ttlNand(input1LS, input2LS);
            output.applyState(initOutput, componentId());
        });
    }

    private void updateOutput() {
        LogicState input1LS = input1.logicState();
        LogicState input2LS = input2.logicState();

        LogicState outputLS = ttlNand(input1LS, input2LS);

        // String desc = describeAs("set output to %s because of inputs %s, %s", outputLS, input1LS, input2LS);
        scheduleWithPropagationDelay(() -> {
            output.applyState(outputLS, componentId());
        });
    }

    private LogicState ttlNand(LogicState input1LS, LogicState input2LS) {
        return (input1LS.toTTL().isOne() && input2LS.toTTL().isOne())
                ? LogicState.ZERO
                : LogicState.ONE;
    }
}
