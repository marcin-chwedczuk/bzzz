package pl.marcinchwedczuk.bzzz.primitives.gates;

import pl.marcinchwedczuk.bzzz.primitives.BaseElement;
import pl.marcinchwedczuk.bzzz.primitives.ComponentId;
import pl.marcinchwedczuk.bzzz.primitives.LogicState;
import pl.marcinchwedczuk.bzzz.primitives.wires.Wire;
import pl.marcinchwedczuk.bzzz.simulator.Duration;
import pl.marcinchwedczuk.bzzz.simulator.Simulator;

public class Nor2 extends BaseElement {
    public final Wire input1;
    public final Wire input2;
    public final Wire output;

    @Override
    protected Duration propagationDelay() {
        return Duration.of(200);
    }

    public Nor2(Simulator simulator, ComponentId componentId) {
        super(simulator, componentId);

        input1 = new Wire(simulator, componentId.inputPin(1));
        input2 = new Wire(simulator, componentId.inputPin(2));
        output = new Wire(simulator, componentId.outputPin());

        onStateChange(input1, this::updateOutput);
        onStateChange(input2, this::updateOutput);

        scheduleInitialization(() -> {
            LogicState input1LS = input1.logicState();
            LogicState input2LS = input2.logicState();

            LogicState outputLS = ttlNand(input1LS, input2LS);

            logger.log("set (%s, %s) -> %s",
                    input1LS, input2LS, outputLS);
            output.applyState(outputLS, componentId());
        });
    }

    private void updateOutput() {
        LogicState input1LS = input1.logicState();
        LogicState input2LS = input2.logicState();

        LogicState outputLS = ttlNand(input1LS, input2LS);

        // String desc = describeAs("set output to %s because of inputs %s, %s", outputLS, input1LS, input2LS);
        scheduleWithPropagationDelay(() -> {
            logger.log("set (%s, %s) -> %s",
                    input1LS, input2LS, outputLS);
            output.applyState(outputLS, componentId());
        });
    }

    private LogicState ttlNand(LogicState input1LS,
                               LogicState input2LS) {
        return (input1LS.toTTL().isOne() ||
                input2LS.toTTL().isOne())
                ? LogicState.ZERO
                : LogicState.ONE;
    }
}
