package pl.marcinchwedczuk.bzzz.primitives.gates;

import pl.marcinchwedczuk.bzzz.primitives.BaseElement;
import pl.marcinchwedczuk.bzzz.primitives.ComponentId;
import pl.marcinchwedczuk.bzzz.primitives.LogicState;
import pl.marcinchwedczuk.bzzz.primitives.wires.Wire;
import pl.marcinchwedczuk.bzzz.simulator.Duration;
import pl.marcinchwedczuk.bzzz.simulator.Simulator;

public class And3 extends BaseElement {
    public final Wire input1;
    public final Wire input2;
    public final Wire input3;
    public final Wire output;

    @Override
    protected Duration propagationDelay() {
        return Duration.of(200);
    }

    public And3(Simulator simulator, ComponentId componentId) {
        super(simulator, componentId);

        input1 = new Wire(simulator, componentId.inputPin(1));
        input2 = new Wire(simulator, componentId.inputPin(2));
        input3 = new Wire(simulator, componentId.inputPin(3));
        output = new Wire(simulator, componentId.outputPin());

        onStateChange(input1, this::updateOutput);
        onStateChange(input2, this::updateOutput);
        onStateChange(input3, this::updateOutput);

        scheduleInitialization(() -> {
            LogicState input1LS = input1.logicState();
            LogicState input2LS = input2.logicState();
            LogicState input3LS = input3.logicState();

            LogicState outputLS = ttlAnd(input1LS, input2LS, input3LS);

            logger.log("[init] set (%s,%s,%s) -> %s", input1LS, input2LS, input3LS, outputLS);
            output.applyState(outputLS, componentId());
        });
    }

    private void updateOutput() {
        LogicState input1LS = input1.logicState();
        LogicState input2LS = input2.logicState();
        LogicState input3LS = input3.logicState();

        LogicState outputLS = ttlAnd(input1LS, input2LS, input3LS);

        scheduleWithPropagationDelay(() -> {
            logger.log("set (%s,%s,%s) -> %s", input1LS, input2LS, input3LS, outputLS);
            output.applyState(outputLS, componentId());
        });
    }

    private LogicState ttlAnd(LogicState input1LS,
                              LogicState input2LS,
                              LogicState input3LS) {
        return (input1LS.toTTL().isOne() &&
                input2LS.toTTL().isOne() &&
                input3LS.toTTL().isOne())
                ? LogicState.ONE
                : LogicState.ZERO;
    }

    public Wire input1() { return input1; }
    public Wire input2() { return input2; }
    public Wire input3() { return input3; }
    public Wire output() { return output; }
}
