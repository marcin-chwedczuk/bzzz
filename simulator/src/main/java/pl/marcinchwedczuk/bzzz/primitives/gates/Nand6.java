package pl.marcinchwedczuk.bzzz.primitives.gates;

import pl.marcinchwedczuk.bzzz.primitives.BaseElement;
import pl.marcinchwedczuk.bzzz.primitives.ComponentId;
import pl.marcinchwedczuk.bzzz.primitives.LogicState;
import pl.marcinchwedczuk.bzzz.primitives.wires.Wire;
import pl.marcinchwedczuk.bzzz.simulator.Duration;
import pl.marcinchwedczuk.bzzz.simulator.Simulator;

public class Nand6 extends BaseElement {
    public final Wire input1;
    public final Wire input2;
    public final Wire input3;
    public final Wire input4;
    public final Wire input5;
    public final Wire input6;
    public final Wire output;

    @Override
    protected Duration propagationDelay() {
        return Duration.of(200);
    }

    public Nand6(Simulator simulator, ComponentId componentId) {
        super(simulator, componentId);

        input1 = new Wire(simulator, componentId.inputPin(1));
        input2 = new Wire(simulator, componentId.inputPin(2));
        input3 = new Wire(simulator, componentId.inputPin(3));
        input4 = new Wire(simulator, componentId.inputPin(4));
        input5 = new Wire(simulator, componentId.inputPin(5));
        input6 = new Wire(simulator, componentId.inputPin(6));
        output = new Wire(simulator, componentId.outputPin());

        onStateChange(input1, this::updateOutput);
        onStateChange(input2, this::updateOutput);
        onStateChange(input3, this::updateOutput);
        onStateChange(input4, this::updateOutput);
        onStateChange(input5, this::updateOutput);
        onStateChange(input6, this::updateOutput);

        scheduleInitialization(() -> {
            LogicState input1LS = input1.logicState();
            LogicState input2LS = input2.logicState();
            LogicState input3LS = input3.logicState();
            LogicState input4LS = input4.logicState();
            LogicState input5LS = input5.logicState();
            LogicState input6LS = input6.logicState();

            LogicState outputLS = ttlNand6(
                    input1LS, input2LS, input3LS,
                    input4LS, input5LS, input6LS);

            logger.log("[init] set (%s, %s, %s, %s, %s, %s) -> %s",
                    input1LS, input2LS, input3LS,
                    input4LS, input5LS, input6LS,
                    outputLS);
            output.applyState(outputLS, componentId());
        });
    }

    private void updateOutput() {
        LogicState input1LS = input1.logicState();
        LogicState input2LS = input2.logicState();
        LogicState input3LS = input3.logicState();
        LogicState input4LS = input4.logicState();
        LogicState input5LS = input5.logicState();
        LogicState input6LS = input6.logicState();

        LogicState outputLS = ttlNand6(
                input1LS, input2LS, input3LS,
                input4LS, input5LS, input6LS);

        scheduleWithPropagationDelay(() -> {
            logger.log("set (%s, %s, %s, %s, %s, %s) -> %s",
                    input1LS, input2LS, input3LS,
                    input4LS, input5LS, input6LS,
                    outputLS);
            output.applyState(outputLS, componentId());
        });
    }

    private LogicState ttlNand6(
            LogicState input1LS,
            LogicState input2LS,
            LogicState input3LS,
            LogicState input4LS,
            LogicState input5LS,
            LogicState input6LS) {
        return (input1LS.toTTL().isOne() &&
                input2LS.toTTL().isOne() &&
                input3LS.toTTL().isOne() &&
                input4LS.toTTL().isOne() &&
                input5LS.toTTL().isOne() &&
                input6LS.toTTL().isOne())
                ? LogicState.ZERO
                : LogicState.ONE;
    }
}
