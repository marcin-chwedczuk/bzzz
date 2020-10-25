package pl.marcinchwedczuk.bzzz.primitives.gates;

import pl.marcinchwedczuk.bzzz.primitives.BaseElement;
import pl.marcinchwedczuk.bzzz.primitives.ComponentId;
import pl.marcinchwedczuk.bzzz.primitives.LogicState;
import pl.marcinchwedczuk.bzzz.primitives.wires.Wire;
import pl.marcinchwedczuk.bzzz.simulator.Duration;
import pl.marcinchwedczuk.bzzz.simulator.Simulator;

public class And1N1 extends BaseElement {
    public final Wire input1N;
    public final Wire input2;
    public final Wire output;

    @Override
    protected Duration propagationDelay() {
        return Duration.of(200);
    }

    public And1N1(Simulator simulator, ComponentId componentId) {
        super(simulator, componentId);

        input1N = new Wire(simulator, componentId.inputPin(1));
        input2 = new Wire(simulator, componentId.inputPin(2));
        output = new Wire(simulator, componentId.outputPin());

        onStateChange(input1N, this::updateOutput);
        onStateChange(input2, this::updateOutput);

        scheduleInitialization(() -> {
            LogicState input1LS = input1N.logicState();
            LogicState input2LS = input2.logicState();

            LogicState outputLS = ttlAnd1N(input1LS, input2LS);

            logger.log("[init] set (%s,%s) -> %s", input1LS, input2LS, outputLS);
            output.applyState(outputLS, componentId());
        });
    }

    private void updateOutput() {
        LogicState input1LS = input1N.logicState();
        LogicState input2LS = input2.logicState();

        LogicState outputLS = ttlAnd1N(input1LS, input2LS);

        scheduleWithPropagationDelay(() -> {
            logger.log("set (%s,%s) -> %s", input1LS, input2LS, outputLS);
            output.applyState(outputLS, componentId());
        });
    }

    private LogicState ttlAnd1N(LogicState input1LS, LogicState input2LS) {
        return (input1LS.toTTL().reverse().isOne() && input2LS.toTTL().isOne())
                ? LogicState.ONE
                : LogicState.ZERO;
    }
}
